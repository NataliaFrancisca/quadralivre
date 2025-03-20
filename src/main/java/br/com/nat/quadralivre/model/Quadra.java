package br.com.nat.quadralivre.model;

import br.com.nat.quadralivre.dto.QuadraDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "Quadra")
@Table(name = "Quadra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Quadra", description = "Operações relacionadas a quadra.")
public class Quadra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Long gestor_id;

    @ManyToOne
    @JoinColumn(name = "gestor_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Gestor gestor;

    @Column(nullable = false)
    @Embedded
    private Endereco endereco;

    public Quadra(QuadraDTO quadraDTO) {
    }
}
