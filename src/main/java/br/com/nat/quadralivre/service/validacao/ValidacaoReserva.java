package br.com.nat.quadralivre.service.validacao;

import br.com.nat.quadralivre.dto.ReservaDTO;
import br.com.nat.quadralivre.helper.ReservaHelper;
import br.com.nat.quadralivre.model.Reserva;
import br.com.nat.quadralivre.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ValidacaoReserva {
    private final ReservaRepository reservaRepository;
    private final ReservaHelper reservaHelper;

    @Autowired
    public ValidacaoReserva(ReservaRepository reservaRepository, ReservaHelper reservaHelper){
        this.reservaRepository = reservaRepository;
        this.reservaHelper = reservaHelper;
    }

    public void validarReserva(ReservaDTO reservaDTO){
        LocalDateTime dataInicioDoDia = this.reservaHelper.encontrarProximaData(reservaDTO.getDataSolicitada());
        LocalDateTime dataFimDoDia = dataInicioDoDia.toLocalDate().atTime(23, 59,59);

        List<Reserva> reservas = this.reservaRepository.findAllByQuadraIdAndDataBetween(
            reservaDTO.getQuadraId(), dataInicioDoDia, dataFimDoDia
        );

        boolean CPFJaUtilizado = reservas.stream()
                .anyMatch(reserva -> reserva.getResponsavelCPF().equals(reservaDTO.getResponsavelCPF()));

        if(CPFJaUtilizado){
            throw new DataIntegrityViolationException("Já existe uma reserva nesse dia para esse número de CPF");
        }
    }
}
