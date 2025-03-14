package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.QuadraDTO;
import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.model.Quadra;
import br.com.nat.quadralivre.repository.GestorRepository;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.service.validacao.ValidacaoQuadra;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuadraService {
    final private QuadraRepository quadraRepository;
    final private GestorRepository gestorRepository;
    final private ValidacaoQuadra validacaoQuadra;

    @Autowired
    public QuadraService(QuadraRepository quadraRepository, ValidacaoQuadra validacaoQuadra, GestorRepository gestorRepository){
        this.quadraRepository = quadraRepository;
        this.gestorRepository = gestorRepository;
        this.validacaoQuadra = validacaoQuadra;
    }

    private Quadra buscarPelaQuadraEFazValidacao(Long id){
        Optional<Quadra> quadra = this.quadraRepository.findById(id);

        if(quadra.isEmpty()){
            throw new EntityNotFoundException("Não encontramos quadra com esse número de identificação.");
        }

        return quadra.get();
    }

    public QuadraDTO create(Quadra quadra){
        if(quadra.getGestor_id() == null){
            throw new IllegalArgumentException("O gestor da quadra não pode ser nulo.");
        }

        Optional<Gestor> gestorOptional = this.gestorRepository.findById(quadra.getGestor_id());

        if(gestorOptional.isEmpty()){
            throw new EntityNotFoundException("Não encontramos gestor com esse número de ID.");
        }

        this.validacaoQuadra.validar(quadra);
        quadra.setGestor(gestorOptional.get());
        return QuadraDTO.fromEntity(this.quadraRepository.save(quadra));
    }

    public List<QuadraDTO> get(){
        return this.quadraRepository.findAll().stream().map(QuadraDTO::fromEntity).toList();
    }

    public List<QuadraDTO> getAllByEmail(String email){
        Optional<List<Quadra>> quadras = this.quadraRepository.findAllByGestorEmail(email);

        if(quadras.isEmpty()){
            throw new EntityNotFoundException("Não encontramos quadras que são gerenciada por esse gestor.");
        }

        return quadras.get().stream().map(QuadraDTO::fromEntity).toList();
    }

    public QuadraDTO getById(Long id){
        return QuadraDTO.fromEntity(this.buscarPelaQuadraEFazValidacao(id));
    }

    public QuadraDTO update(Long id, Quadra quadra){
        Quadra quadraParaAtualizar = this.buscarPelaQuadraEFazValidacao(id);

        this.validacaoQuadra.validarAtualizacao(quadraParaAtualizar, quadra);

        Optional<Gestor> gestorOptional = this.gestorRepository.findById(quadra.getGestor_id());

        if(gestorOptional.isEmpty()){
            throw new EntityNotFoundException("Não encontramos gestor com esse número de identificação.");
        }

        quadraParaAtualizar.setTitulo(quadra.getTitulo());
        quadraParaAtualizar.setGestor_id(quadra.getGestor_id());
        quadraParaAtualizar.setGestor(gestorOptional.get());
        quadraParaAtualizar.setEndereco(quadra.getEndereco());

        return QuadraDTO.fromEntity(quadraParaAtualizar);
    }

    public void delete(Long id){
        this.buscarPelaQuadraEFazValidacao(id);
        this.quadraRepository.deleteById(id);
    }
}
