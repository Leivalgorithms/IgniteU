package com.example.igniteu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.igniteu.Repository.UserRepository;
import com.example.igniteu.Services.MensajeService;

import com.example.igniteu.models.Mensaje;
import com.example.igniteu.models.Usertable;

import java.security.Principal;
import java.util.List;

import java.util.Optional;
import com.example.igniteu.Services.AmistadesService;

@Controller
@RequestMapping
public class MensajeController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private UserRepository usertableRepository;

    @Autowired
    private AmistadesService amistadesService;

    @GetMapping("/bandeja/{contactoId}")
    public String bandejaEntrada(@PathVariable("contactoId") int contactoId, Principal principal, Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username);
        Optional<Usertable> contactoOpt = usertableRepository.findById(contactoId);

        if (currentUserOpt.isPresent() && contactoOpt.isPresent()) {
            List<Mensaje> mensajes = mensajeService.obtenerMensajesEntreUsuarios(currentUserOpt.get(),
                    contactoOpt.get());
            model.addAttribute("mensajes", mensajes);
            model.addAttribute("contacto", contactoOpt.get());
            model.addAttribute("usuarioActual", currentUserOpt.get());
        }

        return "/bandeja";
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public Mensaje enviarMensaje(@Payload Mensaje mensaje, Principal principal) {
        if (principal == null) {
            throw new IllegalStateException("El usuario no está autenticado");
        }

        String username = principal.getName();
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("El nombre de usuario no puede ser null o vacío");
        }

        Optional<Usertable> remitenteOpt = amistadesService.findUserBycorreo(username);
        if (!remitenteOpt.isPresent()) {
            throw new IllegalStateException("El remitente no se encuentra en la base de datos");
        }

        Usertable remitente = remitenteOpt.get();
        if (mensaje.getDestinatario() == null || mensaje.getDestinatario().getId() <= 0) {
            throw new IllegalStateException("El destinatario o su correo no pueden ser null");
        }

        mensajeService.enviarMensaje(remitente.getId(), mensaje.getDestinatario().getId(), mensaje.getContenido(),
                mensaje.getFechaEnvio());

        return mensaje;
    }

}