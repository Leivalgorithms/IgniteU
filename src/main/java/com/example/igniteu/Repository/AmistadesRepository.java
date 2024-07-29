package com.example.igniteu.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Usertable;

@Repository
public interface AmistadesRepository extends JpaRepository<Amistades, Long> {

    List<Amistades> findByAmistad(Usertable amistad);

    List<Amistades> findByAmistadAndEstado(Usertable amistad, Amistades.EstadoAmistad estado);

    List<Amistades> findByUsuario(Usertable usuario);

    Optional<Amistades> findByUsuarioAndAmistadAndEstado(Usertable usuario, Usertable amistad,
            Amistades.EstadoAmistad estado);
    List<Amistades> findByUsuarioAndEstado(Usertable usuario, Amistades.EstadoAmistad estado);

    Optional<Amistades> findByAmistadAndUsuarioAndEstado(Usertable amistad, Usertable usuario,
            Amistades.EstadoAmistad estado);

}
