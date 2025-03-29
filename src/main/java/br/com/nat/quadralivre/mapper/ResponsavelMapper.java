package br.com.nat.quadralivre.mapper;

import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.model.Responsavel;
import org.springframework.stereotype.Component;

@Component
public class ResponsavelMapper {

    public Responsavel toEntidade(ResponsavelDTO responsavelDTO){
        Responsavel responsavel = new Responsavel();

        responsavel.setCpf(responsavelDTO.getCpf());
        responsavel.setNome(responsavelDTO.getNome());
        responsavel.setTelefone(responsavelDTO.getTelefone());
        responsavel.setEmail(responsavelDTO.getEmail());

        return responsavel;
    }

    public ResponsavelDTO toDTOCompleto(Responsavel responsavel){
        ResponsavelDTO responsavelDTO = new ResponsavelDTO();

        responsavelDTO.setCpf(responsavel.getCpf());
        responsavelDTO.setNome(responsavel.getNome());
        responsavelDTO.setTelefone(responsavel.getTelefone());
        responsavelDTO.setEmail(responsavel.getEmail());

        return responsavelDTO;
    }

    public ResponsavelDTO toDTOSimples(Responsavel responsavel){
        ResponsavelDTO responsavelDTO = new ResponsavelDTO();

        responsavelDTO.setNome(responsavel.getNome());
        responsavelDTO.setTelefone(responsavel.getTelefone());
        responsavelDTO.setEmail(responsavel.getEmail());

        return responsavelDTO;
    }
}
