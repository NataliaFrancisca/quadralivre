package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.QuadraAtualizacaoDTO;
import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.infra.CorpoRequisicao;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.infra.RespostasComuns;
import br.com.nat.quadralivre.service.QuadraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quadra")
@Tag(name = "Quadra", description = "Operações relacionadas a quadra.")
public class QuadraController {
    private final QuadraService quadraService;

    @Autowired
    public QuadraController(QuadraService quadraService){
        this.quadraService = quadraService;
    }

    @Operation(summary = "Cria uma quadra.", description = "Um gestor é responsável pela criação da quadra.")
    @RespostasComuns
    @CorpoRequisicao(descricao = "Objeto com as informações da Quadra.", dto = QuadraDTO.class)
    @PostMapping
    public ResponseEntity<RespostaAPI> create(@Valid @RequestBody QuadraDTO quadraDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                this.quadraService.create(quadraDTO)
        );
    }

    @Operation(summary = "Lista todas as quadras cadastradas.", description = "Retorna uma lista com todas as quadras cadastradas.")
    @RespostasComuns
    @GetMapping
    public ResponseEntity<RespostaAPI> getAll(){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.get()
        );
    }

    @Operation(summary = "Retorna uma quadra usando seu ID.", description = "Busca por uma quadra que tenha o ID que foi indicado.")
    @RespostasComuns
    @GetMapping("/{id}")
    public ResponseEntity<RespostaAPI> getById(
            @Parameter(description = "Número do ID da quadra", required = true) @Valid @PathVariable Long id){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getById(id)
        );
    }

    @Operation(summary = "Retorna quadras que estão associadas à um gestor.", description = "Retorna uma lista de quadras que estão associadas à um gestor.")
    @RespostasComuns
    @GetMapping("/gestor")
    public ResponseEntity<RespostaAPI> getAllByGestor(
            @Parameter(description = "E-mail do gestor.", required = true) @Valid @RequestParam String email){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getAllByEmail(email)
        );
    }

    @Operation(summary = "Atualiza os dados de uma quadra.", description = "Atualiza as informações de uma quadra com base no ID informado.")
    @RespostasComuns
    @CorpoRequisicao(descricao = "Objeto com as informações atualizadas da quadra.", dto = QuadraAtualizacaoDTO.class)
    @PutMapping
    public ResponseEntity<RespostaAPI> update(@Parameter(description = "ID da quadra.", required = true)
                                              @Valid @RequestBody QuadraAtualizacaoDTO quadraDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.update(quadraDTO)
        );
    }

    @Operation(summary = "Deleta uma quadra", description = "Deleta uma quadra baseado no ID informado.")
    @RespostasComuns
    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> delete(@Parameter(description = "ID da quadra.", required = true) @PathVariable Long id){
        this.quadraService.delete(id);

        return RespostaAPI.build(
                HttpStatus.OK,
                "Quadra deletada com sucesso."
        );
    }
}
