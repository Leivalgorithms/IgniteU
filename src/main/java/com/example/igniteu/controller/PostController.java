package com.example.igniteu.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.userdetails.User;
import com.example.igniteu.Services.PostService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;

@Controller
public class PostController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final PostService postService;

    // Constructor para inyección de dependencias
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, Model model) {
        // Obtener el nombre de usuario del contexto de seguridad
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Obtener el usuario usando el correo (nombre de usuario)
        Usertable usertable = userService.findByCorreo(username);

        // Verificar si se encontró el usuario
        if (usertable == null) {
            model.addAttribute("error", "User not found");
            return "errorPage"; // Redirige a una página de error o muestra un mensaje en la vista actual
        }

        // Obtener el user_id del usuario autenticado
        Integer userId = usertable.getId();

        // Establecer el user_id en el post
        post.setUsuario_id(userId);
        // Guardar el post en la base de datos
        postService.savePost(post);

        return "home";
    }
}
