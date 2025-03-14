package br.com.nat.quadralivre.mapper;

import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.dto.ReservaCompletaDTO;
import br.com.nat.quadralivre.dto.ReservaSimplesDTO;
import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    public ReservaCompletaDTO toDTOCompleto(Reserva reserva){
        return new ReservaCompletaDTO(
                reserva.getId(),
                reserva.getData(),
                reserva.getHorarioInicio(),
                reserva.getHorarioEncerramento(),
                ResponsavelDTO.fromEntity(reserva.getResponsavel()),
                QuadraDTO.fromEntityShort(reserva.getQuadra())
        );
    }

    public ReservaSimplesDTO toDTOSimples(Reserva reserva){
        return new ReservaSimplesDTO(
                reserva.getId(),
                reserva.getData(),
                reserva.getHorarioInicio(),
                reserva.getHorarioEncerramento(),
                ResponsavelDTO.fromEntity(reserva.getResponsavel())
        );
    }
}
