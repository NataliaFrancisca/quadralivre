package br.com.nat.quadralivre.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class HorarioDisponivel {
    private LocalTime horarioInicio;
    private LocalTime horarioEncerramento;
}
