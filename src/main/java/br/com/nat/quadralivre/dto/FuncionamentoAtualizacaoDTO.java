package br.com.nat.quadralivre.dto;

import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.validation.DiasSemana;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionamentoAtualizacaoDTO {
    @NotNull(message = "É necessário indicar o ID da quadra.")
    private Long quadraId;

    @NotNull(message = "Dia da semana é obrigatório")
    @Enumerated(EnumType.STRING)
    @DiasSemana(message = "Digite um dia da semana válido. Ex.: SEGUNDA ou SEXTA")
    @Schema(description = "O dia da semana deve ser digitado em letras maiúsculas e sem acentos.", example = "SEGUNDA")
    private DiaSemana diaSemana;
}
