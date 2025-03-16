package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.FuncionamentoDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.FuncionamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionamento")
public class FuncionamentoController {
    private final FuncionamentoService funcionamentoService;

    @Autowired
    public FuncionamentoController(FuncionamentoService funcionamentoService){
        this.funcionamentoService = funcionamentoService;
    }

    @PostMapping()
    public ResponseEntity<RespostaAPI> create(@RequestParam Long quadraId, @RequestBody List<FuncionamentoDTO> funcionamentoDTOS){
        return RespostaAPI.build(
                HttpStatus.CREATED,
                this.funcionamentoService.create(quadraId, funcionamentoDTOS)
        );
    }

    @GetMapping
    public ResponseEntity<RespostaAPI> get(@RequestParam Long quadraId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.get(quadraId)
        );
    }

    @PutMapping()
    public ResponseEntity<RespostaAPI> update(@Valid @RequestBody FuncionamentoDTO funcionamentoDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.update(funcionamentoDTO)
        );
    }

    @PatchMapping
    public ResponseEntity<RespostaAPI> updateDisponibilidade(@RequestParam Long quadraId, @RequestParam String diaSemana){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.updateDisponibilidade(quadraId, diaSemana)
        );
    }

    @DeleteMapping
    public ResponseEntity<RespostaAPI> delete(@RequestParam Long quadraId, @RequestParam String diaSemana){
        this.funcionamentoService.delete(quadraId, diaSemana);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Funcionamento para o dia selecionado foi deletado com sucesso."
        );
    }
}
