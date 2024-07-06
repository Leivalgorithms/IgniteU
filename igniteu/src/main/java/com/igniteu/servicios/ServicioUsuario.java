package com.igniteu.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igniteu.modelo.UsuarioTO;
import com.igniteu.repository.UsuarioRepository;

@Service
public class ServicioUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioTO validarUsuario(String correo, String contrasena) {
        // Implementa la lógica para validar las credenciales
        if ("admin@correo.com".equals(correo) && "password".equals(contrasena)) {
            UsuarioTO usuario = new UsuarioTO();
            usuario.setCorreo(correo);
            usuario.setContrasena(contrasena);
            return usuario;
        }
        return null;
    }

    public List<UsuarioTO> demeTodos() {
        return usuarioRepository.findAll();
    }

    public boolean insertar(UsuarioTO usuarioTO) {
        try {
            usuarioRepository.save(usuarioTO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
