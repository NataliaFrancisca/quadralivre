package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.model.Endereco;
import br.com.nat.quadralivre.model.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {
    boolean existsByEndereco(Endereco endereco);
    boolean existsByTitulo(String titulo);
    List<Quadra> findAllByGestorEmail(String email);
}
