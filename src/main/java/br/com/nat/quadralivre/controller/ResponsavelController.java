package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.ResponsavelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responsavel")
public class ResponsavelController {
    private final ResponsavelService responsavelService;

    @Autowired
    public ResponsavelController(ResponsavelService responsavelService){
        this.responsavelService = responsavelService;
    }

    @PostMapping
    public ResponseEntity<RespostaAPI> create(@Valid @RequestBody ResponsavelDTO responsavelDTO){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                "Responsável cadastrado com sucesso.",
                this.responsavelService.create(ResponsavelDTO.toEntity(responsavelDTO))
        );
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<RespostaAPI> getByCPF(@PathVariable String cpf){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.responsavelService.getByCPF(cpf)
        );
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<RespostaAPI> updateByCPF(@PathVariable String cpf, @RequestBody @Valid ResponsavelDTO responsavelDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                "Responsável atualizado com sucesso.",
                this.responsavelService.updateByCPF(cpf, ResponsavelDTO.toEntity(responsavelDTO))
        );
    }

    @DeleteMapping("/{cpf}")
    @Transactional
    public ResponseEntity<RespostaAPI> deleteByCPF(@PathVariable String cpf){
        this.responsavelService.delete(cpf);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Responsável deletado com sucesso."
        );
    }
}
