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
        return usuarioRepository.findByCorreoAndContrasena(correo, Integer.parseInt(contrasena)).orElse(null);
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
