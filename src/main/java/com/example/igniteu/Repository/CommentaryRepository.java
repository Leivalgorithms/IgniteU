package com.example.igniteu.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.igniteu.models.comentarios;

public interface CommentaryRepository extends JpaRepository<comentarios, Integer> {

}
