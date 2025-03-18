package br.com.nat.quadralivre.dto;

import br.com.nat.quadralivre.model.Gestor;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class QuadraDTO {
    private Long id;

    @NotBlank(message = "Titulo para quadra é obrigatório.")
    private String titulo;

    @NotNull(message = "Endereço é obrigatório.")
    @Valid
    private EnderecoDTO endereco;

    @NotNull(message = "ID do gestor é obrigatório.")
    private Long gestor_id;

    private Gestor gestor;
}
