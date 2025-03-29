package br.com.nat.quadralivre.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum DiaSemana {
    DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO;

    public static DiaSemana toDiaSemana(LocalDate data){
        switch (data.getDayOfWeek()){
            case MONDAY: return SEGUNDA;
            case TUESDAY: return TERCA;
            case WEDNESDAY: return QUARTA;
            case THURSDAY: return QUINTA;
            case FRIDAY: return SEXTA;
            case SATURDAY: return SABADO;
            default: return DOMINGO;
        }
    }

    public static DayOfWeek toDayOfWeek(DiaSemana diaSemana){
        switch (diaSemana){
            case SEGUNDA : return DayOfWeek.MONDAY;
            case TERCA : return DayOfWeek.TUESDAY;
            case QUARTA: return DayOfWeek.WEDNESDAY;
            case QUINTA: return DayOfWeek.THURSDAY;
            case SEXTA: return DayOfWeek.FRIDAY;
            case SABADO: return DayOfWeek.SATURDAY;
            default: return DayOfWeek.SUNDAY;
        }
    }
}
