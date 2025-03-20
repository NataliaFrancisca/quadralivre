package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.GestorDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.GestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestor")
@Tag(name = "Gestor", description = "Operações relacionadas a gestor.")
public class GestorController {
    private final GestorService gestorService;

    @Autowired
    public GestorController(GestorService gestorService){
        this.gestorService = gestorService;
    }

    @Operation(
            summary = "Cria um novo gestor.",
            description = "Cira um novo gestor que é o responsável pela quadra.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Gestor criado com sucesso."),
                    @ApiResponse(responseCode = "409", description = "Já existe um cadastro com algum dos valores informados."),
                    @ApiResponse(responseCode = "400", description = "É necessário que os campos sejam preenchidos corretamente.")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações do Gestor", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = GestorDTO.class)
    ))
    @PostMapping
    public ResponseEntity<RespostaAPI> create(
            @RequestBody GestorDTO gestorDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                "Gestor cadastrado com sucesso.",
                this.gestorService.create(GestorDTO.toEntity(gestorDTO))
        );
    }

    @Operation(
            summary = "Retorna um gestor usando seu e-mail.",
            description = "Busca por um gestor que tenha o e-mail que foi passado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna o gestor com o e-mail indicado."),
                    @ApiResponse(responseCode = "404", description = "Não existe gestor cadastrado com esse endereço de e-mail."),
                    @ApiResponse(responseCode = "400", description = "É ncessário um e-mail válido para fazer a busca.")
            }
    )
    @GetMapping("/email")
    public ResponseEntity<RespostaAPI> getByEmail(
            @Parameter(description = "Digite um e-mail do gestor válido.", required = true)
            @RequestParam @Email String valor){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.get(valor)
        );
    }

    @Operation(
            summary = "Retorna todos os gestores cadastrados.",
            description = "Busca por todos os gestores cadastrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os gestores"),
                    @ApiResponse(responseCode = "404", description = "Não existe nenhum gestor cadastrado."),
            }
    )
    @GetMapping
    public ResponseEntity<RespostaAPI> getAll(){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.getAll()
        );
    }

    @Operation(
            summary = "Atualiza os dados de um gestor.",
            description = "Atualiza os dados de um gestor beseado em seu ID.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Digite um id válido para fazer a busca."),
                    @ApiResponse(responseCode = "404", description = "Não existe cadastro com esse número de id."),
                    @ApiResponse(responseCode = "409", description = "Algum dos campos têm valores que pertencem a outro cadastro."),
                    @ApiResponse(responseCode = "200", description = "Gestor atualizado com sucesso.")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com as informações atualizadas do Gestor", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = GestorDTO.class)
    ))
    @PutMapping("/{id}")
    public ResponseEntity<RespostaAPI> update(
            @Parameter(description = "ID do gestor", required = true)
            @PathVariable Long id, @RequestBody GestorDTO gestorDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.update(id, GestorDTO.toEntity(gestorDTO))
        );
    }

    @Operation(
            summary = "Deleta os dados de um gestor.",
            description = "Deleta os dados de um gestor baseada em seu ID.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Digite um id válido para fazer a busca."),
                    @ApiResponse(responseCode = "404", description = "Não existe cadastro com esse número de id."),
                    @ApiResponse(responseCode = "200", description = "Gestor deletado com sucesso.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> delete(
            @Parameter(description = "ID do gestor", required = true)
            @PathVariable Long id){
        this.gestorService.delete(id);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Gestor deletado com sucesso."
        );
    }
}
