package br.com.nat.quadralivre.service;

import br.com.nat.quadralivre.model.Gestor;
import br.com.nat.quadralivre.repository.GestorRepository;
import br.com.nat.quadralivre.service.validacao.ValidacaoGestor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestorService {
    final private GestorRepository gestorRepository;
    final private ValidacaoGestor validacaoGestor;

    @Autowired
    public GestorService(GestorRepository gestorRepository, ValidacaoGestor validacaoGestor) {
        this.gestorRepository = gestorRepository;
        this.validacaoGestor = validacaoGestor;
    }

    private Gestor buscaPeloGestor(Long id){
        Optional<Gestor> gestor = this.gestorRepository.findById(id);

        if(gestor.isEmpty()){
            throw new EntityNotFoundException("Não existe organizador com esse número de identificação.");
        }

        return gestor.get();
    }

    public Gestor create(Gestor gestor){
        this.validacaoGestor.validar(gestor);
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
        List<Gestor> gestores = this.gestorRepository.findAll();

        if(gestores.isEmpty()){
            throw new EntityNotFoundException("Não encontramos nenhum gestor cadastrado.");
        }

        return this.gestorRepository.findAll();
    }

    public Gestor update(Long id, Gestor gestor){
        Gestor gestorParaAtualizar = this.buscaPeloGestor(id);

        this.validacaoGestor.validarAtualizacao(gestorParaAtualizar, gestor);

        gestorParaAtualizar.setNome(gestor.getNome());
        gestorParaAtualizar.setEmail(gestor.getEmail());
        gestorParaAtualizar.setTelefone(gestor.getTelefone());

        return gestorParaAtualizar;
    }

    public void delete(Long id) {
        boolean existeGestorComId = this.gestorRepository.existsById(id);

        if(!existeGestorComId){
            throw new EntityNotFoundException("Não existe gestor com esse número de ID.");
        }

        this.gestorRepository.deleteById(id);
    }
}
