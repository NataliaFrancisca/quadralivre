package br.com.nat.quadralivre.model;

import br.com.nat.quadralivre.enums.DiaSemana;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "funcionamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Funcionamento", description = "Operações relacionadas a funcionamento.")
public class Funcionamento {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private LocalTime abertura;

    @Column(nullable = false)
    private LocalTime fechamento;

    @Column(nullable = false)
    private Boolean disponibilidade = true;

    @Column(name = "quadra_id", nullable = false)
    private Long quadraId;

    @ManyToOne
    @JoinColumn(name = "quadra_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Quadra quadra;
}
