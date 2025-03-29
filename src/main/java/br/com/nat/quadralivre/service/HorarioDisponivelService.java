package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import br.com.nat.quadralivre.model.Reserva;
import br.com.nat.quadralivre.repository.FuncionamentoRepository;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.repository.ReservaRepository;
import br.com.nat.quadralivre.util.DiaSemanaUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class HorarioDisponivelService {
    private final FuncionamentoRepository funcionamentoRepository;
    private final GeradorDeHorarios geradorDeHorarios;
    private final ReservaRepository reservaRepository;
    private final QuadraRepository quadraRepository;

    @Autowired
    public HorarioDisponivelService(FuncionamentoRepository funcionamentoRepository, GeradorDeHorarios geradorDeHorarios, ReservaRepository reservaRepository, QuadraRepository quadraRepository){
        this.funcionamentoRepository = funcionamentoRepository;
        this.geradorDeHorarios = geradorDeHorarios;
        this.reservaRepository = reservaRepository;
        this.quadraRepository = quadraRepository;
    }

    private Funcionamento buscarHorarioDeFuncionamentoQuadra(Long quadraId, LocalDate dataSolicitada){
        DiaSemana diaSemana = DiaSemana.toDiaSemana(dataSolicitada);

        Funcionamento funcionamento = this.funcionamentoRepository.findByQuadraIdAndDiaSemana(quadraId, diaSemana)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos horário de funcionamento para o dia da semana escolhido."));

        if(!funcionamento.getDisponibilidade()){
            throw new IllegalStateException("Quadra não está disponível no dia da semana escolhido.");
        }

        return funcionamento;
    }

    private List<Reserva> buscarPelasReservasJaFeitas(Long quadraId, LocalDate dataSolicitada){
        LocalDateTime inicioData = dataSolicitada.atStartOfDay();
        LocalDateTime fimData = dataSolicitada.atTime(23, 59, 59);

        return this.reservaRepository.findAllByQuadraIdAndDataBetween(
                quadraId,
                inicioData,
                fimData
        );
    }

    private boolean temConflitoDeHorario(HorarioDisponivel horarioDisponivel, List<Reserva> reservas){
        LocalTime horarioInicio = horarioDisponivel.getHorarioInicio();
        LocalTime horarioFim = horarioDisponivel.getHorarioEncerramento();

        return reservas.stream().anyMatch(reserva ->
                (horarioInicio.isBefore(reserva.getHorarioEncerramento().minusMinutes(this.geradorDeHorarios.MINUTOS_PARA_INTERVALO))
                        && horarioFim.isAfter(reserva.getHorarioInicio().minusMinutes(this.geradorDeHorarios.MINUTOS_PARA_INTERVALO)))
        );
    }

    private void removerHorariosReservados(List<HorarioDisponivel> horarios, Long quadraId, LocalDate dataSolicitada){
        List<Reserva> reservas = this.buscarPelasReservasJaFeitas(quadraId, dataSolicitada);

        if(reservas.isEmpty()){
            return;
        }

        horarios.removeIf(entry -> this.temConflitoDeHorario(entry, reservas));
    }

    private void removerHorariosQueJaPassaram(List<HorarioDisponivel> horarios, LocalDate dataSolicitada){
        LocalDateTime dataAtual = LocalDateTime.now();
        DiaSemana diaSemanaAtual = DiaSemanaUtils.transformaDiaSemanaEmPortugues(dataAtual.getDayOfWeek().toString());
        DiaSemana diaSemanaPedido = DiaSemanaUtils.transformaDiaSemanaEmPortugues(dataSolicitada.getDayOfWeek().toString());

        if(diaSemanaAtual.equals(diaSemanaPedido)){
            horarios.removeIf(entry -> entry.getHorarioInicio().isBefore(LocalTime.now()));
        }
    }

    private List<HorarioDisponivel> gerarHorariosParaReserva(Long quadraId, LocalDate dataSolicitada){
        Funcionamento funcionamento = this.buscarHorarioDeFuncionamentoQuadra(quadraId, dataSolicitada);

        List<HorarioDisponivel> horarios = this.geradorDeHorarios.gerarHorarios(funcionamento, dataSolicitada);

        this.removerHorariosReservados(horarios, quadraId, dataSolicitada);
        this.removerHorariosQueJaPassaram(horarios, dataSolicitada);

        if(horarios.isEmpty()){
            throw new IllegalStateException("Não há horários disponíveis para reserva no dia solicitado.");
        }

        return horarios;
    }

    public List<HorarioDisponivel> get(Long quadraId, LocalDate dataSolicitada){
        if(dataSolicitada.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Reservas só podem ser feitas em datas no futuro.");
        }

        this.quadraRepository.findById(quadraId)
                .orElseThrow(() -> new EntityNotFoundException("Não existe quadra cadastrada com esse número de ID."));

        return this.gerarHorariosParaReserva(quadraId, dataSolicitada);
    }

}
