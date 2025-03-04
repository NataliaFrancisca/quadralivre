package br.com.nat.quadralivre.infra;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespostaAPI {
    private int statusHTTP;
    private String mensagem;
    private Object body;
    private Map<String, String> erros;

    public static ResponseEntity<RespostaAPI> build(HttpStatus statusHTTP, String mensagem){
        return ResponseEntity.status(statusHTTP).body(
                new RespostaAPI(statusHTTP.value(), mensagem, null, null));
    }

    public static ResponseEntity<RespostaAPI> build(HttpStatus statusHTTP, String mensagem, Object body){
        return ResponseEntity.status(statusHTTP).body(
                new RespostaAPI(statusHTTP.value(), mensagem, body, null));
    }

    public static ResponseEntity<RespostaAPI> build(HttpStatus statusHTTP, Object body){
        return ResponseEntity.status(statusHTTP).body(
                new RespostaAPI(statusHTTP.value(), null, body, null));
    }

    public static ResponseEntity<RespostaAPI> build(HttpStatus statusHTTP, String mensagem, Map<String, String> erros){
        return ResponseEntity.status(statusHTTP).body(
                new RespostaAPI(statusHTTP.value(), mensagem, null,  erros));
    }

}
