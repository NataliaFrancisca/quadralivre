package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.FuncionamentoDTO;
import br.com.nat.quadralivre.dto.FuncionamentoQuadraDTO;
import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.helper.ReservaHelper;
import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.model.Reserva;
import br.com.nat.quadralivre.repository.FuncionamentoRepository;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.repository.ReservaRepository;
import br.com.nat.quadralivre.service.finder.FuncionamentoFinderService;
import br.com.nat.quadralivre.util.DiaSemanaUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class FuncionamentoService {
    final private FuncionamentoRepository funcionamentoRepository;
    final private QuadraRepository quadraRepository;
    final private FuncionamentoFinderService funcionamentoFinderService;
    final private ReservaRepository reservaRepository;
    final private ReservaHelper reservaHelper;

    @Autowired
    public FuncionamentoService(
            FuncionamentoRepository funcionamentoRepository,
            QuadraRepository quadraRepository,
            FuncionamentoFinderService funcionamentoFinderService,
            ReservaRepository reservaRepository,
            ReservaHelper reservaHelper
            ){
        this.funcionamentoRepository = funcionamentoRepository;
        this.quadraRepository = quadraRepository;
        this.funcionamentoFinderService = funcionamentoFinderService;
        this.reservaRepository = reservaRepository;
        this.reservaHelper = reservaHelper;
    }

    private void validarQuadra(Long quadraId){
        this.quadraRepository.findById(quadraId).orElseThrow(() -> new EntityNotFoundException("Não existe quadra com esse número de ID"));
    }

    private Funcionamento converterParaEntidade(Long quadraId, FuncionamentoDTO funcionamentoDTO){
        return new Funcionamento(quadraId, funcionamentoDTO.getDiaSemana(), funcionamentoDTO.getAbertura(), funcionamentoDTO.getFechamento());
    }

    private void verificarConflitosComReservas(Funcionamento funcionamento){
        DayOfWeek diaDaSemana = DiaSemanaUtils.transfromaDiaSemanaEmIngles(funcionamento.getDiaSemana());

        List<Reserva> reservas = this.reservaRepository.findAllByQuadraIdAndData(
                funcionamento.getQuadra_id(),
                this.reservaHelper.encontrarProximaData(diaDaSemana)
        );

        if(!reservas.isEmpty()){
            reservas.forEach(d -> {
                if(d.getHorarioInicio().isAfter(funcionamento.getFechamento()) || d.getHorarioInicio().isBefore(funcionamento.getAbertura())){
                    throw new IllegalArgumentException("Essa atualização atinge reservas que já foram realizadas.");
                }
            });
        }
    }

    public FuncionamentoQuadraDTO create(Long quadraId, List<FuncionamentoDTO> funcionamentoDTOS){
        this.validarQuadra(quadraId);

        List<DiaSemana> diasCadastrados = this.funcionamentoFinderService.buscarPelosDiasCadastrados(quadraId);

        List<Funcionamento> listaFuncionamentoQuadraNaSemana = funcionamentoDTOS
                .stream()
                .filter(dto -> !diasCadastrados.contains(dto.getDiaSemana()))
                .map(dto -> this.converterParaEntidade(quadraId, dto))
                .toList();

        if(listaFuncionamentoQuadraNaSemana.isEmpty()){
            throw new IllegalStateException("Nenhum valor foi adicionado. Dias da semana que já existem são ignorados.");
        }

        List<Funcionamento> listaFuncionamentoQuadraNaSemanaSalvo = this.funcionamentoRepository.saveAll(listaFuncionamentoQuadraNaSemana);

        List<FuncionamentoDTO> listaFuncionamentoQuadraNaSemanaDTO = listaFuncionamentoQuadraNaSemanaSalvo
                .stream()
                .map(FuncionamentoDTO::fromEntity)
                .toList();

        return new FuncionamentoQuadraDTO(quadraId, listaFuncionamentoQuadraNaSemanaDTO);
    }

    public FuncionamentoQuadraDTO get(Long quadraId){
        this.validarQuadra(quadraId);

        List<FuncionamentoDTO> listaFuncionamentoQuadraNaSemanaDTO = this.funcionamentoRepository.findAllByQuadraIdOrderByDiaSemana(quadraId)
                .stream()
                .map(FuncionamentoDTO::fromEntity)
                .toList();

        if(listaFuncionamentoQuadraNaSemanaDTO.isEmpty()){
            throw new EntityNotFoundException("Não encontramos horário de funcionamento para a quadra indicada.");
        }

        return new FuncionamentoQuadraDTO(quadraId, listaFuncionamentoQuadraNaSemanaDTO);
    }

    public FuncionamentoDTO update(FuncionamentoDTO funcionamentoDTO){
        Funcionamento funcionamento = this.funcionamentoFinderService.buscarPeloFuncionamento(funcionamentoDTO.getDiaSemana(), funcionamentoDTO.getQuadraId());

        this.verificarConflitosComReservas(funcionamento);

        funcionamento.setAbertura(funcionamentoDTO.getAbertura());
        funcionamento.setFechamento(funcionamentoDTO.getFechamento());

        Funcionamento funcionamentoAtualizado = this.funcionamentoRepository.save(funcionamento);

        return FuncionamentoDTO.fromEntity(funcionamentoAtualizado);
    }

    public FuncionamentoDTO updateDisponibilidade(Long quadraId, String diaSemana){
        DiaSemana diaSemanaComoEnum = DiaSemana.valueOf(diaSemana);
        Funcionamento funcionamento = this.funcionamentoFinderService.buscarPeloFuncionamento(diaSemanaComoEnum, quadraId);

        this.verificarConflitosComReservas(funcionamento);

        funcionamento.setDisponibilidade(!funcionamento.getDisponibilidade());

        Funcionamento funcionamentoAtualizado = this.funcionamentoRepository.save(funcionamento);
        return FuncionamentoDTO.fromEntity(funcionamentoAtualizado);
    }

    public void delete(Long quadraId, String diaSemana){
        DiaSemana diaSemanaComoEnum = DiaSemana.valueOf(diaSemana);
        Funcionamento funcionamento = this.funcionamentoFinderService.buscarPeloFuncionamento(diaSemanaComoEnum, quadraId);

        this.verificarConflitosComReservas(funcionamento);
        this.funcionamentoRepository.delete(funcionamento);
    }
}
