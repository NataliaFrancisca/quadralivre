package br.com.nat.quadralivre.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Tag(name = "Reserva", description = "Operações relacionadas a reserva.")
public class ReservaSimplesDTO {
    private Long id;
    private LocalDateTime data;
    private LocalTime horarioInicio;
    private LocalTime horarioEncerramento;
    private ResponsavelDTO responsavel;
}
