package br.com.nat.quadralivre.model.Usuario;

import br.com.nat.quadralivre.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(UsuarioRegistro usuarioRegistro, Role usuarioRole){
        Usuario usuario = new Usuario(usuarioRegistro);
        usuario.setRole(usuarioRole);
        usuario.setSenha(this.passwordEncoder.encode(usuarioRegistro.senha()));
        this.usuarioRepository.save(usuario);
    }
}
