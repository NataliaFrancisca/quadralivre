package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.model.Endereco;
import br.com.nat.quadralivre.model.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {
    boolean existsByTitulo(String titulo);
    boolean existsByGestorId(Long id);
    boolean existsByTituloAndEndereco(String titulo, Endereco endereco);
    List<Quadra> findAllByGestorEmail(String email);
}
