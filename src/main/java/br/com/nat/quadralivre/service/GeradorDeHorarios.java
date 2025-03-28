package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class GeradorDeHorarios {
    private final int MINUTOS_PARA_RESERVA_LONGA = 120;
    public final int MINUTOS_PARA_INTERVALO = 10;

    private int gerarHash(String horarioAbertura){
        String conjunto = horarioAbertura.concat(String.valueOf(this.MINUTOS_PARA_RESERVA_LONGA));
        return Math.abs(conjunto.hashCode());
    }

    public Queue<HorarioDisponivel> gerarHorarios(Funcionamento funcionamento, LocalDate dataSolicitada){
        final int HORAS_PARA_RESERVA_CURTA = 60;

        Comparator<HorarioDisponivel> comparatorDePrioridadeEChegada =
                Comparator.comparing(HorarioDisponivel::getHorarioInicio)
                        .thenComparing(HorarioDisponivel::getHorarioInicio);

        Queue<HorarioDisponivel> horariosParaReserva = new PriorityQueue<>(comparatorDePrioridadeEChegada);

        LocalDateTime horarioAbertura =  LocalDateTime.of(dataSolicitada, funcionamento.getAbertura());
        LocalDateTime horarioEncerramento = LocalDateTime.of(dataSolicitada, funcionamento.getFechamento());
        LocalDateTime horarioAgendamento = horarioAbertura;

        while(horarioAgendamento.plusMinutes(this.MINUTOS_PARA_RESERVA_LONGA).isBefore(horarioEncerramento)){
            HorarioDisponivel horarioDisponivel = new HorarioDisponivel();

            horarioDisponivel.setId(gerarHash(horarioAgendamento.toString()));
            horarioDisponivel.setHorarioInicio(horarioAgendamento.toLocalTime());
            horarioDisponivel.setHorarioEncerramento(horarioAgendamento.toLocalTime().plusMinutes(this.MINUTOS_PARA_RESERVA_LONGA));

            horariosParaReserva.add(horarioDisponivel);

            horarioAgendamento = horarioAgendamento.plusMinutes(this.MINUTOS_PARA_RESERVA_LONGA).plusMinutes(this.MINUTOS_PARA_INTERVALO);
        }

        if (horarioAgendamento.plusMinutes(HORAS_PARA_RESERVA_CURTA).isBefore(horarioEncerramento)) {
            long diferencaEmMinutos = horarioAgendamento.until(horarioEncerramento, ChronoUnit.MINUTES) - this.MINUTOS_PARA_INTERVALO;

            HorarioDisponivel horarioDisponivel = new HorarioDisponivel();

            horarioDisponivel.setId(gerarHash(horarioAgendamento.toString()));
            horarioDisponivel.setHorarioInicio(horarioAgendamento.toLocalTime());
            horarioDisponivel.setHorarioEncerramento(horarioAgendamento.toLocalTime().plusMinutes(diferencaEmMinutos));

            horariosParaReserva.add(horarioDisponivel);
        }

        return horariosParaReserva;
    }
}
