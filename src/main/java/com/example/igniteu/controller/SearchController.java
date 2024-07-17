package com.example.igniteu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Usertable;

@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public String searchUsers(@RequestParam("query") String query, Model model) {
        List<Usertable> searchResults = userService.searchUsers(query);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("query", query);
        return "search";
    }
}