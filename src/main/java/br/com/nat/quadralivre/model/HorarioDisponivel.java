package br.com.nat.quadralivre.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@Tag(name = "Horário Disponivel", description = "Operação relacionada com horário disponivel.")
public class HorarioDisponivel {
    private LocalTime horarioInicio;
    private LocalTime horarioEncerramento;
}
