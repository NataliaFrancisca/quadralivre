package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.infra.RespostaAPI;
import br.com.nat.quadralivre.service.HorarioDisponivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/horarios-disponiveis")
public class HorarioDisponivelController {
    private final HorarioDisponivelService horarioDisponivelService;

    @Autowired
    public HorarioDisponivelController(HorarioDisponivelService horarioDisponivelService){
        this.horarioDisponivelService = horarioDisponivelService;
    }

    @GetMapping("/quadra/{quadraId}/data/{data}")
    public ResponseEntity<RespostaAPI> getHorarioDisponiveis(@PathVariable Long quadraId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data){
        return RespostaAPI.build(
                HttpStatus.OK,
                horarioDisponivelService.buscarPorHorariosDisponiveis(quadraId, data)
        );
    }
}
