package br.com.nat.quadralivre.controller;

import br.com.nat.quadralivre.enums.Role;
import br.com.nat.quadralivre.model.Usuario.UsuarioRegistro;
import br.com.nat.quadralivre.model.Usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/gestor")
    public ResponseEntity registrarGestor(@RequestBody @Valid UsuarioRegistro usuario){
        this.usuarioService.register(usuario, Role.GESTOR);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cliente")
    public ResponseEntity registrarCliente(@RequestBody UsuarioRegistro usuario){
        this.usuarioService.register(usuario, Role.CLIENTE);
        return ResponseEntity.ok().build();
    }
}
