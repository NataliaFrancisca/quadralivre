package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.model.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GestorRepository extends JpaRepository<Gestor, Long> {
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    Optional<Gestor> findByEmail(String email);
}
