package br.com.nat.quadralivre.service.finder;

import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.repository.FuncionamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FuncionamentoFinderService {
    private final FuncionamentoRepository funcionamentoRepository;

    public Funcionamento buscarPeloFuncionamento(DiaSemana diaSemana, Long quadraId){
        return this.funcionamentoRepository.findAllByQuadraIdAndDiaSemana(quadraId, diaSemana)
                .orElseThrow(() -> new EntityNotFoundException("Não existe cadastro de funcionamento para esse dia da semana."));
    }

    public List<DiaSemana> buscarPelosDiasCadastrados(Long quadraId){
        return this.funcionamentoRepository.findAllByQuadraId(quadraId)
                .stream()
                .map(Funcionamento::getDiaSemana)
                .toList();
    }
}
