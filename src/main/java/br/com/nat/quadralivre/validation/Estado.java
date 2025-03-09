package br.com.nat.quadralivre.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DiaSemanaValidacao.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Estado {
    String message() default "Estado inválido. Use a sigla correta, como 'SP' ou 'RJ'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
