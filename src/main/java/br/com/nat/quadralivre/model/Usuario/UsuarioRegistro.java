package br.com.nat.quadralivre.model.Usuario;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioRegistro(
        @NotBlank
        String login,
        @NotBlank
        String senha,
        @NotBlank
        String telefone,
        @NotBlank
        @CPF
        String cpf
) {
}
