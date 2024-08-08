package com.example.igniteu.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.igniteu.Services.PostCompartidoService;
import com.example.igniteu.Services.PostService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.PostCompartido;
import com.example.igniteu.models.Usertable;

@Controller
public class PostCompartidoController {

    @Autowired
    private PostCompartidoService postCompartidoService;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @PostMapping("/compartirPost")
    public String compartirPost(@RequestParam("originalPost.idpost") Integer originalPostId,
            @RequestParam("comentario") String comentario,
            Model model) {

        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);

        if (usertable == null) {
            model.addAttribute("error", "User not found");
            return "errorPage";
        }

        Optional<Post> originalPostOptional = postService.findById(originalPostId);
        if (!originalPostOptional.isPresent()) {
            model.addAttribute("error", "Original post not found");
            return "errorPage";
        }
        Post originalPost = originalPostOptional.get();

        PostCompartido postCompartido = new PostCompartido();
        postCompartido.setOriginalPost(originalPost);
        postCompartido.setSharedBy(usertable);
        postCompartido.setContenidopostcompartido(comentario);
        postCompartido.setFechahoracompartido(LocalDateTime.now());

        postCompartidoService.savePostCompartido(postCompartido);

        return "redirect:/home";
    }

}
