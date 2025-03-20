package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserva")
@Tag(name = "Reserva", description = "Operações relacionadas a reserva.")
public class ReservaController {
    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService){
        this.reservaService = reservaService;
    }

    @Operation(
            summary = "Cria uma nova reserva.",
            description = "Cria uma nova reserva de quadra.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "É necessários que os campos sejam preenchidos corretamente."),
                    @ApiResponse(responseCode = "409", description = "Já existe uma reserva feita pelo CPF indicado."),
                    @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso.")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações atualizadas da quadra", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ReservaDTO.class)
    ))
    @PostMapping
    public ResponseEntity<RespostaAPI> create(@RequestBody @Valid ReservaDTO reservaDTO){
            return RespostaAPI.build(
                    HttpStatus.CREATED,
                    this.reservaService.create(reservaDTO)
            );
    }


    @Operation(
            summary = "Retorna todas as reservas já feitas.",
            description = "Retorna uma lista com todas as reservas já feitas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista com todas as reservas feitas"),
                    @ApiResponse(responseCode = "404", description = "Não foi feita nenhuma reserva.")
            }
    )
    @GetMapping
    public ResponseEntity<RespostaAPI> getAll(){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getAll()
        );
    }

    @Operation(
            summary = "Busca por uma reserva",
            description = "Busca por uma reserva que tenha o ID que foi passado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reserva encontrada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhuma reserva foi encontrada com esse ID."),
                    @ApiResponse(responseCode = "400", description = "Digite um ID que seja válido, ex.: 1 ou 2")
            }
    )
    @GetMapping("/{reservaId}")
    public ResponseEntity<RespostaAPI> getById(
            @Parameter(description = "Identificador da reserva, ex.: 1", required = true)
            @PathVariable Long reservaId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getById(reservaId)
        );
    }

    @Operation(
            summary = "Busca por todas as reservas feitas em determinada quadra",
            description = "Busca por todas as reservas que foram feitas na quadra indicada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservas encontradas com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhuma reserva foi feita na quadra indicada."),
                    @ApiResponse(responseCode = "400", description = "Digite um ID que seja válido, ex.: 1 ou 2")
            }
    )
    @GetMapping("/quadra/{quadraId}")
    public ResponseEntity<RespostaAPI> getAllByQuadra(
            @Parameter(description = "Identificador da quadra, ex.: 1", required = true)
            @PathVariable Long quadraId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getAllByQuadra(quadraId)
        );
    }

    @Operation(
            summary = "Remove uma reserva",
            description = "Remove uma reserva, baseado em seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservas deletada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhuma reserva encontrada com esse ID."),
                    @ApiResponse(responseCode = "400", description = "Digite um ID que seja válido, ex.: 1 ou 2")
            }
    )
    @DeleteMapping("/{reservaId}")
    public ResponseEntity<RespostaAPI> deleteById(
            @Parameter(description = "Identificador da reserva, ex.: 1", required = true)
            @PathVariable Long reservaId){
        this.reservaService.deleteById(reservaId);

        return RespostaAPI.build(
                HttpStatus.OK,
                "Reserva deletada com sucesso."
        );
    }
}

