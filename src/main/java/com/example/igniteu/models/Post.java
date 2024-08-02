package com.example.igniteu.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usertable usuario;

    // Campo adicional para la fecha formateada
    private String formattedFechaPublicacion;

    @NotEmpty(message = "El contenido no puede estar vacío")
    @Column(name = "contenido")
    private String contenido;

    private LocalDateTime fecha_publicacion;

    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY)
    private List<Comentario> comentarios;
}
