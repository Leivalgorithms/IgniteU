package com.example.igniteu.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comentarios")
public class comentarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomentario;

    private Integer post_id;
    private Integer usuario_id;
    private String contenido;
    private LocalDateTime fecha_comentario;

}
