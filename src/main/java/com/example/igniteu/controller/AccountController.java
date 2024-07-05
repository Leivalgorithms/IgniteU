package com.example.igniteu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.igniteu.Repository.UserRepository;
import com.example.igniteu.models.*;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AccountController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/register")
    public String registrom(Model model) {
        Registro registro = new Registro();
        model.addAttribute(registro);
        model.addAttribute("Satisfactoriamente", false);
        return "register";// retorna la pagina html que lleva el registro
    }

    @PostMapping("/register")
    public String registrom(Model model, @Valid @ModelAttribute Registro registro, BindingResult result) {

        if (!registro.getPassword().equals(registro.getConfirmPassword())) {
            result.addError(new FieldError("registro", "confirmPassword", "Las contrasenas no coinciden"));
        }

        Usertable usertable = repo.findByCorreo(registro.getCorreo());
        if (usertable != null) {
            result.addError(new FieldError("registro", "correo", "El correo ingresado ya existe"));
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();

            Usertable nuevousuario = new Usertable();
            nuevousuario.setUsername(registro.getUsername());
            nuevousuario.setCorreo(registro.getCorreo());
            nuevousuario.setContrasena(bCryptEncoder.encode(registro.getPassword()));

            repo.save(nuevousuario);

            model.addAttribute("registro", new Registro());
            model.addAttribute("Satisfactoriamente", true);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return "register";
    }

}
