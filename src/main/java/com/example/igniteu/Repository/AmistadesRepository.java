package com.example.igniteu.Repository;

import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Usertable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AmistadesRepository extends JpaRepository<Amistades, Long> {
    
    
    List<Amistades> findByAmistad(Usertable amistad);
    List<Amistades> findByAmistadAndEstado(Usertable amistad, Amistades.EstadoAmistad estado);


    Optional<Amistades> findByUsuarioAndAmistadAndEstado(Usertable usuario, Usertable amistad, Amistades.EstadoAmistad estado);
    Optional<Amistades> findByAmistadAndUsuarioAndEstado(Usertable amistad, Usertable usuario, Amistades.EstadoAmistad estado);


}

