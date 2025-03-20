package br.com.nat.quadralivre.dto;

import br.com.nat.quadralivre.model.Gestor;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class QuadraDTO {
    @Hidden
    private Long id;

    @NotBlank(message = "Titulo para quadra é obrigatório.")
    private String titulo;

    @NotNull(message = "Endereço é obrigatório.")
    @Valid
    private EnderecoDTO endereco;

    @NotNull(message = "ID do gestor é obrigatório.")
    private Long gestor_id;

    @Hidden
    private Gestor gestor;
}
