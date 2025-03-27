package br.com.nat.quadralivre.dto;

import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.validation.DiasSemana;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuncionamentoDTO {
    @NotNull(message = "Dia da semana é obrigatório")
    @Enumerated(EnumType.STRING)
    @DiasSemana(message = "Digite um dia da semana válido. Ex.: SEGUNDA ou SEXTA")
    @Schema(description = "O dia da semana deve ser digitado em letras maiúsculas e sem acentos.", example = "SEGUNDA")
    private DiaSemana diaSemana;

    @NotNull(message = "Horário de abertura é obrigatório.")
    private LocalTime abertura;

    @NotNull(message = "Horário de fechamento é obrigatório.")
    private LocalTime fechamento;

    @Hidden
    private boolean disponibilidade = true;
}
