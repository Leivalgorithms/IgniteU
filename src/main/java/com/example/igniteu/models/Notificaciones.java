package com.example.igniteu.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Notificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usertable usuarioOrigen;

    @ManyToOne
    private Usertable usuarioDestino;

    private String tipo;

    private String contenido;

    private LocalDateTime fecha;

    private boolean leida;

}
