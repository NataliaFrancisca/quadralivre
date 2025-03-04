package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.QuadraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quadra")
public class QuadraController {
    private final QuadraService quadraService;

    @Autowired
    public QuadraController(QuadraService quadraService){
        this.quadraService = quadraService;
    }

    @PostMapping
    public ResponseEntity<RespostaAPI> create(@Valid @RequestBody QuadraDTO quadraDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                "Quadra cadastrada com sucesso.",
                this.quadraService.create(QuadraDTO.toEntity(quadraDTO))
        );
    }

    @GetMapping()
    public ResponseEntity<RespostaAPI> getAllByEmail(@RequestParam(required = false) @Email String email){

        if(email == null || email.isBlank()){
            return RespostaAPI.build(
                    HttpStatus.OK,
                    this.quadraService.get()
            );
        }

        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getAllByEmail(email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaAPI> getById(@PathVariable Long id){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaAPI> update(@PathVariable Long id, @Valid @RequestBody QuadraDTO quadraDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.update(id, QuadraDTO.toEntity(quadraDTO))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> delete(@PathVariable Long id){
        this.quadraService.delete(id);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Quadra deletada com sucesso."
        );
    }
}
