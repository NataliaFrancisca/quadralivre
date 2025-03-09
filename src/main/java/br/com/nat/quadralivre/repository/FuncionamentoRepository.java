package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.model.Funcionamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionamentoRepository extends JpaRepository<Funcionamento, Long> {
    List<Funcionamento> findAllByQuadraId(Long quadraId);
}
