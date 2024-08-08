package com.example.igniteu.Services;

import com.example.igniteu.models.Comentario;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.PostCompartido;
import com.example.igniteu.models.Usertable;
import com.example.igniteu.Repository.PostCompartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostCompartidoService {

    @Autowired
    private PostCompartidoRepository postCompartidoRepository;

    public PostCompartido savePostCompartido(PostCompartido postCompartido) {

        if (postCompartido.getFechahoracompartido() == null) {
            postCompartido.setFechahoracompartido(LocalDateTime.now());
        }
        return postCompartidoRepository.save(postCompartido);
    }

    public List<PostCompartido> getPostsCompartidosByUser(int userId) {
        return postCompartidoRepository.findBySharedByIdOrderByFechahoracompartidoDesc(userId);
    }
}
