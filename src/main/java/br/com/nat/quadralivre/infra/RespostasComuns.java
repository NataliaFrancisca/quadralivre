package br.com.nat.quadralivre.infra;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado."),
        @ApiResponse(responseCode = "409", description = "Conflito com dados já cadastrados."),
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.")
})
public @interface RespostasComuns{}

