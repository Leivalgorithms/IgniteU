package com.example.igniteu.Repository;

import com.example.igniteu.models.PostCompartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCompartidoRepository extends JpaRepository<PostCompartido, Integer> {
    List<PostCompartido> findBySharedByIdOrderByFechahoracompartidoDesc(int userId);
}
