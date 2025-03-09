package br.com.nat.quadralivre.validation;

import br.com.nat.quadralivre.enums.Estados;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstadoValidacao implements ConstraintValidator<Estado, Estados> {

    @Override
    public void initialize(Estado estado){}

    @Override
    public boolean isValid(Estados value, ConstraintValidatorContext context){
        if(value == null){
            return false;
        }

        for(Estados estado : Estados.values()){
            if(estado == value){
                return true;
            }
        }

        return false;
    }
}
