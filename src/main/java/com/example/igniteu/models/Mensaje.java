package com.example.igniteu.models;


import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Mensajes")
public class Mensaje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private Usertable remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usertable destinatario;
    
    @Column(unique = false, nullable = false)
    private String contenido;

    @Column(unique = false, nullable = false)
    private LocalTime fechaEnvio;
}

