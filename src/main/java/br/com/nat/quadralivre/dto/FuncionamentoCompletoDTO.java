package br.com.nat.quadralivre.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionamentoCompletoDTO extends FuncionamentoDTO {
    @NotNull(message = "Id da quadra é obrigatório")
    private Long quadraId;
}
