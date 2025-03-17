package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GeradorDeHorarios {
    private final int HORAS_PARA_RESERVA_LONGA = 120;
    public final int MINUTOS_PARA_INTERVALO = 10;

    private HorarioDisponivel criarEntidadeParaHorarioDisponivel(LocalDateTime horarioAgendamento, long horasParaReserva){
        return new HorarioDisponivel(horarioAgendamento.toLocalTime(), horarioAgendamento.toLocalTime().plusMinutes(horasParaReserva));
    }

    private int gerarHash(String horarioAbertura){
        String conjunto = horarioAbertura.concat(String.valueOf(this.HORAS_PARA_RESERVA_LONGA));
        return Math.abs(conjunto.hashCode());
    }

    public Map<Integer, HorarioDisponivel> gerarHorarios(Funcionamento funcionamento, LocalDate dataSolicitada){
        final int HORAS_PARA_RESERVA_CURTA = 60;

        Map<Integer, HorarioDisponivel> horariosParaReserva = new LinkedHashMap<>();

        LocalDateTime horarioAbertura = LocalDateTime.of(dataSolicitada, funcionamento.getAbertura());
        LocalDateTime horarioEncerramento = LocalDateTime.of(dataSolicitada, funcionamento.getFechamento());
        LocalDateTime horarioAgendamento = horarioAbertura;

        while(horarioAgendamento.plusMinutes(this.HORAS_PARA_RESERVA_LONGA).isBefore(horarioEncerramento)){
            horariosParaReserva.put(
                    gerarHash(horarioAgendamento.toString()),
                    this.criarEntidadeParaHorarioDisponivel(horarioAgendamento, this.HORAS_PARA_RESERVA_LONGA)
            );

            horarioAgendamento = horarioAgendamento.plusMinutes(this.HORAS_PARA_RESERVA_LONGA).plusMinutes(this.MINUTOS_PARA_INTERVALO);
        }

        if (!horarioAgendamento.plusMinutes(HORAS_PARA_RESERVA_CURTA).isAfter(horarioEncerramento)) {
            long diferencaEmMinutos = horarioAgendamento.until(horarioEncerramento, ChronoUnit.MINUTES) - this.MINUTOS_PARA_INTERVALO;

            horariosParaReserva.put(
                    gerarHash(horarioAgendamento.toString()),
                    this.criarEntidadeParaHorarioDisponivel(horarioAgendamento, diferencaEmMinutos)
            );
        }

        return horariosParaReserva;
    }

}
