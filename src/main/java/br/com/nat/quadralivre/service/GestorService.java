package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.repository.GestorRepository;
import br.com.nat.quadralivre.repository.QuadraRepository;
import br.com.nat.quadralivre.util.ValidatorGestor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestorService {
    final private GestorRepository gestorRepository;
    final private ValidatorGestor validatorGestor;
    final private QuadraRepository quadraRepository;

    @Autowired
    public GestorService(GestorRepository gestorRepository, ValidatorGestor validatorGestor, QuadraRepository quadraRepository) {
        this.gestorRepository = gestorRepository;
        this.validatorGestor = validatorGestor;
        this.quadraRepository = quadraRepository;
    }

    private Gestor buscaPeloGestorEFazValidacao(Long id){
        Optional<Gestor> gestor = this.gestorRepository.findById(id);

        if(gestor.isEmpty()){
            throw new EntityNotFoundException("Não existe organizador com esse número de identificação.");
        }

        return gestor.get();
    }

    public Gestor create(Gestor gestor){
        this.validatorGestor.validar(gestor);
        return this.gestorRepository.save(gestor);
    }

    public Gestor get(String email){
        Optional<Gestor> gestor = this.gestorRepository.findByEmail(email);

        if(gestor.isEmpty()){
            throw new EntityNotFoundException("Não existe organizador com esse endereço de e-mail");
        }

        return gestor.get();
    }

    public List<Gestor> getAll(){
        return this.gestorRepository.findAll();
    }

    public Gestor update(Long id, Gestor gestor){
        Gestor gestorParaAtualizar = this.buscaPeloGestorEFazValidacao(id);

        this.validatorGestor.validarAtualizacao(gestorParaAtualizar, gestor);

        gestorParaAtualizar.setNome(gestor.getNome());
        gestorParaAtualizar.setEmail(gestor.getEmail());
        gestorParaAtualizar.setTelefone(gestor.getTelefone());

        return gestorParaAtualizar;
    }

    public void delete(Long id){
        Gestor gestorParaDeletar = this.buscaPeloGestorEFazValidacao(id);

        boolean existeQuadrasAssociadasAoGestor = this.quadraRepository.existsByGestorEmail(gestorParaDeletar.getEmail());

        if(existeQuadrasAssociadasAoGestor){
           throw new IllegalStateException("Não é possível excluir o gestor porque ele ainda tem quadras associadas.");
        }

        this.gestorRepository.deleteById(id);
    }
}
