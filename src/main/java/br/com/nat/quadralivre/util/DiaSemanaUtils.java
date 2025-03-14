package br.com.nat.quadralivre.util;

import br.com.nat.quadralivre.enums.DiaSemana;

import java.time.DayOfWeek;

public class DiaSemanaUtils {

    public static DiaSemana transformaDiaSemanaEmPortugues(String diaSemanaIngles){
        switch (diaSemanaIngles){
            case "MONDAY":
                return DiaSemana.SEGUNDA;
            case "TUESDAY":
                return DiaSemana.TERCA;
            case "WEDNESDAY":
                return DiaSemana.QUARTA;
            case "THURSDAY":
                return DiaSemana.QUINTA;
            case "FRIDAY":
                return DiaSemana.SEXTA;
            case "SATURDAY":
                return DiaSemana.SABADO;
            case "SUNDAY":
                return DiaSemana.DOMINGO;
            default:
                throw new IllegalArgumentException("Dia da semana inválido");
        }
    }

    public static DayOfWeek transfromaDiaSemanaEmIngles(DiaSemana diaSemanaPortugues){
        switch (diaSemanaPortugues.name()){
            case "SEGUNDA":
                return DayOfWeek.of(1);
            case "TERCA":
                return DayOfWeek.of(2);
            case "QUARTA":
                return DayOfWeek.of(3);
            case "QUINTA":
                return DayOfWeek.of(4);
            case "SEXTA":
                return DayOfWeek.of(5);
            case "SABADO":
                return DayOfWeek.of(6);
            case "DOMINGO":
                return DayOfWeek.of(7);
            default:
                throw new IllegalArgumentException("Dia da semana inválido");
        }
    }
}
