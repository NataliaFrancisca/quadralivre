package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.model.Endereco;
import br.com.nat.quadralivre.model.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {
    boolean existsByEndereco(Endereco endereco);
    boolean existsByGestorEmail(String email);
    boolean existsByTitulo(String titulo);
    Optional<Quadra> findByTitulo(String titulo);
    Optional<List<Quadra>> findAllByGestorEmail(String email);
}
