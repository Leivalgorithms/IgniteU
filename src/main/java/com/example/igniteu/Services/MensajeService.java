package com.example.igniteu.Services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.MensajeRepository;
import com.example.igniteu.Repository.UserRepository;
import com.example.igniteu.models.Mensaje;
import com.example.igniteu.models.Usertable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UserRepository usertableRepository;

    public Mensaje enviarMensaje(int remitenteId, int destinatarioId, String contenido, LocalDateTime localDateTime) {

        Optional<Usertable> remitenteOpt = usertableRepository.findById(remitenteId);
        Optional<Usertable> destinatarioOpt = usertableRepository.findById(destinatarioId);

        if (!remitenteOpt.isPresent()) {
            throw new NoSuchElementException("Remitente no encontrado");
        }

        if (!destinatarioOpt.isPresent()) {
            throw new NoSuchElementException("Destinatario no encontrado");
        }

        Usertable remitente = remitenteOpt.get();
        Usertable destinatario = destinatarioOpt.get();

        Mensaje mensaje = new Mensaje();
        mensaje.setRemitente(remitente);
        mensaje.setDestinatario(destinatario);
        mensaje.setContenido(contenido);
        mensaje.setFechaEnvio(LocalDateTime.now());

        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajesRecibidos(Optional<Usertable> currentUserOpt) {
        return mensajeRepository.findByDestinatarioOrderByFechaEnvioAsc(currentUserOpt);
    }

    public List<Mensaje> obtenerMensajesEnviados(Usertable remitente) {
        return mensajeRepository.findByRemitenteOrderByFechaEnvioAsc(remitente);
    }

    public List<Mensaje> obtenerMensajesEntreUsuarios(Usertable usuario1, Usertable usuario2) {
        List<Mensaje> mensajesEnviados = mensajeRepository.findByRemitenteAndDestinatarioOrderByFechaEnvioAsc(usuario1,
                usuario2);
        List<Mensaje> mensajesRecibidos = mensajeRepository.findByDestinatarioAndRemitenteOrderByFechaEnvioAsc(usuario1,
                usuario2);
        List<Mensaje> todosLosMensajes = new ArrayList<>();
        todosLosMensajes.addAll(mensajesEnviados);
        todosLosMensajes.addAll(mensajesRecibidos);
        // Ordenar todos los mensajes en la lista por fecha
        todosLosMensajes.sort(Comparator.comparing(Mensaje::getFechaEnvio));
        return todosLosMensajes;
    }
}