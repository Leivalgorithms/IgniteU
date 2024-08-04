package com.example.igniteu.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.igniteu.models.Comentario;
import java.util.List;

public interface CommentaryRepository extends JpaRepository<Comentario, Integer> {

    List<Comentario> findByPostId(Integer postId);
}
