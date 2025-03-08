package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    boolean existsByCpf(String cpf);
    Optional<Responsavel> findByCpf(String cpf);
    void deleteByCpf(String cpf);
}
