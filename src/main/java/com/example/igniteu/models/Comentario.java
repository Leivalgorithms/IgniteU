package com.example.igniteu.models;

import java.time.LocalDateTime;

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
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomentario;

    @Column(name = "post_id")
    private Integer postId;
    private Integer usuario_id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)

    private Usertable usuario;
    private String contenido;
    private LocalDateTime fecha_comentario;

}
