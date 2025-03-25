package br.com.nat.quadralivre.mapper;

import br.com.nat.quadralivre.dto.GestorDTO;
import br.com.nat.quadralivre.model.Gestor;
import org.springframework.stereotype.Component;

@Component
public class GestorMapper {
    public Gestor toGestorEntidade(GestorDTO gestorDTO){
        Gestor gestor = new Gestor();

        gestor.setId(gestorDTO.getId());
        gestor.setNome(gestorDTO.getNome());
        gestor.setEmail(gestorDTO.getEmail());
        gestor.setTelefone(gestorDTO.getTelefone());

        return gestor;
    }

    public GestorDTO toDTOCompleto(Gestor gestor){
        GestorDTO gestorDTO = new GestorDTO();

        gestorDTO.setId(gestor.getId());
        gestorDTO.setNome(gestor.getNome());
        gestorDTO.setEmail(gestor.getEmail());
        gestorDTO.setTelefone(gestor.getTelefone());

        return gestorDTO;
    }

    public void atualizarEntidade(Gestor entidade, GestorDTO dto){
        entidade.setNome(dto.getNome());
        entidade.setTelefone(dto.getTelefone());
        entidade.setEmail(dto.getEmail());
    }
}
