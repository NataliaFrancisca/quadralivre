package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.FuncionamentoCompletoDTO;
import br.com.nat.quadralivre.dto.FuncionamentoDTO;
import br.com.nat.quadralivre.dto.FuncionamentoQuadraDTO;
import br.com.nat.quadralivre.enums.DiaSemana;
import br.com.nat.quadralivre.helper.ReservaHelper;
import br.com.nat.quadralivre.mapper.FuncionamentoMapper;
import br.com.nat.quadralivre.model.Funcionamento;
import br.com.nat.quadralivre.model.Quadra;
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
import java.util.stream.Collectors;

@Service
public class FuncionamentoService {
    final private FuncionamentoRepository funcionamentoRepository;
    final private QuadraRepository quadraRepository;
    final private FuncionamentoFinderService funcionamentoFinderService;
    final private ReservaRepository reservaRepository;
    final private ReservaHelper reservaHelper;
    final private FuncionamentoMapper funcionamentoMapper;

    @Autowired
    public FuncionamentoService(
            FuncionamentoRepository funcionamentoRepository,
            QuadraRepository quadraRepository,
            FuncionamentoFinderService funcionamentoFinderService,
            ReservaRepository reservaRepository,
            ReservaHelper reservaHelper,
            FuncionamentoMapper funcionamentoMapper
            ){
        this.funcionamentoRepository = funcionamentoRepository;
        this.quadraRepository = quadraRepository;
        this.funcionamentoFinderService = funcionamentoFinderService;
        this.reservaRepository = reservaRepository;
        this.reservaHelper = reservaHelper;
        this.funcionamentoMapper = funcionamentoMapper;
    }

    private Quadra validarQuadra(Long quadraId){
        return this.quadraRepository.findById(quadraId).orElseThrow(() -> new EntityNotFoundException("Não existe quadra com esse número de ID."));
    }

    private void verificarConflitosComReservas(Funcionamento funcionamento){
        DayOfWeek diaDaSemana = DiaSemanaUtils.transfromaDiaSemanaEmIngles(funcionamento.getDiaSemana());

        List<Reserva> reservas = this.reservaRepository.findAllByQuadraIdAndData(
                funcionamento.getQuadra().getId(),
                this.reservaHelper.encontrarProximaData(diaDaSemana)
        );

        if(!reservas.isEmpty()){
            reservas.forEach(d -> {
                if(d.getHorarioInicio().isAfter(funcionamento.getFechamento()) || d.getHorarioInicio().isBefore(funcionamento.getAbertura())){
                    throw new IllegalArgumentException("Essa atualização atinge reservas que já foram realizadas. Por isso não podemos realizar essa atualização.");
                }
            });
        }
    }

    private List<Funcionamento> obterDiasNaoCadastrados(FuncionamentoQuadraDTO funcionamentoQuadraDTO){
        List<DiaSemana> diasDaSemanaJaCadastrados = this.funcionamentoFinderService.buscarPelosDiasCadastrados(funcionamentoQuadraDTO.getQuadraId());

        List<Funcionamento> diasDaSemanaNaoCadastrados = funcionamentoQuadraDTO.getFuncionamento()
                .stream()
                .filter(dto -> !diasDaSemanaJaCadastrados.contains(dto.getDiaSemana()))
                .map(this.funcionamentoMapper::toFuncionamentoEntidade)
                .toList();

        if(diasDaSemanaNaoCadastrados.isEmpty()){
            throw new IllegalStateException("Nenhum valor foi adicionado. Dias da semana que já existem são ignorados.");
        }

        return diasDaSemanaNaoCadastrados;
    }

    public FuncionamentoQuadraDTO create(FuncionamentoQuadraDTO funcionamentoQuadraDTO){
        Quadra quadra = this.validarQuadra(funcionamentoQuadraDTO.getQuadraId());

        List<Funcionamento> diasDaSemanaCadastrados = this.obterDiasNaoCadastrados(funcionamentoQuadraDTO)
                .stream()
                .peek(dto -> this.funcionamentoMapper.toEntidadeComQuadraId(dto, quadra))
                .collect(Collectors.toList());

        this.funcionamentoRepository.saveAll(diasDaSemanaCadastrados);

        List<FuncionamentoDTO> funcionamentoQuadra = diasDaSemanaCadastrados
                .stream()
                .map(this.funcionamentoMapper::toDTOCompleto)
                .toList();

        return new FuncionamentoQuadraDTO(funcionamentoQuadraDTO.getQuadraId(), funcionamentoQuadra);
    }

    public FuncionamentoQuadraDTO get(Long quadraId){
        this.validarQuadra(quadraId);

        List<FuncionamentoDTO> listaFuncionamentoQuadraNaSemanaDTO = this.funcionamentoRepository.findAllByQuadraIdOrderByDiaSemana(quadraId)
                .stream()
                .map(this.funcionamentoMapper::toDTOCompleto)
                .toList();

        if(listaFuncionamentoQuadraNaSemanaDTO.isEmpty()){
            throw new EntityNotFoundException("Não encontramos horário de funcionamento para a quadra indicada.");
        }

        return new FuncionamentoQuadraDTO(quadraId, listaFuncionamentoQuadraNaSemanaDTO);
    }

    public FuncionamentoDTO update(FuncionamentoCompletoDTO funcionamentoDTO){
        Funcionamento funcionamento = this.funcionamentoFinderService.buscarPeloFuncionamento(funcionamentoDTO.getDiaSemana(), funcionamentoDTO.getQuadraId());

        this.verificarConflitosComReservas(funcionamento);

        funcionamento.setAbertura(funcionamentoDTO.getAbertura());
        funcionamento.setFechamento(funcionamentoDTO.getFechamento());

        Funcionamento funcionamentoAtualizado = this.funcionamentoRepository.save(funcionamento);

        return this.funcionamentoMapper.toDTOCompleto(funcionamentoAtualizado);
    }

    public FuncionamentoDTO updateDisponibilidade(Long quadraId, DiaSemana diaSemana){
        Funcionamento funcionamento = this.funcionamentoFinderService.buscarPeloFuncionamento(diaSemana, quadraId);

        this.verificarConflitosComReservas(funcionamento);
        funcionamento.setDisponibilidade(!funcionamento.getDisponibilidade());

        return this.funcionamentoMapper.toDTOCompleto(this.funcionamentoRepository.save(funcionamento));
    }

    public void delete(Long quadraId, DiaSemana diaSemana){
        Funcionamento funcionamento = this.funcionamentoFinderService.buscarPeloFuncionamento(diaSemana, quadraId);
        this.verificarConflitosComReservas(funcionamento);
        this.funcionamentoRepository.delete(funcionamento);
    }
}
