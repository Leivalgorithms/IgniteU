package com.example.igniteu.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usertable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @Column(unique = false, nullable = false)
    private boolean TieneSolicitud;

    @Column(unique = true, nullable = false)
    private String correo;
    private String contrasena;

}
