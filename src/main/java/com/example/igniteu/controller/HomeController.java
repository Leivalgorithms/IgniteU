package com.example.igniteu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Usertable;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping({ "", "/" })
    public String home() {
        return "index";
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
