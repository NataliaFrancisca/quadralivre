package br.com.nat.quadralivre.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class HorarioDisponivelDTO {
    @NotNull(message = "É necessário indicar o ID da quadra.")
    private Long quadraId;

    @NotNull(message = "Data é obrigatório.")
    @FutureOrPresent(message = "A data deve ser uma data no presente, ou no futuro.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Valid
    private LocalDate data;
}
