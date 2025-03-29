package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.*;
import br.com.nat.quadralivre.mapper.QuadraMapper;
import br.com.nat.quadralivre.mapper.ReservaMapper;
import br.com.nat.quadralivre.model.HorarioDisponivel;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.model.Reserva;
import br.com.nat.quadralivre.model.Responsavel;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.repository.ReservaRepository;
import br.com.nat.quadralivre.service.finder.ReservaFinderService;
import br.com.nat.quadralivre.service.validacao.ValidacaoReserva;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ValidacaoReserva validacaoReserva;
    private final QuadraRepository quadraRepository;
    private final ReservaMapper reservaMapper;
    private final QuadraMapper quadraMapper;
    private final ReservaFinderService reservaFinderService;

    @Autowired
    public ReservaService(
            ReservaRepository reservaRepository,
            ValidacaoReserva validacaoReserva,
            QuadraRepository quadraRepository,
            ReservaMapper reservaMapper,
            ReservaFinderService reservaFinderService,
            QuadraMapper quadraMapper
    ){
        this.reservaRepository = reservaRepository;
        this.validacaoReserva = validacaoReserva;
        this.quadraRepository = quadraRepository;
        this.reservaMapper = reservaMapper;
        this.reservaFinderService = reservaFinderService;
        this.quadraMapper = quadraMapper;
    }

    public ReservaSimplesDTO create(ReservaDTO reservaDTO){
        this.validacaoReserva.validarReserva(reservaDTO);

        HorarioDisponivel horarioReserva = this.reservaFinderService.buscarHorarioReserva(reservaDTO);

        Reserva reserva = this.reservaMapper.toReservaEntidade(reservaDTO, horarioReserva);

        Responsavel responsavel = this.reservaFinderService.buscarResponsavel(reservaDTO.getResponsavelCPF());
        Quadra quadra = this.reservaFinderService.buscarQuadra(reservaDTO.getQuadraId());

        reserva.setResponsavel(responsavel);
        reserva.setQuadra(quadra);

        Reserva reservaSalva = this.reservaRepository.save(reserva);

        return this.reservaMapper.toDTOSimples(reservaSalva);
    }

    public ReservaCompletaDTO getById(Long id){
        Reserva reserva = this.reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe reserva com esse número de ID."));

        return this.reservaMapper.toDTOCompleto(reserva);
    }

    public ReservasQuadraDTO getAllByQuadra(Long quadraId){
        Quadra quadra = this.quadraRepository.findById(quadraId)
                .orElseThrow(() -> new EntityNotFoundException("Não existe quadra com esse número de ID."));

        List<ReservaSimplesDTO> reservas = this.reservaRepository.findAllByQuadraIdOrderByData(quadraId)
                .stream()
                .map(this.reservaMapper::toDTOSimples)
                .filter(dto -> dto.getData().isAfter(LocalDateTime.now()))
                .toList();

        if(reservas.isEmpty()){
            throw new EntityNotFoundException("Não existe reservas para essa quadra.");
        }

        return new ReservasQuadraDTO(this.quadraMapper.toDTOSimples(quadra), reservas);
    }

    public void deleteById(Long id){
        try{
            this.reservaRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new EntityNotFoundException("Não existe reserva com esse número de ID.");
        }
    }
}
