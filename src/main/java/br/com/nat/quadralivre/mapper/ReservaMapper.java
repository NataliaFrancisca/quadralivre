package br.com.nat.quadralivre.mapper;

import br.com.nat.quadralivre.dto.ReservaCompletaDTO;
import br.com.nat.quadralivre.dto.ReservaSimplesDTO;
import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    private final QuadraMapper quadraMapper;

    @Autowired
    public ReservaMapper(QuadraMapper quadraMapper) {
        this.quadraMapper = quadraMapper;
    }

    public ReservaCompletaDTO toDTOCompleto(Reserva reserva){
        return new ReservaCompletaDTO(
                reserva.getId(),
                reserva.getData(),
                reserva.getHorarioInicio(),
                reserva.getHorarioEncerramento(),
                ResponsavelDTO.fromEntity(reserva.getResponsavel()),
                this.quadraMapper.toDTOSimples(reserva.getQuadra())
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
