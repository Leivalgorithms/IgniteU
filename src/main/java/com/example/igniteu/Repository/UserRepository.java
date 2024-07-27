package com.example.igniteu.Repository;

<<<<<<< Updated upstream
=======
import java.util.List;
import java.util.Optional;

>>>>>>> Stashed changes
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.igniteu.models.Usertable;

public interface UserRepository extends JpaRepository<Usertable, Integer> {

    public Usertable findByCorreo(String correo);
<<<<<<< Updated upstream
}
=======
    
    Optional<Usertable> findByUsername(String username);

    public List<Usertable> findByUsernameContainingIgnoreCase(String nombre);
}
>>>>>>> Stashed changes
