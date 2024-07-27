package com.example.igniteu.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.example.igniteu.Services.AmistadesService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Usertable;

@Controller
public class HomeController {

    
    @Autowired
    private AmistadesService amistadesService;

    @Autowired
    UserService userService;


    @GetMapping({ "", "/" })
    public String home() {
        return "index";
    }

    @GetMapping("/profile")
    public String profileinitString(Principal principal, Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);
        model.addAttribute("username",
                usertable.getUsername());
        model.addAttribute("correo",
                usertable.getCorreo());
        
       

        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username);
               
        
        List<Amistades> requests = amistadesService.getFriendRequests(currentUserOpt.get());
        System.out.println(requests);
        model.addAttribute("requests", requests);  

        return "profile";
    }
    
}
