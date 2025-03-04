package br.com.nat.quadralivre.dto;

import br.com.nat.quadralivre.enums.Estados;
import br.com.nat.quadralivre.model.Endereco;
import br.com.nat.quadralivre.util.Estado;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    @NotNull(message = "O local é obrigatório.")
    private String local;

    @NotNull(message = "A rua é obrigatória.")
    private String rua;

    @NotNull(message = "O número da rua é obrigatório.")
    private String numero;

    @NotNull(message = "O bairro é obrigatório.")
    private String bairro;

    @NotNull(message = "A cidade é obrigatória.")
    private String cidade;

    @NotNull(message = "O estado é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Estado(message = "Digite um estado brasileiro válido. Ex.: SP ou RJ")
    private Estados estado;

    public static Endereco toEndereco(EnderecoDTO enderecoDTO){
        if(enderecoDTO == null) return null;

        Endereco endereco = new Endereco();

        endereco.setLocal(enderecoDTO.getLocal());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());

        return endereco;
    }

    public static EnderecoDTO fromEndereco(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setLocal(endereco.getLocal());
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());

        return enderecoDTO;
    }
}
