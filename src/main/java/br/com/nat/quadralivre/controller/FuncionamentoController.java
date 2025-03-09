package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.FuncionamentoDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.FuncionamentoService;
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

    @PostMapping("/{id}")
    public ResponseEntity<RespostaAPI> create(@PathVariable Long id, @RequestBody List<FuncionamentoDTO> funcionamentoDTOS){
        this.funcionamentoService.create(id, funcionamentoDTOS);

        return RespostaAPI.build(
                HttpStatus.CREATED,
                "Dias da semana cadastrada com sucesso."
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaAPI> getAll(@PathVariable Long id){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.getAll(id)
        );
    }

    @PutMapping()
    public ResponseEntity<RespostaAPI> update(@RequestBody FuncionamentoDTO funcionamentoDTO){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.funcionamentoService.update(funcionamentoDTO)
        );
    }

    @PatchMapping("/{id}/atualizar-disponibilidade")
    public ResponseEntity<RespostaAPI> updateDisponibilidade(@PathVariable Long id, @RequestParam boolean disponibilidade){
        return RespostaAPI.build(
                HttpStatus.OK,
                disponibilidade ? "Acesso à reservas liberadas com sucesso" : "Acesso à reservas bloqueadas com sucesso.",
                this.funcionamentoService.updateDisponibilidade(id, disponibilidade)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaAPI> delete(@PathVariable Long id){
        this.funcionamentoService.delete(id);
        return RespostaAPI.build(
                HttpStatus.OK,
                "Dia da semana deletado com sucesso."
        );
    }
}
