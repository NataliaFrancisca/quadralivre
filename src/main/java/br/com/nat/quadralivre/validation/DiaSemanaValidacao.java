package br.com.nat.quadralivre.validation;

import br.com.nat.quadralivre.enums.DiaSemana;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;

public class DiaSemanaValidacao implements ConstraintValidator<DiasSemana, DiaSemana> {

    @Override
    public void initialize(DiasSemana diaSemana){}

    @Override
    public boolean isValid(DiaSemana value, ConstraintValidatorContext context){

        if(value == null){
            return false;
        }

        for(DiaSemana diaSemana: DiaSemana.values()){
            if(diaSemana == value){
                return true;
            }
        }

        return false;
    }
}
