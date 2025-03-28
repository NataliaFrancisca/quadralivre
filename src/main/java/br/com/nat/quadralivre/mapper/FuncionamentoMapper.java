package br.com.nat.quadralivre.mapper;

import br.com.nat.quadralivre.dto.FuncionamentoDTO;
import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.model.Quadra;
import org.springframework.stereotype.Component;

@Component
public class FuncionamentoMapper {

    public Funcionamento toFuncionamentoEntidade(FuncionamentoDTO funcionamentoDTO){
        Funcionamento funcionamento = new Funcionamento();

        funcionamento.setAbertura(funcionamentoDTO.getAbertura());
        funcionamento.setFechamento(funcionamentoDTO.getFechamento());
        funcionamento.setDiaSemana(funcionamentoDTO.getDiaSemana());
        funcionamento.setDisponibilidade(funcionamento.getDisponibilidade());

        return funcionamento;
    }

    public FuncionamentoDTO toDTOCompleto(Funcionamento funcionamento){
        FuncionamentoDTO funcionamentoDTO = new FuncionamentoDTO();

        funcionamentoDTO.setAbertura(funcionamento.getAbertura());
        funcionamentoDTO.setFechamento(funcionamento.getFechamento());
        funcionamentoDTO.setDiaSemana(funcionamento.getDiaSemana());
        funcionamentoDTO.setDisponibilidade(funcionamento.getDisponibilidade());

        return funcionamentoDTO;
    }

    public void toEntidadeComQuadraId(Funcionamento funcionamento, Quadra quadra){
        funcionamento.setQuadra(quadra);
        funcionamento.setQuadraId(quadra.getId());
    }
}

