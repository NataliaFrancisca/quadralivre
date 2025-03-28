package br.com.nat.quadralivre.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Tag(name = "Horário Disponivel", description = "Operação relacionada com horário disponivel.")
public class HorarioDisponivel {
    private int id;
    private LocalTime horarioInicio;
    private LocalTime horarioEncerramento;
}
