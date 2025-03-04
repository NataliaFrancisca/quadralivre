package br.com.nat.quadralivre.util;

import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.repository.GestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ValidatorGestor {
    final private GestorRepository gestorRepository;

    @Autowired
    public ValidatorGestor(GestorRepository gestorRepository){
        this.gestorRepository = gestorRepository;
    }

    public void validar(Gestor gestor){
        if(this.gestorRepository.existsByEmail(gestor.getEmail())){
            throw new DataIntegrityViolationException("Já existe um cadastro com esse endereço de e-mail.");
        }

        if(this.gestorRepository.existsByTelefone(gestor.getTelefone())){
            throw new DataIntegrityViolationException("Já existe um cadastro com esse número de telefone.");
        }
    }

    public void validarAtualizacao(Gestor gestor, Gestor gestorAtualizado){
        if(!gestor.getTelefone().equals(gestorAtualizado.getTelefone())
        || !gestor.getEmail().equals(gestorAtualizado.getEmail())){
            this.validar(gestor);
        }
    }
}
