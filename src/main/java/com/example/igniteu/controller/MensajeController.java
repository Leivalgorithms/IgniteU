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
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            List<Mensaje> mensajes = mensajeService.obtenerMensajesEntreUsuarios(currentUserOpt.get(),contactoOpt.get());
            Usertable currentUser = currentUserOpt.get();
            model.addAttribute("mensajes", mensajes);
            model.addAttribute("contacto", contactoOpt.get());
            model.addAttribute("usuarioActual", currentUserOpt.get());
            model.addAttribute("currentUsername", currentUser.getUsername());
        }

        return "/bandeja";
    }




    @MessageMapping("/chat.send")
public void enviarMensaje(@Payload Mensaje mensaje, Principal principal) {
    System.out.println("Mensaje recibido en el servidor: " + mensaje);
    

    String username = principal.getName();
    
    Optional<Usertable> remitenteOpt = amistadesService.findUserBycorreo(username);
    
    Usertable remitente = remitenteOpt.get();

    
    Optional<Usertable> destinatarioOpt = usertableRepository.findById(mensaje.getDestinatario().getId());
    
    Usertable destinatario = destinatarioOpt.get();

    mensaje.setRemitente(remitente);
    mensaje.setDestinatario(destinatario);
    mensaje.setFechaEnvio(LocalDateTime.now());

    mensajeService.enviarMensaje(remitente.getId(), destinatario.getId(), mensaje.getContenido(), mensaje.getFechaEnvio());

    String conversationId = getConversationId(remitente.getId(), destinatario.getId());




    // Enviar el mensaje al destinatario
   
        System.out.println("Enviando mensaje a: " + destinatario.getUsername());
        simpMessagingTemplate.convertAndSendToUser(
            destinatario.getUsername(),
            "/queue/messages/" + conversationId,
            mensaje
        );



    System.out.println("Mensaje enviado a: " + destinatario.getUsername());
    // Enviar una copia del mensaje al remitente
    simpMessagingTemplate.convertAndSendToUser(
        remitente.getUsername(),
        "/queue/messages/" + conversationId,
        mensaje
    );
}
    


private String getConversationId(int userId1, int userId2) {
    
    return userId1 < userId2 ? userId1 + "-" + userId2 : userId2 + "-" + userId1;
}
}