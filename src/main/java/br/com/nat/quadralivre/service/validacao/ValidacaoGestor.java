package br.com.nat.quadralivre.service.validacao;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.nat.quadralivre.dto.GestorDTO;
import br.com.nat.quadralivre.exceptions.CPFInvalidoException;
import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.repository.GestorRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

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

    public void validar(GestorDTO gestor){
        this.verificarEmailExistente(gestor.getEmail());
        this.verificarTelefoneExistente(gestor.getTelefone());
    }

    public void validarAtualizacao(Gestor gestor, GestorDTO gestorAtualizado){
        if(!gestor.getEmail().equals(gestorAtualizado.getEmail())){
            this.verificarEmailExistente(gestorAtualizado.getEmail());
        }

        if(!gestor.getTelefone().equals(gestorAtualizado.getTelefone())){
            this.verificarTelefoneExistente(gestorAtualizado.getTelefone());
        }
    }

    public void validarEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Digite um e-mail válido. Ex.: email@email.com");
        }
    }
}
