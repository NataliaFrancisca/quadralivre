package br.com.nat.quadralivre.service.finder;

import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.model.Responsavel;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.repository.ResponsavelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ReservaFinderService {
    private final ResponsavelRepository responsavelRepository;
    private final QuadraRepository quadraRepository;
    private final HorarioDisponivelFinderService horarioDisponivelFinderService;

    public HorarioDisponivel buscarHorarioReserva(ReservaDTO reserva){
        return horarioDisponivelFinderService.buscarHorarioReserva(reserva);
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
