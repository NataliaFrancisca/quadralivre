package br.com.nat.quadralivre.infra;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<RespostaAPI> handleJsonParseException(JsonMappingException ex) {
        String mensagemErro = "O JSON enviado está malformado. Verifique a sintaxe e tente novamente.";

        if(ex instanceof UnrecognizedPropertyException){
            mensagemErro = "O JSON está malformado. Verifique a sintaxe (faltando vírgulas ou chaves).";
        }

        return RespostaAPI.build(HttpStatus.BAD_REQUEST, mensagemErro);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<RespostaAPI> lidarComExcecoesDeValidacao(Exception ex){
        Map<String, String> erros = new HashMap<>();

        if(ex instanceof MethodArgumentNotValidException){
            ((MethodArgumentNotValidException) ex)
                    .getBindingResult()
                    .getFieldErrors().forEach(error -> {
                        erros.put(error.getField(), error.getDefaultMessage());
                    });
        } else if(ex instanceof ConstraintViolationException){
            ((ConstraintViolationException) ex)
                    .getConstraintViolations()
                    .forEach(violation -> {
                        erros.put(violation.getPropertyPath().toString(), violation.getMessage());
                    });
        }

        return RespostaAPI.build(HttpStatus.BAD_REQUEST, "Ocorreu um erro na requisição", erros);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RespostaAPI> lidarComExcecoesDeConflito(Exception ex){
        return RespostaAPI.build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RespostaAPI> lidarComDadoNaoEncontrado(EntityNotFoundException ex){
        return RespostaAPI.build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<RespostaAPI> lidarComExcecaoDeEstadoInvalido(IllegalStateException ex){
        return RespostaAPI.build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespostaAPI> lidarComExcecaoDeArgumentoInvalido(IllegalArgumentException ex){
        return RespostaAPI.build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
