package com.example.igniteu.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.igniteu.Repository.UserRepository;
import com.example.igniteu.Services.PostService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;

@Controller
public class HomeController {


    UserRepository repo;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @GetMapping({ "", "/" })
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String homeinitString(Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);
        model.addAttribute("username",
                usertable.getUsername());

        // Obtener el user_id del usuario autenticado
        Integer userId = usertable.getId();

        List<Post> posts = postService.getPostUserId(userId);

        // Imprimir los valores de los posts en la consola
        for (Post post : posts) {
            System.out.println("Post ID: " + post.getIdpost());
            System.out.println("Contenido: " + post.getContenido());
            System.out.println("Fecha de Publicación: " + post.getFecha_publicacion());
        }

        model.addAttribute("userposts", posts);
        return "home";
    }

    @GetMapping("/profile")
    public String profileinitString(Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);
        model.addAttribute("username",
                usertable.getUsername());
        model.addAttribute("correo",
                usertable.getCorreo());
        return "profile";
    }

}
