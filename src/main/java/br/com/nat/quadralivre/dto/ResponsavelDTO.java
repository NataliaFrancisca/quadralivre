package br.com.nat.quadralivre.dto;

import br.com.nat.quadralivre.model.Responsavel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsavelDTO {
    @NotBlank(message = "CPF é obrigatório.")
    @CPF
    private String cpf;

    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Número para contato é obrigatório.")
    @Pattern(regexp = "^\\d{2}\\s9?\\d{4}-\\d{4}$", message = "Número de telefone inválido. Use o formato 'DD' XXXX-XXXX ou 'DD 9XXXX-XXXX'.")
    private String telefone;

    @NotBlank(message = "Endereço de e-mail é obrigatório.")
    @Email
    private String email;

    public static Responsavel toEntity(ResponsavelDTO responsavelDTO){
        Responsavel responsavel = new Responsavel();

        responsavel.setCpf(responsavelDTO.getCpf());
        responsavel.setNome(responsavelDTO.getNome());
        responsavel.setTelefone(responsavelDTO.getTelefone());
        responsavel.setEmail(responsavelDTO.getEmail());

        return responsavel;
    }

    public static ResponsavelDTO fromEntity(Responsavel responsavel){
        ResponsavelDTO responsavelDTO = new ResponsavelDTO();

        responsavelDTO.setCpf(responsavel.getCpf());
        responsavelDTO.setNome(responsavel.getNome());
        responsavelDTO.setTelefone(responsavel.getTelefone());
        responsavelDTO.setEmail(responsavel.getEmail());

        return responsavelDTO;
    }
}
