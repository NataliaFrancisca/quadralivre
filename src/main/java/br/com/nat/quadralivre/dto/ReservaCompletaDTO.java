package br.com.nat.quadralivre.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ReservaCompletaDTO {
    private Long id;
    private LocalDateTime data;
    private LocalTime horarioInicio;
    private LocalTime horarioEncerramento;
    private ResponsavelDTO responsavel;
    private QuadraDTO quadra;
}
