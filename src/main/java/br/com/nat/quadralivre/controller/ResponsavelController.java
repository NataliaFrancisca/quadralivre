package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.dto.ResponsavelSimplesDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.ResponsavelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responsavel")
@Tag(name = "Responsável", description = "Operações relacionadas a responsável")
public class ResponsavelController {
    private final ResponsavelService responsavelService;

    @Autowired
    public ResponsavelController(ResponsavelService responsavelService){
        this.responsavelService = responsavelService;
    }

    @Operation(
            summary = "Cria um novo responsável",
            description = "Cria uma nova pessoa responsável pela reserva da quadra.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Responsável criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespostaAPI.class))),
                    @ApiResponse(responseCode = "409", description = "Já existe um cadastro com algum dos valores informados."),
                    @ApiResponse(responseCode = "400", description = "É necessário que os campos sejam preenchidos corretamente.")
            }
    )
    @PostMapping
    public ResponseEntity<RespostaAPI> create(@Parameter(description = "Objeto com as informações do responsável", required = true) @Valid @RequestBody ResponsavelDTO responsavelDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                this.responsavelService.create(responsavelDTO)
        );
    }

    @Operation(
            summary = "Busca por um responsável",
            description = "Busca por um responsável, usando o CPF como identificador.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Digite um CPF válido para fazer a busca."),
                    @ApiResponse(responseCode = "404", description = "Não existe cadastro com esse número de CPF."),
                    @ApiResponse(responseCode = "200", description = "Responsável encontrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespostaAPI.class))),
            }
    )
    @GetMapping
    public ResponseEntity<RespostaAPI> getByCPF(@Parameter(description = "CPF do responsável em formato de texto, e somente os números.") @RequestParam String cpf){
        return RespostaAPI.build(
              HttpStatus.OK,
              this.responsavelService.getByCPF(cpf)
        );
    }

    @Operation(
            summary = "Atualizar os dados de um um responsável.",
            description = "Atualiza os dados de um responsável baseado em seu CPF.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Digite um CPF válido para fazer a busca."),
                    @ApiResponse(responseCode = "404", description = "Não existe cadastro com esse número de CPF."),
                    @ApiResponse(responseCode = "409", description = "Algum dos campos têm valores que pertencem a outro cadastro."),
                    @ApiResponse(responseCode = "200", description = "Responsável atualizado com sucesso.")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações atualizadas do responsável", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponsavelSimplesDTO.class)
    ))
    @PutMapping
    public ResponseEntity<RespostaAPI> updateByCPF(
            @Parameter(description = "CPF do responsável em formato de texto, e somente os números.", required = true) @RequestParam String cpf,
            @Valid @RequestBody ResponsavelSimplesDTO responsavelSimplesDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.responsavelService.update(cpf, responsavelSimplesDTO)
        );
    }

    @Operation(
            summary = "Deletar os dados de um responsável.",
            description = "Deletar um responsável.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Digite um CPF válido para fazer a busca."),
                    @ApiResponse(responseCode = "404", description = "Não existe cadastro com esse número de CPF."),
                    @ApiResponse(responseCode = "200", description = "Responsável deletado com sucesso.")
            }
    )
    @DeleteMapping
    @Transactional
    public ResponseEntity<RespostaAPI> deleteByCPF(@Parameter(description = "CPF do responsável em formato de texto, e somente os números.") @RequestParam String cpf){
        this.responsavelService.delete(cpf);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Responsavel deletado com sucesso."
        );
    }
}
