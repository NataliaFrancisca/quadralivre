package br.com.nat.quadralivre.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Reserva", description = "Operações relacionadas a reserva.")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quadraId;

    @Column(nullable = false)
    private String responsavelCPF;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private LocalTime horarioInicio;

    @Column(nullable = false)
    private LocalTime horarioEncerramento;

    @ManyToOne
    @JoinColumn(name = "responsavelCPF", nullable = false, referencedColumnName = "cpf", insertable = false, updatable = false)
    private Responsavel responsavel;

    @ManyToOne
    @JoinColumn(name = "quadraId", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Quadra quadra;
}