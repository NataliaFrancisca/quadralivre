package br.com.nat.quadralivre.infra;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Resposta padrão da API.")
public class RespostaAPI {
    @Schema(description = "Código de status HTTP.")
    private int statusHTTP;
    @Schema(description = "Mensagem opcional.", nullable = true)
    private String mensagem;
    @Schema(description = "Corpo da resposta", nullable = true)
    private Object body;
    @Schema(description = "Mapa de erros.", nullable = true)
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
