package com.example.igniteu.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.CommentaryRepository;
import com.example.igniteu.Repository.PostRepository;
import com.example.igniteu.models.Comentario;

@Service
public class ComentarioService {

    @Autowired
    private CommentaryRepository comentarioRepository;

    @Autowired
    private PostRepository postRepository;

    public Comentario saveComentario(Comentario comentario) {

        if (comentario.getFecha_comentario() == null) {
            comentario.setFecha_comentario(LocalDateTime.now());
        }
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> getCommentsByPostId(Integer postId) {
        return comentarioRepository.findByPostId(postId);
    }

    public Integer countCommentsByPostId(Integer postId) {
        return comentarioRepository.countCommentsByPostId(postId);
    }
}