package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.dto.ResponsavelSimplesDTO;
import br.com.nat.quadralivre.infra.CorpoRequisicao;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.infra.RespostasComuns;
import br.com.nat.quadralivre.service.ResponsavelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Cria um novo responsável", description = "Cria uma nova pessoa responsável pela reserva da quadra.")
    @RespostasComuns
    @CorpoRequisicao(descricao = "Objeto com as informações do responsável.", dto = ResponsavelDTO.class)
    @PostMapping
    public ResponseEntity<RespostaAPI> create(@Valid @RequestBody ResponsavelDTO responsavelDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                this.responsavelService.create(responsavelDTO)
        );
    }

    @Operation(summary = "Busca por um responsável", description = "Busca por um responsável, usando o CPF como identificador.")
    @RespostasComuns
    @GetMapping("/cpf")
    public ResponseEntity<RespostaAPI> getByCPF(@Parameter(description = "CPF do responsável.") @RequestParam String valor){
        return RespostaAPI.build(
              HttpStatus.OK,
              this.responsavelService.getByCPF(valor)
        );
    }

    @Operation(summary = "Atualizar os dados de um um responsável.", description = "Atualiza os dados de um responsável baseado em seu CPF.")
    @RespostasComuns
    @CorpoRequisicao(descricao = "Objeto com as informações atualizadas do responsável.", dto = ResponsavelSimplesDTO.class)
    @PutMapping("/cpf")
    public ResponseEntity<RespostaAPI> updateByCPF(
            @Parameter(description = "CPF do responsável.", required = true) @RequestParam String valor,
            @Valid @RequestBody ResponsavelSimplesDTO responsavelSimplesDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.responsavelService.update(valor, responsavelSimplesDTO)
        );
    }

    @Operation(summary = "Deletar os dados de um responsável.", description = "Deletar um responsável.")
    @RespostasComuns
    @DeleteMapping("/cpf")
    @Transactional
    public ResponseEntity<RespostaAPI> deleteByCPF(
            @Parameter(description = "CPF do responsável.") @RequestParam String valor){
        this.responsavelService.delete(valor);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Responsável deletado com sucesso."
        );
    }
}
