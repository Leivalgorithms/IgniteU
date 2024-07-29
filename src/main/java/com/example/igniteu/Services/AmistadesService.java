package com.example.igniteu.Services;

import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Usertable;
import com.example.igniteu.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AmistadesService {

    @Autowired
    private AmistadesRepository amistadesRepository;

    @Autowired
    private UserRepository userRepository;

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
        return amistadesRepository.save(amistad);
    }

    public List<Amistades> getFriendRequests(Usertable user) {
        return amistadesRepository.findByAmistadAndEstado(user, Amistades.EstadoAmistad.PENDIENTE);
    }

    public void acceptFriendRequest(Long requestId) {
        Optional<Amistades> amistadOpt = amistadesRepository.findById(requestId);
        
            Amistades amistad = amistadOpt.get();
            amistad.setEstado(Amistades.EstadoAmistad.ACEPTADA);
            amistadesRepository.save(amistad);
        
    }


    public void DeniedFriendRequest(Long requestId) {
        Optional<Amistades> amistadOpt = amistadesRepository.findById(requestId);
        
            Amistades amistad = amistadOpt.get();
            amistad.setEstado(Amistades.EstadoAmistad.RECHAZADA);
            amistadesRepository.save(amistad);
    }



    public boolean existeSolicitudDeAmistad(Usertable currentUserOpt, Usertable amistadId, Amistades.EstadoAmistad estado) {
        
        return amistadesRepository.findByUsuarioAndAmistadAndEstado(currentUserOpt, amistadId, estado).isPresent() ||
        amistadesRepository.findByAmistadAndUsuarioAndEstado(amistadId, currentUserOpt, estado).isPresent();
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

}

