package com.example.igniteu.Repository;

import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Usertable;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AmistadesRepository extends JpaRepository<Amistades, Long> {
    
    
    List<Amistades> findByAmistad(Usertable amistad);
    List<Amistades> findByAmistadAndEstado(Usertable amistad, Amistades.EstadoAmistad estado);



    Optional<Amistades> findByUsuarioAndAmistad(Usertable usuario, Usertable amistad);
    Optional<Amistades> findByAmistadAndUsuario(Usertable amistad, Usertable usuario);



    Optional<Amistades> findByUsuarioAndAmistadAndEstado(Usertable usuario, Usertable amistad, Amistades.EstadoAmistad estado);
    Optional<Amistades> findByAmistadAndUsuarioAndEstado(Usertable amistad, Usertable usuario, Amistades.EstadoAmistad estado);


    @Transactional
    void deleteByUsuarioAndAmistad(Usertable usuario, Usertable amistad);

    @Transactional
    void deleteByAmistadAndUsuario(Usertable amistad, Usertable usuario);

}

