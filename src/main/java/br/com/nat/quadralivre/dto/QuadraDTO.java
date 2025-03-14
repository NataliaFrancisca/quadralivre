package br.com.nat.quadralivre.dto;

import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.model.Quadra;

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

    public static Quadra toEntity(QuadraDTO quadraDTO){
        Quadra quadra = new Quadra();

        quadra.setId(quadraDTO.getId());
        quadra.setTitulo(quadraDTO.getTitulo());
        quadra.setGestor_id(quadraDTO.getGestor_id());
        quadra.setGestor(quadraDTO.getGestor());
        quadra.setEndereco(EnderecoDTO.toEndereco(quadraDTO.getEndereco()));

        return quadra;
    }

    public static QuadraDTO fromEntity(Quadra quadra){
        QuadraDTO quadraDTO = new QuadraDTO();

        quadraDTO.setId(quadra.getId());
        quadraDTO.setTitulo(quadra.getTitulo());
        quadraDTO.setGestor(quadra.getGestor());
        quadraDTO.setEndereco(EnderecoDTO.fromEndereco(quadra.getEndereco()));

        return quadraDTO;
    }

    public static QuadraDTO fromEntityShort(Quadra quadra){
        QuadraDTO quadraDTO = new QuadraDTO();

        quadraDTO.setTitulo(quadra.getTitulo());
        quadraDTO.setEndereco(EnderecoDTO.fromEndereco(quadra.getEndereco()));

        return quadraDTO;
    }
}
