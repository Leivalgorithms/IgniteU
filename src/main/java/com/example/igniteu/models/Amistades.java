package com.example.igniteu.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "amistades")
public class Amistades {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usertable usuario;

    @ManyToOne
    @JoinColumn(name = "idamistad", nullable = false)
    private Usertable amistad;

    @Column(name = "fecha_amistad", nullable = false)
    private LocalDate fechaAmistad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoAmistad estado;

    public enum EstadoAmistad {
        PENDIENTE,
        ACEPTADA,
        RECHAZADA
    }
}

