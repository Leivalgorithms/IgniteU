package com.example.igniteu.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.igniteu.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByIdpost(Integer idpost);

    @Query(value = "SELECT * FROM posts WHERE usuario_id = :userId order by fecha_publicacion desc ", nativeQuery = true)
    List<Post> findPostsByUserIdNative(@Param("userId") Integer userId);

}
