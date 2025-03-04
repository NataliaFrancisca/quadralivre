package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.GestorDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.GestorService;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestor")
public class GestorController {
    private final GestorService gestorService;

    @Autowired
    public GestorController(GestorService gestorService){
        this.gestorService = gestorService;
    }


    @PostMapping
    public ResponseEntity<RespostaAPI> create(@RequestBody GestorDTO gestorDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                "Gestor cadastrado com sucesso.",
                this.gestorService.create(GestorDTO.toEntity(gestorDTO))
        );
    }

    @GetMapping()
    public ResponseEntity<RespostaAPI> getByEmail(@RequestParam(required = false) @Email String email){

        if(email == null || email.isEmpty()){
            return RespostaAPI.build(
                    HttpStatus.OK,
                    this.gestorService.getAll()
            );
        }

        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.get(email)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaAPI> update(@PathVariable Long id, @RequestBody GestorDTO gestorDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.gestorService.update(id, GestorDTO.toEntity(gestorDTO))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> delete(@PathVariable Long id){
        this.gestorService.delete(id);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Gestor deletado com sucesso."
        );
    }
}
