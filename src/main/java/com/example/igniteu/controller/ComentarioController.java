package com.example.igniteu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.igniteu.Services.ComentarioService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Comentario;
import com.example.igniteu.models.Usertable;

@Controller
public class ComentarioController {

    @Autowired
    private UserService userService;

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/createComment")
    public String createPost(@ModelAttribute Comentario comentario, Model model) {

        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);

        if (usertable == null) {
            model.addAttribute("error", "User not found");
            return "errorPage";
        }

        Integer userId = usertable.getId();
        comentario.setUsuario_id(userId);

        comentarioService.saveComentario(comentario);

        return "redirect:/home";
    }
}
