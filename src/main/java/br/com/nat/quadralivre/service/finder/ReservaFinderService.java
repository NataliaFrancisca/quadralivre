package br.com.nat.quadralivre.service.finder;

import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.model.Responsavel;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.repository.ResponsavelRepository;
import br.com.nat.quadralivre.service.HorarioDisponivelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservaFinderService {
    private final ResponsavelRepository responsavelRepository;
    private final QuadraRepository quadraRepository;
    private final HorarioDisponivelService horarioDisponivelService;

    public HorarioDisponivel buscarHorarioReserva(ReservaDTO reserva){
        Map<Integer, HorarioDisponivel> horariosDisponiveis = this.horarioDisponivelService.buscarHorariosDisponiveis(
                reserva.getQuadraId(),
                reserva.getDataSolicitada()
        );

        return Optional.ofNullable(horariosDisponiveis.get(reserva.getHorarioDisponivelId())).orElseThrow(() ->
                new EntityNotFoundException("Horário de reserva com ID " + reserva.getHorarioDisponivelId() + " não encontrado."));
    }

    public Quadra buscarQuadra(Long quadraId){
        return this.quadraRepository.findById(quadraId)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos quadra com esse número de ID."));
    }

    public Responsavel buscarResponsavel(String CPF){
        return this.responsavelRepository.findByCpf(CPF)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos responsável com esse número de CPF."));
    }
}
