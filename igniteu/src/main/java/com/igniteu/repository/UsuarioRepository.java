package com.igniteu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igniteu.modelo.UsuarioTO;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioTO, Integer> {
    Optional<UsuarioTO> findByCorreoAndContrasena(String correo, int contrasena);
    void save(UsuarioTO usuarioTO);
}
