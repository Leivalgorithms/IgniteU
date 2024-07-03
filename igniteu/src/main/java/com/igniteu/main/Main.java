package com.igniteu.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.igniteu.modelo.UsuarioTO;
import com.igniteu.servicios.ServicioUsuario;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private ServicioUsuario servicioUsuario;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear un usuario de prueba
        UsuarioTO usuario = new UsuarioTO();
        usuario.setNombre("Prueba");
        usuario.setCorreo("prueba@example.com");
        usuario.setContrasena(1234);

        // Guardar el usuario en la base de datos
        servicioUsuario.insertar(usuario);

        // Recuperar y mostrar el usuario guardado
        UsuarioTO retrievedUser = servicioUsuario.validarUsuario(usuario.getCorreo(), String.valueOf(usuario.getContrasena()));
        if (retrievedUser != null) {
            System.out.println("Usuario guardado: " + retrievedUser.getNombre() + ", " + retrievedUser.getCorreo());
        } else {
            System.out.println("Error al guardar el usuario.");
        }
    }
}
