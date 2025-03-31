package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.EnderecoDTO;
import br.com.nat.quadralivre.dto.QuadraAtualizacaoDTO;
import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.mapper.QuadraMapper;
import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.model.Reserva;
import br.com.nat.quadralivre.repository.GestorRepository;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.repository.ReservaRepository;
import br.com.nat.quadralivre.service.validacao.ValidacaoQuadra;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuadraService {
    final private QuadraRepository quadraRepository;
    final private GestorRepository gestorRepository;
    final private ValidacaoQuadra validacaoQuadra;
    final private QuadraMapper quadraMapper;
    final private ReservaRepository reservaRepository;

    @Autowired
    public QuadraService(QuadraRepository quadraRepository,
                         GestorRepository gestorRepository,
                         ReservaRepository reservaRepository,
                         ValidacaoQuadra validacaoQuadra,
                         QuadraMapper quadraMapper

    ){
        this.quadraRepository = quadraRepository;
        this.gestorRepository = gestorRepository;
        this.reservaRepository = reservaRepository;
        this.validacaoQuadra = validacaoQuadra;
        this.quadraMapper = quadraMapper;
    }

    private Quadra buscarPelaQuadra(Long id){
        return this.quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos quadra com esse número de ID."));
    }

    private Gestor buscarPeloGestor(Long id){
        return this.gestorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos gestor com ess número de ID."));
    }

    private void validarSeQuadraPodeSerRemovido(Long quadraId){
        LocalDateTime dataAtual = LocalDateTime.now();

        List<Reserva> reservas = this.reservaRepository
                .findAllByQuadraIdAndDataBetween(
                        quadraId,
                        dataAtual.toLocalDate().atStartOfDay(),
                        dataAtual.toLocalDate().atTime(23, 59,59)
                );

        if(!reservas.isEmpty()){
            throw new IllegalStateException("Esta quadra têm reservas ativas e não pode ser removido no momento.");
        }
    }

    public QuadraDTO create(QuadraDTO quadraDTO){
        Gestor gestor = this.buscarPeloGestor(quadraDTO.getGestor_id());

        this.validacaoQuadra.validar(quadraDTO);

        Quadra quadraParaSalvar = this.quadraMapper.toQuadraEntidade(quadraDTO);
        quadraParaSalvar.setGestor(gestor);
        Quadra quadraSalva = this.quadraRepository.save(quadraParaSalvar);

        return this.quadraMapper.toDTOCompleto(quadraSalva);
    }

    public List<QuadraDTO> get(){
        List<Quadra> quadras = this.quadraRepository.findAll();
        return quadras.stream().map(this.quadraMapper::toDTOCompleto).toList();
    }

    public QuadraDTO getById(Long id){
        return this.quadraMapper.toDTOCompleto(this.buscarPelaQuadra(id));
    }

    public List<QuadraDTO> getAllByEmail(String email){
        this.validacaoQuadra.validarEmail(email);

        List<Quadra> quadras = this.quadraRepository.findAllByGestorEmail(email);

        return quadras.stream().map(this.quadraMapper::toDTOCompleto).toList();
    }

    public QuadraDTO update(QuadraAtualizacaoDTO quadraDTO){
        Quadra quadraParaAtualizar = this.buscarPelaQuadra(quadraDTO.getId());

        this.validacaoQuadra.validarAtualizacao(quadraParaAtualizar, quadraDTO);

        Gestor gestor = this.buscarPeloGestor(quadraDTO.getGestor_id());

        quadraParaAtualizar.setTitulo(quadraDTO.getTitulo());
        quadraParaAtualizar.setGestor_id(quadraDTO.getGestor_id());
        quadraParaAtualizar.setGestor(gestor);
        quadraParaAtualizar.setEndereco(EnderecoDTO.toEndereco(quadraDTO.getEndereco()));

        this.quadraRepository.save(quadraParaAtualizar);

        return this.quadraMapper.toDTOCompleto(quadraParaAtualizar);
    }

    public void delete(Long id){
        this.validarSeQuadraPodeSerRemovido(id);
        this.buscarPelaQuadra(id);
        this.quadraRepository.deleteById(id);
    }
}
