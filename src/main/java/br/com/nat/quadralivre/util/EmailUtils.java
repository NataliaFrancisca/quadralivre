package br.com.nat.quadralivre.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailUtils {

    public void validarEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Digite um e-mail válido. Ex.: email@email.com");
        }
    }
}
