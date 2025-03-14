package br.com.nat.quadralivre.helper;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReservaHelper {
    public LocalDateTime encontrarProximaData(DayOfWeek diaDaSemana){
        LocalDate dataFinal = LocalDate.now();

        while(dataFinal.getDayOfWeek() != diaDaSemana){
            dataFinal = dataFinal.plusDays(1);
        }

        return dataFinal.atStartOfDay();
    }
}
