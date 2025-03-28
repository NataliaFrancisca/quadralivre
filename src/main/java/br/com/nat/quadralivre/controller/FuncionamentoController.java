package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.FuncionamentoCompletoDTO;
import br.com.nat.quadralivre.dto.FuncionamentoDTO;
import br.com.nat.quadralivre.dto.FuncionamentoQuadraDTO;

import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.infra.CorpoRequisicao;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.infra.RespostasComuns;
import br.com.nat.quadralivre.service.FuncionamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/funcionamento")
@Tag(name = "Funcionamento", description = "Operações relacionadas a funcionamento.")
public class FuncionamentoController {
    private final FuncionamentoService funcionamentoService;

    @Autowired
    public FuncionamentoController(FuncionamentoService funcionamentoService){
        this.funcionamentoService = funcionamentoService;
    }

    @Operation(summary = "Define horários de funcionamento para uma quadra.", description = "Define horários de funcionamento para a quadra indicada.")
    @CorpoRequisicao(descricao = "Objeto com as informações dos horários de funcionamento.", dto = FuncionamentoDTO.class)
    @RespostasComuns
    @PostMapping
    public ResponseEntity<RespostaAPI> create(@Valid @RequestBody FuncionamentoQuadraDTO funcionamentoQuadraDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                this.funcionamentoService.create(funcionamentoQuadraDTO)
        );
    }

    @Operation(summary = "Busca pelo horário de funcionamento de uma quadra", description = "Retorna o funcionamento da quadra indicada.")
    @RespostasComuns
    @GetMapping
    public ResponseEntity<RespostaAPI> get(@Parameter(description = "Id da quadra", required = true) @Valid @NotNull @RequestParam Long quadraId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.get(quadraId)
        );
    }

    @Operation(summary = "Atualiza os dados de funcionamento de um dia da semana.", description = "Atualiza os dados de funcionamento de uma quadra.")
    @RespostasComuns
    @CorpoRequisicao(descricao = "Objeto com o funcionamento atualizado.", dto = FuncionamentoCompletoDTO.class)
    @PutMapping
    public ResponseEntity<RespostaAPI> update(@Valid @RequestBody FuncionamentoCompletoDTO funcionamentoDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.update(funcionamentoDTO)
        );
    }

    @Operation(summary = "Atualiza a disponibilidade da quadra em um dia da semana.",
            description = "Atualiza os dados de funcionamento de uma quadra de acordo com o dia da semana indicado."
    )
    @RespostasComuns
    @PatchMapping
    public ResponseEntity<RespostaAPI> updateDisponibilidade(
            @Parameter(description = "ID da quadra", required = true)
            @RequestParam Long quadraId,
            @Parameter(description = "Dia da semana indicado, ex.: SEGUNDA", required = true)
            @Valid @RequestParam DiaSemana diaSemana){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.updateDisponibilidade(quadraId, diaSemana)
        );
    }


    @Operation(summary = "Deleta horário de funcionamento da quadra",
            description = "Deleta horário de funcionamento da quadra, baseado na quadra e o dia da semana."
    )
    @RespostasComuns
    @DeleteMapping
    public ResponseEntity<RespostaAPI> delete(
            @Parameter(description = "ID da quadra", required = true)
            @RequestParam Long quadraId,
            @Parameter(description = "Dia da semana indicado, ex.: SEGUNDA", required = true)
            @Valid @RequestParam DiaSemana diaSemana
    ){
        this.funcionamentoService.delete(quadraId, diaSemana);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Horário de funcionamento para o dia indicado foi deletado com sucesso."
        );
    }
}
