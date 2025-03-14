package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserva")
public class ReservaController {
    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService){
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<RespostaAPI> create(@RequestBody @Valid ReservaDTO reservaDTO){
            return RespostaAPI.build(
                    HttpStatus.CREATED,
                    this.reservaService.create(reservaDTO)
            );
    }

    @GetMapping
    public ResponseEntity<RespostaAPI> getAll(){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getAll()
        );
    }

    @GetMapping("/{reservaId}")
    public ResponseEntity<RespostaAPI> getById(@PathVariable Long reservaId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getById(reservaId)
        );
    }

    @GetMapping("/quadra/{quadraId}")
    public ResponseEntity<RespostaAPI> getAllByQuadra(@PathVariable Long quadraId){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.reservaService.getAllByQuadra(quadraId)
        );
    }

    @DeleteMapping("/{reservaId}")
    public ResponseEntity<RespostaAPI> deleteById(@PathVariable Long reservaId){
        this.reservaService.deleteById(reservaId);

        return RespostaAPI.build(
                HttpStatus.OK,
                "Reserva deletada com sucesso."
        );
    }
}

