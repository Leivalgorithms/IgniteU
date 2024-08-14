package com.example.igniteu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.igniteu.Services.LikeService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Usertable;

@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @PostMapping("/like")
    public String likePost(@RequestParam("postId") Integer postId, Model model) {
        // Obtener el nombre de usuario del contexto de seguridad
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Obtener el usuario usando el correo (nombre de usuario)
        Usertable usertable = userService.findByCorreo(username);

        if (usertable == null) {
            model.addAttribute("error", "User not found");
            return "errorPage"; // Redirige a una página de error o muestra un mensaje en la vista actual
        }

        likeService.toggleLike(postId, username);

        return "redirect:/home";
    }
}