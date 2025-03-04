package br.com.nat.quadralivre.model;

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

    public Quadra(Quadra quadra, Gestor gestor){
        this.id = quadra.getId();
        this.titulo = quadra.getTitulo();
        this.gestor_id = quadra.getGestor_id();
        this.endereco = quadra.getEndereco();
        this.gestor = gestor;
    }
}
