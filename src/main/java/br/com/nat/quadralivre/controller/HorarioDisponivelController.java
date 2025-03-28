package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.infra.RespostasComuns;
import br.com.nat.quadralivre.service.HorarioDisponivelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/horarios-disponiveis")
@Tag(name = "Horário Disponivel", description = "Operação relacionada com horário disponivel.")
public class HorarioDisponivelController {
    private final HorarioDisponivelService horarioDisponivelService;

    @Autowired
    public HorarioDisponivelController(HorarioDisponivelService horarioDisponivelService){
        this.horarioDisponivelService = horarioDisponivelService;
    }

    @Operation(summary = "Busca pelos horários disponiveis para reserva",
            description = "Retorna uma lista com os horários disponiveis para reserva da quadra indicada.")
    @RespostasComuns
    @GetMapping
    public ResponseEntity<RespostaAPI> get(
            @Parameter(description = "Identificador da quadra, ex.: 1")
            @RequestParam Long quadraId,
            @Parameter(description = "Data para consultar horários para reserva, ex.: 2025-03-19")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.horarioDisponivelService.get(quadraId, data)
        );
    }
}
