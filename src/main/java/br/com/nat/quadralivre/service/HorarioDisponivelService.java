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
import java.time.LocalTime;

import java.util.List;
import java.util.Map;


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

    private Funcionamento buscarPeloFuncionamentoDaQuadra(Long quadraId, LocalDate dataSolicitada){
        this.quadraRepository.findById(quadraId).orElseThrow(() -> new EntityNotFoundException("Não existe quadra cadastrada com esse número de ID."));

        DiaSemana diaSemana = DiaSemanaUtils.transformaDiaSemanaEmPortugues(dataSolicitada.getDayOfWeek().toString());

        Funcionamento funcionamento = this.funcionamentoRepository.findByQuadraIdAndDiaSemana(quadraId, diaSemana)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos horário de funcionamento para o dia da semana escolhido."));

        if(!funcionamento.getDisponibilidade()){
            throw new IllegalStateException("Quadra não está disponível no dia da semana escolhido.");
        }

        return funcionamento;
    }

    private Map<Integer, HorarioDisponivel> gerarHorariosLivresParaReserva(Funcionamento funcionamento, LocalDate dataSolicitada, List<Reserva> reservas){
        Map<Integer, HorarioDisponivel> horarios = this.geradorDeHorarios.gerarHorarios(funcionamento, dataSolicitada);

        if(horarios.isEmpty()){
            throw new IllegalStateException("Não há horários disponíveis para reserva no dia solicitado.");
        }

        if(!reservas.isEmpty()){
            horarios.entrySet().removeIf(entry -> {
                LocalTime horarioInicio = entry.getValue().getHorarioInicio();
                LocalTime horarioFim = entry.getValue().getHorarioEncerramento();

                return reservas.stream().anyMatch(reserva ->
                        (horarioInicio.isBefore(reserva.getHorarioEncerramento().minusMinutes(this.geradorDeHorarios.MINUTOS_PARA_INTERVALO))
                                && horarioFim.isAfter(reserva.getHorarioInicio().minusMinutes(this.geradorDeHorarios.MINUTOS_PARA_INTERVALO)))
                );
            });
        }

        return horarios;
    }

    private List<Reserva> buscarPelasReservasJaFeitas(Long quadraId, LocalDate dataSolicitada){
        return this.reservaRepository.findByQuadraIdAndDataBetween(
                quadraId,
                dataSolicitada.atStartOfDay(),
                dataSolicitada.atTime(23, 59, 59)
        );
    }

    public Map<Integer, HorarioDisponivel> buscarHorariosDisponiveis(Long quadraId, LocalDate dataSolicitada){
        if(dataSolicitada.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Reservas só podem ser feitas em datas no futuro.");
        }

        Funcionamento funcionamento = this.buscarPeloFuncionamentoDaQuadra(quadraId, dataSolicitada);

        List<Reserva> reservas = this.buscarPelasReservasJaFeitas(quadraId, dataSolicitada);

        return this.gerarHorariosLivresParaReserva(funcionamento, dataSolicitada, reservas);
    }

}
