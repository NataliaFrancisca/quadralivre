package br.com.nat.quadralivre.service.finder;

import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.repository.FuncionamentoRepository;
import br.com.nat.quadralivre.util.DiaSemanaUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HorarioDisponivelFinderService {
    private final FuncionamentoRepository funcionamentoRepository;

    public Funcionamento buscaPorHorariosDeFuncionamento(Long quadraId, LocalDate dataSolicitada){
        final DiaSemana diaDaSemana = DiaSemanaUtils.transformaDiaSemanaEmPortugues(dataSolicitada.getDayOfWeek().toString());

        Optional<Funcionamento> optionalFuncionamento = this.funcionamentoRepository.findAllByQuadraId(quadraId)
                .stream()
                .filter(dto -> dto.getDiaSemana().name().equals(diaDaSemana.name()))
                .findFirst();

        if(optionalFuncionamento.isEmpty()){
            throw new EntityNotFoundException("Não encontramos horários disponíveis para o dia solicitado.");
        }

        return optionalFuncionamento.get();
    }
}
