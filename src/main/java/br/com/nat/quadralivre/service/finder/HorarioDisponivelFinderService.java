package br.com.nat.quadralivre.service.finder;

import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import br.com.nat.quadralivre.service.HorarioDisponivelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HorarioDisponivelFinderService {
    private final HorarioDisponivelService horarioDisponivelService;

    private List<HorarioDisponivel> buscarHorariosDisponives(ReservaDTO reserva){
        List<HorarioDisponivel> horariosDisponiveis = this.horarioDisponivelService.get(
                reserva.getQuadraId(),
                reserva.getDataSolicitada()
        );

        if(horariosDisponiveis.isEmpty()){
            throw new EntityNotFoundException("Não existe horários de reserva");
        }

        return horariosDisponiveis;
    }

    public HorarioDisponivel buscarHorarioReserva(ReservaDTO reserva){
        List<HorarioDisponivel> horarios = this.buscarHorariosDisponives(reserva);

        Optional<HorarioDisponivel> horarioReservaDados = horarios.stream()
                .filter(dto -> dto.getId() == reserva.getHorarioDisponivelId())
                .findFirst();

        if(horarioReservaDados.isEmpty()){
            throw new EntityNotFoundException("Horário de reserva com ID " +  reserva.getHorarioDisponivelId() + " não foi encontrada.");
        }

        return horarioReservaDados.get();
    }
}
