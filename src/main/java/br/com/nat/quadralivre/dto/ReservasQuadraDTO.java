package br.com.nat.quadralivre.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Reserva", description = "Operações relacionadas a reserva.")
public class ReservasQuadraDTO {
    private QuadraDTO quadra;
    private List<ReservaSimplesDTO> reservas;
}
