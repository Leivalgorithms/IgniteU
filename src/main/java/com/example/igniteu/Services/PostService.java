package com.example.igniteu.Services;

import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;
import com.example.igniteu.Repository.AmistadesRepository;
import com.example.igniteu.Repository.PostRepository;
import com.example.igniteu.Repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {

        if (post.getFecha_publicacion() == null) {
            post.setFecha_publicacion(LocalDateTime.now()); // Asigna la fecha actual si no está definida
        }
        return postRepository.save(post);
    }

    public List<Post> getPostUserId(Integer id) {
        List<Post> posts = postRepository.findPostsByUserIdNative(id);
        System.out.println("Posts retrieved: " + posts);
        return posts;
    }

    public List<Post> getPostsByUserIds(List<Integer> userIds) {
        return postRepository.findPostsByUserIds(userIds);
    }

    public Optional<Post> findById(Integer id) {
        return postRepository.findById(id);
    }

}
