package br.com.nat.quadralivre.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Tag(name = "Quadra", description = "Operações relacionadas a quadra.")
public class QuadraAtualizacaoDTO extends QuadraDTO {
    @NotNull(message = "ID da quadra é obrigatório.")
    private Long id;
}
