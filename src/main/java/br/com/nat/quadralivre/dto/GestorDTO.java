package br.com.nat.quadralivre.dto;

import io.swagger.v3.oas.annotations.Hidden;
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
@Tag(name = "Gestor", description = "Operações relacionadas a gestor.")
public class GestorDTO {
    @Hidden
    private Long id;

    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Endereço de e-mail é obrigatório.")
    @Email(message = "Digite um e-mail válido.")
    private String email;

    @NotBlank(message = "Número para contato é obrigatório.")
    @Pattern(regexp = "^\\d{2}\\s9?\\d{4}-\\d{4}$", message = "Número de telefone inválido. Use o formato 'DD' XXXX-XXXX ou 'DD 9XXXX-XXXX'.")
    private String telefone;
}
