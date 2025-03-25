package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.GestorDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.infra.RespostasComuns;
import br.com.nat.quadralivre.service.GestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestor")
@Tag(name = "Gestor", description = "Operações relacionadas a gestor.")
public class GestorController {
    private final GestorService gestorService;

    @Autowired
    public GestorController(GestorService gestorService) {
        this.gestorService = gestorService;
    }

    @Operation(
            summary = "Cria um novo gestor.",
            description = "Cria um novo gestor que é o responsável pela quadra."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações do Gestor.", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = GestorDTO.class)
    ))
    @RespostasComuns
    @PostMapping
    public ResponseEntity<RespostaAPI> create(
            @Valid @RequestBody GestorDTO gestorDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                "Gestor cadastrado com sucesso.",
                this.gestorService.create(gestorDTO)
        );
    }

    @Operation(
            summary = "Retorna todos os gestores cadastrados.",
            description = "Busca por todos os gestores cadastrados."
    )
    @RespostasComuns
    @GetMapping
    public ResponseEntity<RespostaAPI> getAll() {
        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.getAll()
        );
    }

    @Operation(
            summary = "Retorna um gestor usando seu e-mail.",
            description = "Busca por um gestor que tenha o e-mail que foi indicado."
    )
    @RespostasComuns
    @GetMapping("/email")
    public ResponseEntity<RespostaAPI> getByEmail(
            @Parameter(description = "E-mail do gestor.", required = true)
            @RequestParam @NotNull String valor) {
        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.get(valor)
        );
    }

    @Operation(
            summary = "Atualiza os dados de um gestor.",
            description = "Atualiza os dados de um gestor, baseado no e-mail indicado."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações atualizadas do Gestor.", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = GestorDTO.class)
    ))
    @RespostasComuns
    @PutMapping("/email")
    public ResponseEntity<RespostaAPI> update(
            @Parameter(description = "E-mail do gestor.")
            @RequestParam String valor,
            @Valid @RequestBody GestorDTO gestorDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.update(valor, gestorDTO)
        );
    }

    @Operation(
            summary = "Deleta os dados do Gestor.",
            description = "Deleta os dados do gestor, baseado no e-mail indicado."
    )
    @RespostasComuns
    @Transactional
    @DeleteMapping("/email")
    public ResponseEntity<RespostaAPI> delete(@Parameter(description = "E-mail do gestor.", required = true) @RequestParam String valor) {
        this.gestorService.delete(valor);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Gestor deletado com sucesso."
        );
    }
}
