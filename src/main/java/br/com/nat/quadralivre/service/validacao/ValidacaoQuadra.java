package br.com.nat.quadralivre.service.validacao;

import br.com.nat.quadralivre.dto.EnderecoDTO;
import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.util.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoQuadra {
    final private QuadraRepository quadraRepository;
    final private EmailUtils emailUtils;

    @Autowired
    public ValidacaoQuadra(QuadraRepository quadraRepository, EmailUtils emailUtils){
        this.quadraRepository = quadraRepository;
        this.emailUtils = emailUtils;
    }

    public void validar(QuadraDTO quadraDTO){
        boolean quadraComTituloJaCadastradaNoEndereco = this.quadraRepository
                .existsByTituloAndEndereco(quadraDTO.getTitulo(), EnderecoDTO.toEndereco(quadraDTO.getEndereco()));

        if(quadraComTituloJaCadastradaNoEndereco) {
            throw new DataIntegrityViolationException("Já existe um cadastro para essa quadra nesse endereço.");
        }
    }

    public void validarAtualizacao(Quadra quadra, QuadraDTO quadraAtualizada){
        if(!quadra.getTitulo().equals(quadraAtualizada.getTitulo())){
            this.validar(quadraAtualizada);
        }
    }

    public void validarEmail(String email){
        this.emailUtils.validarEmail(email);
    }
}

