package com.example.igniteu.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.CommentaryRepository;
import com.example.igniteu.Repository.PostRepository;
import com.example.igniteu.models.Comentario;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;

@Service
public class ComentarioService {

    @Autowired
    private CommentaryRepository comentarioRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private UserService userService;

    public Comentario saveComentario(Comentario comentario, String username) {
        Usertable usertable = userService.findByCorreo(username);
        if (comentario.getFecha_comentario() == null) {
            comentario.setFecha_comentario(LocalDateTime.now());
        }
        Comentario savedComentario = comentarioRepository.save(comentario);

        // Obtener el post y el usuario que hizo el post
        Post post = postRepository.findById(comentario.getPostId())
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
        Usertable postOwner = post.getUsuario();

        // Crear notificación si el comentario no es del dueño del post
        if (comentario.getUsuario_id() != postOwner.getId()) {
            notificacionService.crearNotificacion(
                    usertable,
                    postOwner,
                    "comentario",
                    "comentó en tu post: " + (comentario.getContenido().length() > 30
                            ? comentario.getContenido().substring(0, 30) + "..."
                            : comentario.getContenido()));
        }

        return savedComentario;
    }

    public List<Comentario> getCommentsByPostId(Integer postId) {
        return comentarioRepository.findByPostId(postId);
    }

    public Integer countCommentsByPostId(Integer postId) {
        return comentarioRepository.countCommentsByPostId(postId);
    }
}