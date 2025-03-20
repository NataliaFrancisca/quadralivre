package br.com.nat.quadralivre.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Responsável", description = "Operações relacionadas a responsável")
public class ResponsavelSimplesDTO {
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Número para contato é obrigatório.")
    @Pattern(regexp = "^\\d{2}\\s9?\\d{4}-\\d{4}$", message = "Número de telefone inválido. Use o formato 'DD' XXXX-XXXX ou 'DD 9XXXX-XXXX'.")
    private String telefone;

    @NotBlank(message = "Endereço de e-mail é obrigatório.")
    @Email
    private String email;
}
