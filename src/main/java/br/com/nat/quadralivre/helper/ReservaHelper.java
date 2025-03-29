package br.com.nat.quadralivre.helper;

import br.com.nat.quadralivre.enums.DiaSemana;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReservaHelper {

    private LocalDateTime buscarProximaDataDeAcordoComDiaDaSemana(DayOfWeek diaSemana){
        LocalDate dataFinal = LocalDate.now();

        while(dataFinal.getDayOfWeek() != diaSemana){
            dataFinal = dataFinal.plusDays(1);
        }

        return dataFinal.atStartOfDay();
    }

    public LocalDateTime encontrarProximaData(LocalDate dataSolicitada){
        DayOfWeek diaSemana = DiaSemana.toDayOfWeek(DiaSemana.toDiaSemana(dataSolicitada));
        return this.buscarProximaDataDeAcordoComDiaDaSemana(diaSemana);
    }

    public LocalDateTime encontrarProximaData(DayOfWeek diaSemana){
        return this.buscarProximaDataDeAcordoComDiaDaSemana(diaSemana);
    }
}
