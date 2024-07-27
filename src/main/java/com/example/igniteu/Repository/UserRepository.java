package com.example.igniteu.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.igniteu.models.Usertable;

public interface UserRepository extends JpaRepository<Usertable, Integer> {

    public Usertable findByCorreo(String correo);

    Optional<Usertable> findByUsername(String username);

    public List<Usertable> findByUsernameContainingIgnoreCase(String nombre);
    
    public List<Usertable> findById(int id);

    public Usertable findBybio(String bio);
}

