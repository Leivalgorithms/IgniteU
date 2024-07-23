package com.example.igniteu.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Registro {

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String correo;

    @Size(min = 6, message = "La longitud minima de contrasena debe de ser de 6 caracteres")
    private String password;

    private String confirmPassword;
}
