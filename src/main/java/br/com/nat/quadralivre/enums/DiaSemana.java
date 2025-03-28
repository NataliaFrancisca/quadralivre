package br.com.nat.quadralivre.enums;

import java.time.LocalDate;

public enum DiaSemana {
    DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO;

    public static DiaSemana from(LocalDate data){
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
}
