package com.example.igniteu.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.LikeRepository;
import com.example.igniteu.Repository.PostRepository;
import com.example.igniteu.models.Like;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public void toggleLike(Integer postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no ha sido encontrado"));
        Usertable usertable = userService.findByCorreo(username);
        Optional<Like> existingLike = likeRepository.findByUsuarioAndPost(usertable, post);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
            Like like = new Like();
            like.setUsuario(usertable);
            like.setPost(post);
            likeRepository.save(like);
        }
    }

    public Integer countLikesByPost(Post post) {
        return likeRepository.countByPost(post);
    }

    public boolean isLikedByUser(Post post, Usertable user) {
        return likeRepository.findByUsuarioAndPost(user, post).isPresent();
    }

}