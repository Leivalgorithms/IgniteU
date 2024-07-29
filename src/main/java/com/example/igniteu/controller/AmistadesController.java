package com.example.igniteu.controller;


import com.example.igniteu.models.Usertable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.igniteu.Services.*;
import org.springframework.security.core.userdetails.User;


import java.util.Optional;

@Controller
public class AmistadesController {

    @Autowired
    private AmistadesService amistadesService;

    @Autowired
    UserService userService;

    @GetMapping("/send-request")
    public String showSendRequestForm() {
        return "send-request";
    }

    @PostMapping("/send-request")
    public ResponseEntity<Void> sendFriendRequest(@RequestParam String friendUsername) {

        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username);
        Optional<Usertable> friendOpt = amistadesService.findUserByUsername(friendUsername);

        if (currentUserOpt.isPresent() && friendOpt.isPresent()) {
            amistadesService.sendFriendRequest(currentUserOpt.get(), friendOpt.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/accept-request")
    public String acceptFriendRequest(@RequestParam Long requestId) {
        System.out.println("Accept request with ID: " + requestId);
        amistadesService.acceptFriendRequest(requestId);
        return "index";
    }

    @PostMapping("/deny-request")
    public String DeniedFriendRequest(@RequestParam Long requestId) {
        System.out.println("Deny request with ID: " + requestId);
        amistadesService.DeniedFriendRequest(requestId);
        return "index";
    }




    @PostMapping("/remove-friend")
    @ResponseBody
    public ResponseEntity<Void> removeFriend(@RequestParam String friendUsername) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username);
        Optional<Usertable> friendOpt = amistadesService.findUserByUsername(friendUsername);

        if (currentUserOpt.isPresent() && friendOpt.isPresent()) {
            amistadesService.eliminarAmistad(currentUserOpt.get(), friendOpt.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



}
