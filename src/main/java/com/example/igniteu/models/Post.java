package com.example.igniteu.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpost;

    private Integer usuario_id;

    @NotEmpty(message = "El contenido no puede estar vacío")
    @Column(name = "contenido")
    private String contenido;

    private LocalDateTime fecha_publicacion;
}
