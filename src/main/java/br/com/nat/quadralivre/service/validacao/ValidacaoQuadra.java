package br.com.nat.quadralivre.service.validacao;

import br.com.nat.quadralivre.dto.EnderecoDTO;
import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.repository.QuadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoQuadra {
    final private QuadraRepository quadraRepository;

    @Autowired
    public ValidacaoQuadra(QuadraRepository quadraRepository){
        this.quadraRepository = quadraRepository;
    }

    private void verificarTituloExistente(String titulo){
        if(this.quadraRepository.existsByTitulo(titulo)){
            throw new DataIntegrityViolationException("Já existe uma quadra com esse título");
        }
    }

    public void validar(QuadraDTO quadra){
        boolean tituloExiste = this.quadraRepository.existsByTitulo(quadra.getTitulo());
        boolean enderecoExiste = this.quadraRepository.existsByEndereco(EnderecoDTO.toEndereco(quadra.getEndereco()));

        if(tituloExiste && enderecoExiste){
            throw new DataIntegrityViolationException("Já existe um cadastro para essa quadra nesse endereço.");
        }
    }

    public void validarAtualizacao(Quadra quadra, QuadraDTO quadraAtualizada){
        if(!quadra.getTitulo().equals(quadraAtualizada.getTitulo())){
            this.verificarTituloExistente(quadraAtualizada.getTitulo());
        }
    }
}

