package br.com.nat.quadralivre.service.validacao;

import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.repository.GestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoGestor {
    final private GestorRepository gestorRepository;

    @Autowired
    public ValidacaoGestor(GestorRepository gestorRepository){
        this.gestorRepository = gestorRepository;
    }

    private void validarUnicidade(String campo, String valor, boolean existe){
        if(existe){
            throw new DataIntegrityViolationException("Já existe um cadastro com esse " + campo + ": " + valor);
        }
    }

    private void verificarEmailExistente(String email){
        this.validarUnicidade("e-mail", email, this.gestorRepository.existsByEmail(email));
    }

    private void verificarTelefoneExistente(String telefone){
        this.validarUnicidade("telefone", telefone, this.gestorRepository.existsByTelefone(telefone));
    }

    public void validar(Gestor gestor){
        this.verificarEmailExistente(gestor.getEmail());
        this.verificarTelefoneExistente(gestor.getTelefone());
    }

    public void validarAtualizacao(Gestor gestor, Gestor gestorAtualizado){
        if(!gestor.getEmail().equals(gestorAtualizado.getEmail())){
            this.verificarEmailExistente(gestorAtualizado.getEmail());
        }

        if(!gestor.getTelefone().equals(gestorAtualizado.getTelefone())){
            this.verificarTelefoneExistente(gestorAtualizado.getTelefone());
        }
    }
}
