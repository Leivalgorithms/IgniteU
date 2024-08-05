package com.example.igniteu.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.igniteu.models.Comentario;

public interface CommentaryRepository extends JpaRepository<Comentario, Integer> {

    List<Comentario> findByPostId(Integer postId);
    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.postId = :postId")
    Integer countCommentsByPostId(@Param("postId") Integer postId);
}
