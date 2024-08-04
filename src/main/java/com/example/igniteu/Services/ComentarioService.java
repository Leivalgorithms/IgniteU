package com.example.igniteu.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.CommentaryRepository;
import com.example.igniteu.models.Comentario;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private CommentaryRepository comentarioRepository;

    public Comentario saveComentario(Comentario comentario) {
        comentario.setFecha_comentario(LocalDateTime.now());
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> getComentariosByPostId(Integer postId) {
        return comentarioRepository.findByPostId(postId);
    }
}
