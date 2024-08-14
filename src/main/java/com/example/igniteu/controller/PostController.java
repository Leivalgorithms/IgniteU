package com.example.igniteu.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.User;
import com.example.igniteu.Services.PostService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.Usertable;

@Controller
public class PostController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final PostService postService;

    // Constructor para inyección de dependencias
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, @RequestParam("pfp") MultipartFile pfp, Model model) {
        // Obtener el nombre de usuario del contexto de seguridad
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Obtener el usuario usando el correo (nombre de usuario)
        Usertable usertable = userService.findByCorreo(username);

        // Verificar si se encontró el usuario
        if (usertable == null) {
            model.addAttribute("error", "User not found");
            return "errorPage"; // Redirige a una página de error o muestra un mensaje en la vista actual
        }

        if (!pfp.isEmpty()) {
            Path directoryImages = Paths.get("src/main/resources/static/images");
            String rutaAbsoluta = directoryImages.toFile().getAbsolutePath();
            String filename = UUID.randomUUID().toString() + "_" + pfp.getOriginalFilename();

            try {
                byte[] byteImg = pfp.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta, filename);
                Files.write(rutaCompleta, byteImg);

                post.setImageURL(filename);

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Error al subir la imagen.");
                return "home";
            }
        }

        // Obtener el user_id del usuario autenticado
        Integer userId = usertable.getId();

        // Establecer el user_id en el post
        post.setUsuario_id(userId);
        // Guardar el post en la base de datos
        postService.savePost(post);

        // Obtener la lista actualizada de posts
        List<Post> userPosts = postService.getPostUserId(userId);
        model.addAttribute("userposts", userPosts);

        // Pausar el hilo durante 1 segundo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al pausar la ejecución.");
            return "home";
        }

        // Devuelve el fragmento de posts actualizado
        return "redirect:/home";
    }

}
