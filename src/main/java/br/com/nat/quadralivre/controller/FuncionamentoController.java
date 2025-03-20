package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.FuncionamentoDTO;
import br.com.nat.quadralivre.dto.GestorDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.FuncionamentoService;
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

import java.util.List;

@RestController
@RequestMapping("/funcionamento")
@Tag(name = "Funcionamento", description = "Operações relacionadas a funcionamento.")
public class FuncionamentoController {
    private final FuncionamentoService funcionamentoService;

    @Autowired
    public FuncionamentoController(FuncionamentoService funcionamentoService){
        this.funcionamentoService = funcionamentoService;
    }

    @Operation(
            summary = "Cria um novo funcionamento",
            description = "Cria horários de funcionamento para a quadra indicada",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Não existe quadra com esse número de ID."),
                    @ApiResponse(responseCode = "400", description = "Os dados devem ser preenchidos corretamente. Ou, nenhum valor foi adicionado, dias da semana que já existem são ignorados."),
                    @ApiResponse(responseCode = "201", description = "Horários de funcionamento cadastrados com sucesso.")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações dos horários de funcionamento.", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = FuncionamentoDTO.class)
    ))
    @PostMapping
    public ResponseEntity<RespostaAPI> create(
            @Parameter(description = "Identificador da quadra", required = true)
            @RequestParam Long quadraId,
            @RequestBody List<FuncionamentoDTO> funcionamentoDTOS){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                this.funcionamentoService.create(quadraId, funcionamentoDTOS)
        );
    }

    @Operation(
            summary = "Busca pelo funcionamento de uma quadra",
            description = "Retorna o funcionamento da quadra indicada.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Não existe quadra com esse número de ID."),
                    @ApiResponse(responseCode = "200", description = "Retorna os horários de funcionamento da quadra.")
            }
    )
    @GetMapping
    public ResponseEntity<RespostaAPI> get(
            @Parameter(description = "Identificador da quadra", required = true)
            @RequestParam Long quadraId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.get(quadraId)
        );
    }

    @Operation(
            summary = "Atualiza os dados de funcionamento.",
            description = "Atualiza os dados de funcionamento de uma quadra.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Não existe quadra com esse número de ID."),
                    @ApiResponse(responseCode = "200", description = "Dados de funcionamento da quadra atualizados com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Os dados devem ser preenchidos corretamente.")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações dos horários de funcionamento.", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = FuncionamentoDTO.class)
    ))
    @PutMapping
    public ResponseEntity<RespostaAPI> update(@Valid @RequestBody FuncionamentoDTO funcionamentoDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.update(funcionamentoDTO)
        );
    }


    @Operation(
            summary = "Atualiza a disponibilidade da quadra em um dia da semana.",
            description = "Atualiza os dados de funcionamento de uma quadra de acordo com o dia da semana indicado.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Não existe cadastro de funcionamento para esse dia da semana."),
                    @ApiResponse(responseCode = "200", description = "Dados de funcionamento da quadra atualizados com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Os dados devem ser preenchidos corretamente.")
            }
    )
    @PatchMapping
    public ResponseEntity<RespostaAPI> updateDisponibilidade(
            @Parameter(description = "Identificador da quadra", required = true)
            @RequestParam Long quadraId,
            @Parameter(description = "Dia da semana indicado, ex.: SEGUNDA", required = true)
            @RequestParam String diaSemana){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.updateDisponibilidade(quadraId, diaSemana)
        );
    }

    @Operation(
            summary = "Deleta horários de funcionamento da quadra",
            description = "Deleta horários de funcionamento da quadra, baseado na quadra e o dia da semana.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Uma dos parametros não foi encontrado."),
                    @ApiResponse(responseCode = "200", description = "Horário de funcionamento deletado com sucesso."),
            }
    )
    @DeleteMapping
    public ResponseEntity<RespostaAPI> delete(
            @Parameter(description = "Identificador da quadra", required = true)
            @RequestParam Long quadraId,
            @Parameter(description = "Dia da semana indicado, ex.: SEGUNDA", required = true)
            @RequestParam String diaSemana
    ){
        this.funcionamentoService.delete(quadraId, diaSemana);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Funcionamento para o dia selecionado foi deletado com sucesso."
        );
    }
}
