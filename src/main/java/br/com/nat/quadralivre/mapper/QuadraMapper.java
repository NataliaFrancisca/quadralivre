package br.com.nat.quadralivre.mapper;

import br.com.nat.quadralivre.dto.EnderecoDTO;
import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.model.Quadra;
import org.springframework.stereotype.Component;

@Component
public class QuadraMapper {

    public Quadra toQuadraEntidade(QuadraDTO quadraDTO){
        Quadra quadra = new Quadra();

        quadra.setId(quadraDTO.getId());
        quadra.setTitulo(quadraDTO.getTitulo());
        quadra.setGestor_id(quadraDTO.getGestor_id());
        quadra.setGestor(quadraDTO.getGestor());
        quadra.setEndereco(EnderecoDTO.toEndereco(quadraDTO.getEndereco()));

        return quadra;
    }

    public QuadraDTO toDTOCompleto(Quadra quadra){
        QuadraDTO quadraDTO = new QuadraDTO();

        quadraDTO.setId(quadra.getId());
        quadraDTO.setTitulo(quadra.getTitulo());
        quadraDTO.setGestor(quadra.getGestor());
        quadraDTO.setEndereco(EnderecoDTO.fromEndereco(quadra.getEndereco()));

        return quadraDTO;
    }

    public QuadraDTO toDTOSimples(Quadra quadra){
        QuadraDTO quadraDTO = new QuadraDTO();

        quadraDTO.setTitulo(quadra.getTitulo());
        quadraDTO.setEndereco(EnderecoDTO.fromEndereco(quadra.getEndereco()));

        return quadraDTO;
    }
}
