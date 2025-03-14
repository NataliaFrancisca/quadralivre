package br.com.nat.quadralivre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservasQuadraDTO {
    private QuadraDTO quadra;
    private List<ReservaSimplesDTO> reservas;
}
