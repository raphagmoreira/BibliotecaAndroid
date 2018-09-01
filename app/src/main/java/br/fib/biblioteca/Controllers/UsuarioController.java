package br.fib.biblioteca.Controllers;

import android.content.Context;

import java.util.List;

import br.fib.biblioteca.Models.Usuario;
import br.fib.biblioteca.Repository.UsuarioRepository;

public class UsuarioController {
    private UsuarioRepository usuarioRepository;

    public UsuarioController(Context context) {
        usuarioRepository = new UsuarioRepository(context);
    }

    public Boolean validaUsuario(String username, String password) {
        Usuario usuario = usuarioRepository.findByUserPass(username, password);

        if (usuario != null) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public List<Usuario> findUsuarios(Usuario usuario) {
        return usuarioRepository.findUsuarios(usuario);
    }

    public void gravarUsuario(Usuario usuario) {
        usuarioRepository.gravarUsuario(usuario);
    }

    public Usuario findById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public void apagarUsuario(Long idUsuario) { usuarioRepository.apagarUsuario(idUsuario); }
}
