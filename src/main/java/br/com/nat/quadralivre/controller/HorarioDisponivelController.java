package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.HorarioDisponivelDTO;
import br.com.nat.quadralivre.infra.CorpoRequisicao;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.infra.RespostasComuns;
import br.com.nat.quadralivre.service.HorarioDisponivelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @CorpoRequisicao(descricao = "Objeto com a informação do horário escolhido.", dto = HorarioDisponivelDTO.class)
    @GetMapping
    public ResponseEntity<RespostaAPI> get(@Valid @RequestBody HorarioDisponivelDTO horarioDisponivelDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.horarioDisponivelService.get(horarioDisponivelDTO)
        );
    }
}
