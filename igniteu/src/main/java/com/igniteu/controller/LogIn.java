package com.igniteu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igniteu.modelo.UsuarioTO;
import com.igniteu.servicios.ServicioUsuario;

@Controller
public class LogIn {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginUsuario", new UsuarioTO());
        return "login";
    }

    @PostMapping("/login")
    public String ingresar(@ModelAttribute("loginUsuario") UsuarioTO loginUsuario, Model model,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        UsuarioTO loginUsuarioRetorno = servicioUsuario.validarUsuario(loginUsuario.getCorreo(),
                loginUsuario.getContrasena());
        if (loginUsuarioRetorno != null) {
            request.getSession().setAttribute("loginUsuario", loginUsuarioRetorno);
            // List<UsuarioTO> listaUsuarios = servicioUsuario.demeTodos();
            // model.addAttribute("listaUsuarios", listaUsuarios);
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "La clave o correo no son correctos");
            return "redirect:/login";
        }
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        UsuarioTO loginUsuario = (UsuarioTO) request.getSession().getAttribute("loginUsuario");
        if (loginUsuario != null) {
            model.addAttribute("loginUsuario", loginUsuario);
            return "home";
        } else {
            return "redirect:/login";
        }
    }
}