package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.EnderecoDTO;
import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.mapper.QuadraMapper;
import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.repository.GestorRepository;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.service.validacao.ValidacaoQuadra;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuadraService {
    final private QuadraRepository quadraRepository;
    final private GestorRepository gestorRepository;
    final private ValidacaoQuadra validacaoQuadra;
    final private QuadraMapper quadraMapper;

    @Autowired
    public QuadraService(QuadraRepository quadraRepository,
                         ValidacaoQuadra validacaoQuadra,
                         GestorRepository gestorRepository,
                         QuadraMapper quadraMapper
    ){
        this.quadraRepository = quadraRepository;
        this.gestorRepository = gestorRepository;
        this.validacaoQuadra = validacaoQuadra;
        this.quadraMapper = quadraMapper;
    }

    private Quadra buscarPelaQuadraEFazValidacao(Long id){
        return this.quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos quadra com esse número de ID."));
    }

    private Gestor buscarPeloGestor(Long id){
        return this.gestorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos gestor com ess número de ID."));
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

        if(quadras.isEmpty()){
            throw new EntityNotFoundException("Não encontramos nenhuma quadra cadastrada.");
        }

        return quadras.stream().map(this.quadraMapper::toDTOCompleto).toList();
    }

    public QuadraDTO getById(Long id){
        return this.quadraMapper.toDTOCompleto(this.buscarPelaQuadraEFazValidacao(id));
    }


    public List<QuadraDTO> getAllByEmail(String email){
        List<Quadra> quadras = this.quadraRepository.findAllByGestorEmail(email);

        if(quadras.isEmpty()){
            throw new EntityNotFoundException("Não encontramos quadras que são gerenciada por esse gestor.");
        }

        return quadras.stream().map(this.quadraMapper::toDTOCompleto).toList();
    }

    public QuadraDTO update(Long id, QuadraDTO quadraDTO){
        Quadra quadraParaAtualizar = this.buscarPelaQuadraEFazValidacao(id);

        this.validacaoQuadra.validarAtualizacao(quadraParaAtualizar, quadraDTO);

        Gestor gestor = this.gestorRepository.findById(quadraDTO.getGestor_id())
                .orElseThrow(() -> new EntityNotFoundException("Não encontramos gestor com esse número de identificação."));

        quadraParaAtualizar.setTitulo(quadraDTO.getTitulo());
        quadraParaAtualizar.setGestor_id(quadraDTO.getGestor_id());
        quadraParaAtualizar.setGestor(gestor);
        quadraParaAtualizar.setEndereco(EnderecoDTO.toEndereco(quadraDTO.getEndereco()));

        this.quadraRepository.save(quadraParaAtualizar);

        return this.quadraMapper.toDTOCompleto(quadraParaAtualizar);
    }

    public void delete(Long id){
        this.buscarPelaQuadraEFazValidacao(id);
        this.quadraRepository.deleteById(id);
    }
}
