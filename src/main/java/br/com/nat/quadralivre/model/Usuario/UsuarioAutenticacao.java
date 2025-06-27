package br.com.nat.quadralivre.model.Usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioAutenticacao(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
