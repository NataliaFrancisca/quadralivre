package br.com.nat.quadralivre.util;

import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.repository.QuadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidatorQuadra {
    final private QuadraRepository quadraRepository;

    @Autowired
    public ValidatorQuadra(QuadraRepository quadraRepository){
        this.quadraRepository = quadraRepository;
    }

    public void validar(Quadra quadra){
        boolean tituloExiste = this.quadraRepository.existsByTitulo(quadra.getTitulo());
        boolean enderecoExiste = this.quadraRepository.existsByEndereco(quadra.getEndereco());

        if(tituloExiste && enderecoExiste){
            throw new DataIntegrityViolationException("Já existe um cadastro para essa quadra.");
        }
    }

    public void validarAtualizacao(Quadra quadra, Quadra quadraAtualizada){
        if(!quadra.getTitulo().equals(quadraAtualizada.getTitulo())){
            this.validar(quadra);
        }
    }
}

