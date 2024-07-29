package com.example.igniteu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.igniteu.Services.AmistadesService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Usertable;

@Controller
public class SearchController {

    @Autowired

    private AmistadesService amistadesService;
    
    @Autowired
    private UserService userService;


    @GetMapping("/search")
    public String searchUsers(@RequestParam("query") String query, Model model) {
        List<Usertable> searchResults = userService.searchUsers(query);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("query", query);
        
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);
        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username); 
        List<Amistades> requests = amistadesService.getFriendRequests(currentUserOpt.get());
        model.addAttribute("requests", requests);
        model.addAttribute("pfp", usertable.getPfp());

        Usertable loggedInUser = currentUserOpt.get();
        
        for (Usertable user: searchResults) {
            boolean isFriend = amistadesService.existeSolicitudDeAmistad(loggedInUser, user, Amistades.EstadoAmistad.ACEPTADA)||
            amistadesService.existeSolicitudDeAmistad(user, loggedInUser, Amistades.EstadoAmistad.ACEPTADA);
            boolean isPending = amistadesService.existeSolicitudDeAmistad(loggedInUser, user, Amistades.EstadoAmistad.PENDIENTE)||
            amistadesService.existeSolicitudDeAmistad(user, loggedInUser, Amistades.EstadoAmistad.PENDIENTE);
            user.setTieneSolicitud(isFriend || isPending);
        }
        
        List<Usertable> amistades = amistadesService.getAmistadesAceptadas(usertable);
        model.addAttribute("amistades", amistades);
        
        return "search";
    }
}