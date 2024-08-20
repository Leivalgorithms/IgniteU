package com.example.igniteu.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.igniteu.models.Notificaciones;
import com.example.igniteu.models.Usertable;

public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {

    List<Notificaciones> findByUsuarioDestinoAndLeidaOrderByFechaDesc(Usertable usuario, boolean leida);

    List<Notificaciones> findByUsuarioDestinoOrderByFechaDesc(Usertable usuario);

    long countByUsuarioDestinoAndLeida(Usertable usuario, boolean leida);

    @Query("SELECT n FROM Notificaciones n LEFT JOIN FETCH n.usuarioOrigen WHERE n.usuarioDestino = :usuario ORDER BY n.leida ASC, n.fecha DESC")
    List<Notificaciones> findByUsuarioDestinoOrderByLeidaAscFechaDesc(@Param("usuario") Usertable usuario);
}
