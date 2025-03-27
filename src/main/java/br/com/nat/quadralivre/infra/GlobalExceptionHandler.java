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
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<RespostaAPI> handleJsonParseException(JsonMappingException ex) {
        String mensagemErro = "O JSON enviado está malformado. Verifique a sintaxe e tente novamente.";

        if (ex instanceof UnrecognizedPropertyException) {
            mensagemErro = "O JSON está malformado. Verifique a sintaxe (faltando vírgulas ou chaves).";
        } else if (ex.getOriginalMessage().contains("br.com.nat.quadralivre.enums.Estados")) {
            mensagemErro = "O campo 'estado' é inválido. Digite um estado válido no formato de sigla, como 'SP' ou 'RJ'.";
        } else if (ex.getOriginalMessage().contains("br.com.nat.quadralivre.enums.DiaSemana")){
            mensagemErro = "O campo 'diaSemana' é inválido. Digite um dia da semana válido no seguinte formato, como 'SEGUNDA' OU 'SEXTA'";
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespostaAPI> lidarComErrosDeTiposDiferentes(MethodArgumentTypeMismatchException ex){
        String mensagemErro = "O parâmetro deve ser um valor válido.";

        if (ex.getRequiredType() != null) {
            String tipoEsperado = ex.getRequiredType().getSimpleName();

            if(tipoEsperado.equals("Long")){
                mensagemErro = "O parâmetro é inválido. Digite um número válido, ex.: 1 ou 34";
                if( ex.getName().equals("quadraId")){
                    mensagemErro = "O parâmetro '" + ex.getName() + "' é inválido. Digite um número válido.";
                }
            }

            if (tipoEsperado.equals("DiaSemana")) {
                mensagemErro = "O parâmetro 'diaSemana' é inválido. Digite um dia da semana válido, como: SEGUNDA ou TERCA";
            }
        }
        return RespostaAPI.build(HttpStatus.BAD_REQUEST, mensagemErro);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RespostaAPI> lidarComFaltaDeValorNosParametros(MissingServletRequestParameterException  ex){
        String mensagem = "O parâmetro '" + ex.getParameterName() + "' é obrigatório e não pode estar vazio.";
        return RespostaAPI.build(HttpStatus.BAD_REQUEST, mensagem);
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
