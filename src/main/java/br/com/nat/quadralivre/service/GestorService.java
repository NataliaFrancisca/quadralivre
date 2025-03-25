package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.dto.GestorDTO;
import br.com.nat.quadralivre.mapper.GestorMapper;
import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.repository.GestorRepository;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.service.validacao.ValidacaoGestor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorService {
    final private GestorRepository gestorRepository;
    final private QuadraRepository quadraRepository;
    final private ValidacaoGestor validacaoGestor;
    final private GestorMapper gestorMapper;

    @Autowired
    public GestorService(GestorRepository gestorRepository, QuadraRepository quadraRepository, ValidacaoGestor validacaoGestor, GestorMapper gestorMapper) {
        this.gestorRepository = gestorRepository;
        this.quadraRepository = quadraRepository;
        this.validacaoGestor = validacaoGestor;
        this.gestorMapper = gestorMapper;
    }

    private Gestor buscarPeloEmail(String email){
        return this.gestorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Gestor com e-mail: " + email + " não encontrado."));
    }

    private void validarSeGestorPodeSerRemovido(Long gestorId){
        boolean gestorGerenciaQuadras = this.quadraRepository.existsByGestorId(gestorId);

        if(gestorGerenciaQuadras){
            throw new IllegalStateException("Este gestor é responsável por quadras e não pode ser removido no momento.");
        }
    }

    public GestorDTO create(GestorDTO gestor){
        this.validacaoGestor.validar(gestor);

        Gestor gestorSalvo = this.gestorRepository.save(this.gestorMapper.toGestorEntidade(gestor));

        return this.gestorMapper.toDTOCompleto(gestorSalvo);
    }

    public GestorDTO get(String email){
        this.validacaoGestor.validarEmail(email);

        Gestor gestor = this.buscarPeloEmail(email);

        return this.gestorMapper.toDTOCompleto(gestor);
    }

    public List<GestorDTO> getAll(){
        List<Gestor> gestores = this.gestorRepository.findAll();
        return gestores.stream().map(this.gestorMapper::toDTOCompleto).toList();
    }

    public GestorDTO update(String email, GestorDTO gestor){
        this.validacaoGestor.validarEmail(email);

        Gestor gestorParaAtualizar = this.buscarPeloEmail(email);

        this.validacaoGestor.validarAtualizacao(gestorParaAtualizar, gestor);
        gestorMapper.atualizarEntidade(gestorParaAtualizar, gestor);
        Gestor gestorAtualizado = this.gestorRepository.save(gestorParaAtualizar);

        return this.gestorMapper.toDTOCompleto(gestorAtualizado);
    }

    public void delete(String email) {
        this.validacaoGestor.validarEmail(email);

        Gestor gestorParaDeletar = this.buscarPeloEmail(email);
        this.validarSeGestorPodeSerRemovido(gestorParaDeletar.getId());

        this.gestorRepository.delete(gestorParaDeletar);
    }
}
