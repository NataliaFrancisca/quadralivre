package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.QuadraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

    @Operation(
            summary = "Cria uma nova quadra.",
            description = "Um gestor é responsável pela criação e manutenção da quadra.",
            responses = {
                    @ApiResponse(responseCode = "409", description = "Já existe uma quadra cadastrada nesse endereço."),
                    @ApiResponse(responseCode = "404", description = "Não existe gestor com esse número de ID."),
                    @ApiResponse(responseCode = "201", description = "Quadra cadastrada com sucesso.")
            }
    )
    @PostMapping
    public ResponseEntity<RespostaAPI> create(
            @Parameter(description = "Objeto com as informações da quadra",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuadraDTO.class)))
            @Valid @RequestBody QuadraDTO quadraDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                this.quadraService.create(quadraDTO)
        );
    }

    @Operation(
            summary = "Lista todas as quadras cadastradas",
            description = "Retorna um array com todas as quadras cadastradas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna um array com todas as quadras cadastradas")
            }
    )
    @GetMapping
    public ResponseEntity<RespostaAPI> getAll(){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.get()
        );
    }


    @Operation(
            summary = "Retorna quadra usando seu ID.",
            description = "Busca por uma quadra que tenha o ID que foi passado via parametro.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quadra encontrada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhuma quadra foi encontrada com esse ID."),
                    @ApiResponse(responseCode = "400", description = "Digite um ID que seja válido, ex.: 1 ou 2")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RespostaAPI> getById(
            @Parameter(description = "Número de identificação da quadra", required = true)
            @PathVariable Long id){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getById(id)
        );
    }

    @Operation(
            summary = "Retorna todas as quadras que foram cadastradas pelo gestor.",
            description = "Retorna um lista das quadras que foram cadastradas pelo gestor.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Nenhuma quadra foi cadastrada pelo gestor informado."),
                    @ApiResponse(responseCode = "200", description = "Retorna um array com todas as quadras que foram encontradas."),
                    @ApiResponse(responseCode = "400", description = "É necessário um e-mail válido para realizar a busca.")
            }
    )
    @GetMapping("/gestor")
    public ResponseEntity<RespostaAPI> getAllByGestor(
            @Parameter(description = "E-mail do gestor cadastrado", required = true)
            @RequestParam @Email String email){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getAllByEmail(email)
        );
    }

    @Operation(
            summary = "Atualiza os dados de uma quadra.",
            description = "Atualiza as informações de uma quadra com base no ID informado.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Nenhuma quadra foi encontrada com esse ID."),
                    @ApiResponse(responseCode = "200", description = "Quadra atualizada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "É necessário um id válido para realizar a busca."),
                    @ApiResponse(responseCode = "409", description = "Já existe uma quadra com os valores indicados.")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações atualizadas da quadra", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = QuadraDTO.class)
    ))
    @PutMapping("/{id}")
    public ResponseEntity<RespostaAPI> update(
            @Parameter(description = "ID da quadra.", required = true)
            @PathVariable Long id,
            @Valid @RequestBody QuadraDTO quadraDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.update(id, quadraDTO)
        );
    }

    @Operation(
            summary = "Deleta uma quadra",
            description = "Deleta uma quadra, baseado em seu ID.",
            responses = {
                    @ApiResponse(responseCode = "404", description = "Nenhuma quadra foi encontrada com esse ID."),
                    @ApiResponse(responseCode = "200", description = "Quadra deletada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "É necessário um id válido para realizar a busca.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> delete(
            @Parameter(description = "ID da quadra.", required = true)
            @PathVariable Long id){
        this.quadraService.delete(id);

        return RespostaAPI.build(
                HttpStatus.OK,
                "Quadra deletada com sucesso."
        );
    }
}
