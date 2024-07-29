package com.example.igniteu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.igniteu.Services.ComentarioService;

@Controller
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

}
