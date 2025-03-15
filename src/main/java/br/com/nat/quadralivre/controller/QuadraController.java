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
                this.quadraService.create(quadraDTO)
        );
    }

    @GetMapping
    public ResponseEntity<RespostaAPI> getAll(){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.get()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaAPI> getById(@PathVariable Long id){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getById(id)
        );
    }

    @GetMapping("/gestor")
    public ResponseEntity<RespostaAPI> getAllByGestor(@RequestParam @Email String email){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.getAllByEmail(email)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaAPI> update(@PathVariable Long id, @Valid @RequestBody QuadraDTO quadraDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.quadraService.update(id, quadraDTO)
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
