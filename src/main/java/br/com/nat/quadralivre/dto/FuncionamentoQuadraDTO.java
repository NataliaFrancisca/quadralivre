package br.com.nat.quadralivre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionamentoQuadraDTO {
    private Long quadraId;
    private List<FuncionamentoDTO> funcionamento;
}
