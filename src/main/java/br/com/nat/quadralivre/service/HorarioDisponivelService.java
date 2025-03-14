package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import br.com.nat.quadralivre.model.Reserva;
import br.com.nat.quadralivre.repository.ReservaRepository;
import br.com.nat.quadralivre.service.finder.HorarioDisponivelFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Map;

@Service
public class HorarioDisponivelService {
    private final ReservaRepository reservaRepository;
    private final HorarioDisponivelFinderService horarioDisponivelFinderService;
    private final GeradorDeHorarios geradorDeHorarios;

    @Autowired
    public HorarioDisponivelService(ReservaRepository reservaRepository, GeradorDeHorarios geradorDeHorarios, HorarioDisponivelFinderService horarioDisponivelFinderService){
        this.reservaRepository = reservaRepository;
        this.geradorDeHorarios = geradorDeHorarios;
        this.horarioDisponivelFinderService = horarioDisponivelFinderService;
    }

    public Map<Integer, HorarioDisponivel> buscarPorHorariosDisponiveis(Long quadraId, LocalDate dataSolicitada) {

        if(dataSolicitada.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Agendamentos só podem ser feitos em datas no futuro.");
        }

        Funcionamento funcionamento = this.horarioDisponivelFinderService.buscaPorHorariosDeFuncionamento(quadraId, dataSolicitada);

        if(!funcionamento.getDisponibilidade()){
            throw new IllegalStateException("A quadra não está disponível para o dia solicitado.");
        }

        Map<Integer, HorarioDisponivel> horariosDisponiveis = this.geradorDeHorarios.gerarHorarios(funcionamento, dataSolicitada);

        if(horariosDisponiveis.isEmpty()){
            throw new IllegalArgumentException("Não há horários disponiveis para reserva no dia solicitado.");
        }

        List<Reserva> reservas = reservaRepository
                .findByQuadraIdAndDataBetween(quadraId, dataSolicitada.atStartOfDay(), dataSolicitada.atTime(23, 59, 59));

        if (!reservas.isEmpty()) {
            horariosDisponiveis.entrySet().removeIf(entry -> {
                LocalTime horarioInicio = entry.getValue().getHorarioInicio();
                LocalTime horarioFim = entry.getValue().getHorarioEncerramento();

                return reservas.stream().anyMatch(reserva ->
                        (horarioInicio.isBefore(reserva.getHorarioEncerramento().minusMinutes(this.geradorDeHorarios.MINUTOS_PARA_INTERVALO))
                                && horarioFim.isAfter(reserva.getHorarioInicio().minusMinutes(this.geradorDeHorarios.MINUTOS_PARA_INTERVALO)))
                );
            });
        }

        return horariosDisponiveis;
    }
}
