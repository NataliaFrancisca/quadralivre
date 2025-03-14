package br.com.nat.quadralivre.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    @NotNull(message = "Necessário digitar o ID da quadra escolhida.")
    private Long quadraId;

    @NotNull(message = "Necessário digitar o CPF do responsável pela reserva.")
    @CPF(message = "Digite um CPF válido.")
    private String responsavelCPF;

    @NotNull(message = "Necessário digitar um id.")
    private int horarioDisponivelId;

    @NotNull
    @FutureOrPresent(message = "A data solicitada deve ser no presente ou futuro.")
    private LocalDate dataSolicitada;
}
