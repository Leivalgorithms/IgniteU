package com.example.igniteu.controller;

<<<<<<< Updated upstream
=======
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< Updated upstream
@Controller
public class HomeController {

=======

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

>>>>>>> Stashed changes
    @GetMapping({ "", "/" })
    public String home() {
        return "index";
    }

<<<<<<< Updated upstream
    @GetMapping("/contact")
    public String contact() {
        return "contact";
=======
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

          
>>>>>>> Stashed changes
    }



    
}
