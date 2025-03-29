package br.com.nat.quadralivre.mapper;

import br.com.nat.quadralivre.dto.ReservaCompletaDTO;
import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.dto.ReservaSimplesDTO;
import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import br.com.nat.quadralivre.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    private final QuadraMapper quadraMapper;
    private final ResponsavelMapper responsavelMapper;

    @Autowired
    public ReservaMapper(QuadraMapper quadraMapper, ResponsavelMapper responsavelMapper) {
        this.quadraMapper = quadraMapper;
        this.responsavelMapper = responsavelMapper;
    }

    public ReservaCompletaDTO toDTOCompleto(Reserva reserva){
        return new ReservaCompletaDTO(
                reserva.getId(),
                reserva.getData(),
                reserva.getHorarioInicio(),
                reserva.getHorarioEncerramento(),
                this.responsavelMapper.toDTOCompleto(reserva.getResponsavel()),
                this.quadraMapper.toDTOSimples(reserva.getQuadra())
        );
    }

    public ReservaSimplesDTO toDTOSimples(Reserva reserva){
        return new ReservaSimplesDTO(
                reserva.getId(),
                reserva.getData(),
                reserva.getHorarioInicio(),
                reserva.getHorarioEncerramento(),
                this.responsavelMapper.toDTOSimples(reserva.getResponsavel())
        );
    }

    public Reserva toReservaEntidade(ReservaDTO reservaDTO, HorarioDisponivel horarioReserva){
        Reserva reserva = new Reserva();

        reserva.setQuadraId(reservaDTO.getQuadraId());
        reserva.setResponsavelCPF(reservaDTO.getResponsavelCPF());
        reserva.setData(reservaDTO.getDataSolicitada().atTime(horarioReserva.getHorarioInicio()));
        reserva.setHorarioInicio(horarioReserva.getHorarioInicio());
        reserva.setHorarioEncerramento(horarioReserva.getHorarioEncerramento());

        return reserva;
    }
}
