package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.model.Funcionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FuncionamentoRepository extends JpaRepository<Funcionamento, Long> {
    Optional<Funcionamento> findAllByQuadraIdAndDiaSemana(Long quadraId, DiaSemana diaSemana);

    List<Funcionamento> findAllByQuadraId(Long quadraId);
    @Query(value = """
           SELECT f
           FROM Funcionamento f
           WHERE f.quadra_id = :quadraId
           ORDER BY
                CASE
                    WHEN f.diaSemana = 'SEGUNDA' THEN 1
                    WHEN f.diaSemana = 'TERCA' THEN 2
                    WHEN f.diaSemana = 'QUARTA' THEN 3
                    WHEN f.diaSemana = 'QUINTA' THEN 4
                    WHEN f.diaSemana = 'SEXTA' THEN 5
                    WHEN f.diaSemana = 'SABADO' THEN 6
                    WHEN f.diaSemana = 'DOMINGO' THEN 7
                END
           """)
    List<Funcionamento> findAllByQuadraIdOrderByDiaSemana(Long quadraId);
}
