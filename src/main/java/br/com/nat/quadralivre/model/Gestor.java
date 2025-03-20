package br.com.nat.quadralivre.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Gestor")
@Table(name = "Gestor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Gestor", description = "Operações relacionadas a gestor.")
public class Gestor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String telefone;
}
