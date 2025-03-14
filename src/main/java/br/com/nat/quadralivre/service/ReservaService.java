package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.*;
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

import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ValidacaoReserva validacaoReserva;
    private final QuadraRepository quadraRepository;
    private final ReservaMapper reservaMapper;
    private final ReservaFinderService reservaFinderService;

    @Autowired
    public ReservaService(
            ReservaRepository reservaRepository,
            ValidacaoReserva validacaoReserva,
            QuadraRepository quadraRepository,
            ReservaMapper reservaMapper,
            ReservaFinderService reservaFinderService
    ){
        this.reservaRepository = reservaRepository;
        this.validacaoReserva = validacaoReserva;
        this.quadraRepository = quadraRepository;
        this.reservaMapper = reservaMapper;
        this.reservaFinderService = reservaFinderService;
    }

    private Reserva criarEntidadeReserva(ReservaDTO reservaDTO, HorarioDisponivel horarioReserva){
        Reserva reserva = new Reserva();

        reserva.setQuadraId(reservaDTO.getQuadraId());
        reserva.setResponsavelCPF(reservaDTO.getResponsavelCPF());
        reserva.setData(reservaDTO.getDataSolicitada().atTime(horarioReserva.getHorarioInicio()));
        reserva.setHorarioInicio(horarioReserva.getHorarioInicio());
        reserva.setHorarioEncerramento(horarioReserva.getHorarioEncerramento());

        return reserva;
    }

    public ReservaCompletaDTO create(ReservaDTO reservaDTO){
        this.validacaoReserva.validarReserva(reservaDTO);

        HorarioDisponivel horarioReserva = this.reservaFinderService.buscarHorarioReserva(reservaDTO);
        Reserva reserva = this.criarEntidadeReserva(reservaDTO, horarioReserva);

        Responsavel responsavel = this.reservaFinderService.buscarResponsavel(reserva.getResponsavelCPF());
        Quadra quadra = this.reservaFinderService.buscarQuadra(reserva.getQuadraId());

        reserva.setResponsavel(responsavel);
        reserva.setQuadra(quadra);

        Reserva reservaParaSalvarNoBD = this.reservaRepository.save(reserva);
        return this.reservaMapper.toDTOCompleto(reservaParaSalvarNoBD);
    }

    public List<ReservaCompletaDTO> getAll(){
        return this.reservaRepository.findAll().stream().map(this.reservaMapper::toDTOCompleto).toList();
    }

    public ReservaCompletaDTO getById(Long id){
        Reserva reserva = this.reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe reserva com esse número de ID."));

        return this.reservaMapper.toDTOCompleto(reserva);
    }

    public ReservasQuadraDTO getAllByQuadra(Long quadraId){
        Quadra quadra = this.quadraRepository.findById(quadraId)
                .orElseThrow(() -> new EntityNotFoundException("Não existe quadra com esse número de ID."));

        List<ReservaSimplesDTO> reservas = this.reservaRepository.findByQuadraIdOrderByData(quadraId)
                .stream()
                .map(this.reservaMapper::toDTOSimples)
                .toList();

        if(reservas.isEmpty()){
            throw new EntityNotFoundException("Não existe reservas para essa quadra.");
        }

        return new ReservasQuadraDTO(QuadraDTO.fromEntityShort(quadra), reservas);
    }

    public void deleteById(Long id){
        try{
            this.reservaRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new EntityNotFoundException("Não existe reserva com esse número de ID.");
        }
    }
}
