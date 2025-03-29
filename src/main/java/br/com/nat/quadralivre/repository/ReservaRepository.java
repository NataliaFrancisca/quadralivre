package br.com.nat.quadralivre.repository;

import br.com.nat.quadralivre.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findAllByQuadraIdAndDataBetween(Long quadraId, LocalDateTime dataInicio, LocalDateTime dataFim);
    List<Reserva> findAllByQuadraIdAndData(Long quadraId, LocalDateTime data);
    List<Reserva> findAllByQuadraIdOrderByData(Long quadraId);
}
