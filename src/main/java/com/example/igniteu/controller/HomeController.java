package com.example.igniteu.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import com.example.igniteu.Repository.UserRepository;
//services
import com.example.igniteu.Services.PostService;
import com.example.igniteu.Services.AmistadesService;
import com.example.igniteu.Services.UserService;
//models
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;
import com.example.igniteu.models.Amistades;

@Controller
public class HomeController {
    @Autowired
    private AmistadesService amistadesService;

    UserRepository repo;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;

    @GetMapping({ "", "/" })
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String homeinitString(Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);
        model.addAttribute("username",
                usertable.getUsername());

        // Obtener el user_id del usuario autenticado
        Integer userId = usertable.getId();

        List<Post> posts = postService.getPostUserId(userId);

        // Imprimir los valores de los posts en la consola
        for (Post post : posts) {
            System.out.println("Post ID: " + post.getIdpost());
            System.out.println("Contenido: " + post.getContenido());
            System.out.println("Fecha de Publicación: " + post.getFecha_publicacion());
        }

        model.addAttribute("userposts", posts);
        return "home";
    }

    @GetMapping("/profile")
    public String profileinitString(Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);
        model.addAttribute("username",
                usertable.getUsername());
        model.addAttribute("correo",
                usertable.getCorreo());

        model.addAttribute("bio",
                usertable.getBio());
        model.addAttribute("pfp",
                usertable.getPfp());


        // Obtener el user_id del usuario autenticado
        Integer userId = usertable.getId();

        List<Post> posts = postService.getPostUserId(userId);

        // Imprimir los valores de los posts en la consola
        for (Post post : posts) {
            System.out.println("Post ID: " + post.getIdpost());
            System.out.println("Contenido: " + post.getContenido());
            System.out.println("Fecha de Publicación: " + post.getFecha_publicacion());
        }

        model.addAttribute("userposts", posts);

        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username);

        List<Amistades> requests = amistadesService.getFriendRequests(currentUserOpt.get());
        System.out.println(requests);
        model.addAttribute("requests", requests);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("username") String newUsername,
                                @RequestParam("bio") String newBio, 
                                @RequestParam("pfp") MultipartFile pfp, 
                                Model model) {
        String currentUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(currentUsername);

        usertable.setUsername(newUsername);
        usertable.setBio(newBio);

        if (!pfp.isEmpty()) {
            Path directoryImages = Paths.get("src/main/resources/static/images");
            String rutaAbsoluta = directoryImages.toFile().getAbsolutePath();
            String filename = UUID.randomUUID().toString() + "_" + pfp.getOriginalFilename();

            try {
                byte[] byteImg = pfp.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta, filename);
                Files.write(rutaCompleta, byteImg);

                usertable.setPfp(filename);

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Error al subir la imagen.");
                return "profile";
            }
        }

        userService.save(usertable);

        model.addAttribute("username", usertable.getUsername());
        model.addAttribute("correo", usertable.getCorreo());
        model.addAttribute("bio", usertable.getBio());
        model.addAttribute("pfp", usertable.getPfp()); // Agrega la foto de perfil al modelo

        return "profile";
    }


    @GetMapping("/profile-search/{username}")
    public String viewProfile(@PathVariable String username, Model model, Authentication authentication) {
        Usertable usertable = userService.findByUsername(username);
        
        if (usertable != null) {
            model.addAttribute("profileUser", usertable);
            boolean isOwnProfile = authentication != null && 
                authentication.getName().equals(usertable.getCorreo());
            model.addAttribute("isOwnProfile", isOwnProfile);

            String currentUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable2 = userService.findByCorreo(currentUsername);
            boolean sonAmigos = amistadesService.sonAmigos(usertable2, usertable);
            model.addAttribute("profileUserTieneSolicitud", sonAmigos);
            return "profile-search";
        } else {
            return "error";
        }
    }

}