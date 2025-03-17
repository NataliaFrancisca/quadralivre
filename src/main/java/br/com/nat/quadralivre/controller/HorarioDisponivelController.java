package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.HorarioDisponivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/horarios-disponiveis")
public class HorarioDisponivelController {
    private final HorarioDisponivelService horarioDisponivelService;

    @Autowired
    public HorarioDisponivelController(HorarioDisponivelService horarioDisponivelService){
        this.horarioDisponivelService = horarioDisponivelService;
    }

    @GetMapping
    public ResponseEntity<RespostaAPI> get(@RequestParam Long quadraId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data){
        return RespostaAPI.build(
                HttpStatus.OK,
                this.horarioDisponivelService.buscarHorariosDisponiveis(quadraId, data)
        );
    }
}
