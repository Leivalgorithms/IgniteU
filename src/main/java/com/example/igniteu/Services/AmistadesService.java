package com.example.igniteu.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.AmistadesRepository;
import com.example.igniteu.Repository.UserRepository;
import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Amistades.EstadoAmistad;
import com.example.igniteu.models.Usertable;

@Service
public class AmistadesService {

    @Autowired
    private AmistadesRepository amistadesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificacionService notificacionService;

    public Optional<Usertable> findUserBycorreo(String username) {
        return Optional.of(userRepository.findByCorreo(username));
    }

    public Optional<Usertable> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Amistades sendFriendRequest(Usertable user, Usertable friend) {
        Amistades amistad = new Amistades();
        amistad.setUsuario(user);
        amistad.setAmistad(friend);
        amistad.setFechaAmistad(LocalDate.now());
        amistad.setEstado(Amistades.EstadoAmistad.PENDIENTE);
        Amistades savedAmistad = amistadesRepository.save(amistad);

        // Crear notificación
        notificacionService.crearNotificacion(
                user,
                friend,
                "solicitud_amistad",
                "Te ha enviado una solicitud de amistad");

        return savedAmistad;
    }

    public List<Amistades> getFriendRequests(Usertable user) {
        return amistadesRepository.findByAmistadAndEstado(user, Amistades.EstadoAmistad.PENDIENTE);
    }

    public void acceptFriendRequest(Long requestId) {
        Optional<Amistades> amistadOpt = amistadesRepository.findById(requestId);

        Amistades amistad = amistadOpt.get();
        amistad.setEstado(Amistades.EstadoAmistad.ACEPTADA);
        amistadesRepository.save(amistad);

        // Crear notificación
        notificacionService.crearNotificacion(
                amistad.getAmistad(),
                amistad.getUsuario(),
                "solicitud_aceptada",
                "Ha aceptado tu solicitud de amistad");

    }

    public void DeniedFriendRequest(Long requestId) {
        Optional<Amistades> amistadOpt = amistadesRepository.findById(requestId);

        Amistades amistad = amistadOpt.get();
        amistad.setEstado(Amistades.EstadoAmistad.RECHAZADA);
        amistadesRepository.delete(amistad);
    }

    public boolean existeSolicitudDeAmistad(Usertable currentUserOpt, Usertable amistadId,
            Amistades.EstadoAmistad estado) {

        return amistadesRepository.findByUsuarioAndAmistadAndEstado(currentUserOpt, amistadId, estado).isPresent() ||
                amistadesRepository.findByAmistadAndUsuarioAndEstado(amistadId, currentUserOpt, estado).isPresent();
    }

    public List<Usertable> getAmistadesAceptadas(Usertable usuario) {
        List<Amistades> amistades = amistadesRepository.findByUsuarioAndEstado(usuario,
                Amistades.EstadoAmistad.ACEPTADA);
        List<Amistades> amistadesInversas = amistadesRepository.findByAmistadAndEstado(usuario,
                Amistades.EstadoAmistad.ACEPTADA);

        List<Usertable> amigos = new ArrayList<>();

        for (Amistades amistad : amistades) {
            amigos.add(amistad.getAmistad());
        }

        for (Amistades amistad : amistadesInversas) {
            amigos.add(amistad.getUsuario());
        }

        return amigos;
    }

    public List<Integer> getFriendIdsByUserId(Usertable userId) {
        List<Amistades> amistades = amistadesRepository.findByUsuario(userId);
        return amistades.stream()
                .map(Amistades::getAmistad)
                .map(Usertable::getId)
                .collect(Collectors.toList());
    }

    public void eliminarAmistad(Usertable currentUser, Usertable amistadUser) {
        Optional<Amistades> amistadOpt = amistadesRepository.findByUsuarioAndAmistad(currentUser, amistadUser);
        if (!amistadOpt.isPresent()) {
            amistadOpt = amistadesRepository.findByAmistadAndUsuario(currentUser, amistadUser);
        }
        amistadOpt.ifPresent(amistad -> amistadesRepository.delete(amistad));
    }

    public boolean sonAmigos(Usertable usuario, Usertable amigo) {
        Optional<Amistades> amistadOpt = amistadesRepository.findByUsuarioAndAmistad(usuario, amigo);
        if (!amistadOpt.isPresent()) {
            amistadOpt = amistadesRepository.findByAmistadAndUsuario(usuario, amigo);
        }
        return amistadOpt.isPresent();
    }

    public List<Amistades> getPendingRequests() {
        return amistadesRepository.findAllByEstado(Amistades.EstadoAmistad.PENDIENTE);
    }

    public boolean sonAmigosAceptados(Usertable user1, Usertable user2) {
        Optional<Amistades> amistadOptional = amistadesRepository.findByUsuarioAndAmistad(user1, user2);
        if (!amistadOptional.isPresent()) {
            amistadOptional = amistadesRepository.findByUsuarioAndAmistad(user2, user1);
        }

        return amistadOptional.map(amistad -> EstadoAmistad.ACEPTADA.equals(amistad.getEstado()))
                .orElse(false);
    }

}
