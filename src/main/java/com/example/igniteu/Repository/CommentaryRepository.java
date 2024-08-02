package com.example.igniteu.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.igniteu.models.Comentario;
import com.example.igniteu.models.Post;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Comentario, Integer> {

    List<Comentario> findByPostId(Integer postId);
}
