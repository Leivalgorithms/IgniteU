package com.example.igniteu.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "postcompartido")
public class PostCompartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpostcompartido;

    @ManyToOne
    @JoinColumn(name = "original_post_id", nullable = false)
    private Post originalPost;

    @ManyToOne
    @JoinColumn(name = "shared_by_user_id", nullable = false)
    private Usertable sharedBy;

    private LocalDateTime fechahoracompartido;

    private String contenidopostcompartido;

}
