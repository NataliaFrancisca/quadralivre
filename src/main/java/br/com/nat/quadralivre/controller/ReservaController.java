package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.infra.CorpoRequisicao;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.infra.RespostasComuns;
import br.com.nat.quadralivre.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Cria uma nova reserva.", description = "Cria uma nova reserva de quadra.")
    @RespostasComuns
    @CorpoRequisicao(descricao = "Objeto com as informações da reserva.", dto=ReservaDTO.class)
    @PostMapping
    public ResponseEntity<RespostaAPI> create(@Valid @RequestBody ReservaDTO reservaDTO){
            return RespostaAPI.build(
                    HttpStatus.CREATED,
                    this.reservaService.create(reservaDTO)
            );
    }

    @Operation(summary = "Busca por uma reserva", description = "Busca por uma reserva que tenha o ID indicado.")
    @GetMapping("/{reservaId}")
    public ResponseEntity<RespostaAPI> getById(
            @Parameter(description = "Identificador da reserva, ex.: 1", required = true)
            @PathVariable Long reservaId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getById(reservaId)
        );
    }

    @Operation(summary = "Busca por todas as reservas feitas em determinada quadra",
            description = "Busca por todas as reservas que foram feitas na quadra indicada."
    )
    @RespostasComuns
    @GetMapping("/quadra/{quadraId}")
    public ResponseEntity<RespostaAPI> getAllByQuadra(
            @Parameter(description = "Identificador da quadra, ex.: 1", required = true)
            @PathVariable Long quadraId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getAllByQuadra(quadraId)
        );
    }

    @Operation(summary = "Remove uma reserva", description = "Remove uma reserva que tenha o ID indicado.")
    @RespostasComuns
    @DeleteMapping("/{reservaId}")
    public ResponseEntity<RespostaAPI> deleteById(
            @Parameter(description = "ID da reserva, ex.: 1", required = true)
            @PathVariable Long reservaId){
        this.reservaService.deleteById(reservaId);

        return RespostaAPI.build(
                HttpStatus.OK,
                "Reserva deletada com sucesso."
        );
    }
}

