package com.example.igniteu.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.igniteu.models.Like;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUsuarioAndPost(Usertable usuario, Post post);
    Integer countByPost(Post post);
    void deleteByUsuarioAndPost(Usertable usuario, Post post);
}
