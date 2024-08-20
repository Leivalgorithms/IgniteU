package com.example.igniteu.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.NotificacionesRepository;
import com.example.igniteu.models.Notificaciones;
import com.example.igniteu.models.Usertable;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionesRepository notificacionRepository;

    public void crearNotificacion(Usertable origen, Usertable destino, String tipo, String contenido) {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setUsuarioOrigen(origen);
        notificacion.setUsuarioDestino(destino);
        notificacion.setTipo(tipo);
        notificacion.setContenido(contenido);
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionRepository.save(notificacion);
    }

    public List<Notificaciones> obtenerNotificacionesNoLeidas(Usertable usuario) {
        return notificacionRepository.findByUsuarioDestinoAndLeidaOrderByFechaDesc(usuario, false);
    }

    public long contarNotificacionesNoLeidas(Usertable usuario) {
        return notificacionRepository.countByUsuarioDestinoAndLeida(usuario, false);
    }

    public boolean marcarComoLeida(Long notificationId, Usertable usuario) {
        Optional<Notificaciones> notificacionOpt = notificacionRepository.findById(notificationId);
        if (notificacionOpt.isPresent() && notificacionOpt.get().getUsuarioDestino().equals(usuario)) {
            Notificaciones notificacion = notificacionOpt.get();
            notificacion.setLeida(true);
            notificacionRepository.save(notificacion);
            return true;
        }
        return false;
    }

    public List<Notificaciones> findAllByUsuarioDestino(Usertable usuario) {
        return notificacionRepository.findByUsuarioDestinoOrderByLeidaAscFechaDesc(usuario);
    }

}
