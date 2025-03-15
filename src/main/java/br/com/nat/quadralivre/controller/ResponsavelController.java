package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.ResponsavelDTO;
import br.com.nat.quadralivre.dto.ResponsavelSimplesDTO;
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
                this.responsavelService.create(responsavelDTO)
        );
    }

    @GetMapping
    public ResponseEntity<RespostaAPI> getByCPF(@RequestParam String cpf){
        return RespostaAPI.build(
              HttpStatus.OK,
              this.responsavelService.getByCPF(cpf)
        );
    }

    @PutMapping
    public ResponseEntity<RespostaAPI> updateByCPF(@RequestParam String cpf, @RequestBody @Valid ResponsavelSimplesDTO responsavelSimplesDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.responsavelService.update(cpf, responsavelSimplesDTO)
        );
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<RespostaAPI> deleteByCPF(@RequestParam String cpf){
        this.responsavelService.delete(cpf);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Responsavel deletado com sucesso."
        );
    }
}
