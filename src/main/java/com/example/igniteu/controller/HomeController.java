package com.example.igniteu.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.example.igniteu.Services.AmistadesService;
import com.example.igniteu.Services.ComentarioService;
import com.example.igniteu.Services.LikeService;
import com.example.igniteu.Services.PostCompartidoService;
import com.example.igniteu.Services.PostService;
import com.example.igniteu.Services.UserService;
import com.example.igniteu.models.Amistades;
import com.example.igniteu.models.Comentario;
import com.example.igniteu.models.Post;
import com.example.igniteu.models.PostCompartido;
import com.example.igniteu.models.Usertable;

@Controller
public class HomeController {
    @Autowired
    private AmistadesService amistadesService;

    UserRepository repo;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    ComentarioService comentarioService;

    @Autowired
    AmistadesService amistadService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostCompartidoService postCompartidoService;

    @GetMapping({ "", "/" })
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String homeinitString(Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usertable usertable = userService.findByCorreo(username);
        model.addAttribute("pfp",
                usertable.getPfp());

        Post postx = new Post();
        // Obtener el user_id del usuario autenticado
        Integer userId = usertable.getId();

        List<Post> posts = postService.getPostUserId(userId);

        List<Usertable> amistades = amistadesService.getAmistadesAceptadas(usertable);

        // Obtener los IDs de los amigos
        List<Integer> friendIds = amistades.stream()
                .map(Usertable::getId)
                .collect(Collectors.toList());

        // Obtener los posts de los amigos
        List<Post> friendPosts = postService.getPostsByUserIds(friendIds);

        List<Post> userPostsList = new ArrayList<>();
        userPostsList.addAll(posts);

        Map<Integer, List<Comentario>> userpostCommentsMap = new HashMap<>();
        for (Post post : userPostsList) {
            List<Comentario> comentarios = comentarioService.getCommentsByPostId(post.getIdpost());
            userpostCommentsMap.put(post.getIdpost(), comentarios);
        }

        // Combinar las listas
        List<Post> combinedPosts = new ArrayList<>();
        combinedPosts.addAll(posts);
        combinedPosts.addAll(friendPosts);

        Map<Integer, List<Comentario>> postCommentsMap = new HashMap<>();
        for (Post post : combinedPosts) {
            List<Comentario> comentarios = comentarioService.getCommentsByPostId(post.getIdpost());
            postCommentsMap.put(post.getIdpost(), comentarios);
        }

        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username);
        List<Amistades> requests = amistadesService.getFriendRequests(currentUserOpt.get());

        // Manejo de likes
        Map<Integer, Boolean> likesMap = new HashMap<>();
        Map<Integer, Integer> likesCountMap = new HashMap<>();
        Map<Integer, Integer> commentsCountMap = new HashMap<>();

        for (Post post : combinedPosts) {
            boolean isLiked = likeService.isLikedByUser(post, usertable);
            likesMap.put(post.getIdpost(), isLiked);

            Integer likesCount = likeService.countLikesByPost(post);
            likesCountMap.put(post.getIdpost(), likesCount);

            Integer commentsCount = comentarioService.countCommentsByPostId(post.getIdpost());
            commentsCountMap.put(post.getIdpost(), commentsCount);
        }

        PostCompartido postCompartido = new PostCompartido();
        model.addAttribute("postCompartido", postCompartido);

        // Añadir el mapa al modelo para usarlo en la vista
        model.addAttribute("likesMap", likesMap);
        model.addAttribute("likesCountMap", likesCountMap);
        model.addAttribute("commentsCountMap", commentsCountMap);
        model.addAttribute("postCommentsMap", postCommentsMap);

        model.addAttribute("userpostCommentsMap", userpostCommentsMap);

        model.addAttribute("requests", requests);

        model.addAttribute("username", usertable.getUsername());

        model.addAttribute("userposts", posts);

        model.addAttribute("friendPosts", friendPosts);

        model.addAttribute("combinedPosts", combinedPosts);

        model.addAttribute("amistades", amistades);

        model.addAttribute("postimage", postx.getImageURL());

        // Imprimir los valores de los posts en la consola
        for (Post post : friendPosts) {
            System.out.println("Post ID: " + post.getIdpost());
            System.out.println("Contenido: " + post.getContenido());
            System.out.println("Fecha de Publicación: " + post.getFecha_publicacion());
        }

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
        List<PostCompartido> postsCompartidos = postCompartidoService.getPostsCompartidosByUser(userId);

        List<Post> userPostsList = new ArrayList<>();
        userPostsList.addAll(posts);

        Map<Integer, List<Comentario>> userpostCommentsMap = new HashMap<>();
        for (Post post : userPostsList) {
            List<Comentario> comentarios = comentarioService.getCommentsByPostId(post.getIdpost());
            userpostCommentsMap.put(post.getIdpost(), comentarios);
        }

        model.addAttribute("postsCompartidos", postsCompartidos);

        // Imprimir los valores de los posts en la consola
        for (Post post : posts) {
            System.out.println("Post ID: " + post.getIdpost());
            System.out.println("Contenido: " + post.getContenido());
            System.out.println("Fecha de Publicación: " + post.getFecha_publicacion());
        }

        Map<Integer, Boolean> likesMap = new HashMap<>();
        Map<Integer, Integer> likesCountMap = new HashMap<>();
        Map<Integer, Integer> commentsCountMap = new HashMap<>();

        for (Post post : userPostsList) {
            boolean isLiked = likeService.isLikedByUser(post, usertable);
            likesMap.put(post.getIdpost(), isLiked);

            Integer likesCount = likeService.countLikesByPost(post);
            likesCountMap.put(post.getIdpost(), likesCount);

            Integer commentsCount = comentarioService.countCommentsByPostId(post.getIdpost());
            commentsCountMap.put(post.getIdpost(), commentsCount);
        }

        for (PostCompartido postCompartido : postsCompartidos) {
            Post originalPost = postCompartido.getOriginalPost();
    
            boolean isLiked = likeService.isLikedByUser(originalPost, usertable);
            likesMap.put(originalPost.getIdpost(), isLiked);
    
            Integer likesCount = likeService.countLikesByPost(originalPost);
            likesCountMap.put(originalPost.getIdpost(), likesCount);
    
            Integer commentsCount = comentarioService.countCommentsByPostId(originalPost.getIdpost());
            commentsCountMap.put(originalPost.getIdpost(), commentsCount);
        }

        // Añadir el mapa al modelo para usarlo en la vista
        model.addAttribute("likesMap", likesMap);
        model.addAttribute("likesCountMap", likesCountMap);
        model.addAttribute("commentsCountMap", commentsCountMap);
        model.addAttribute("userpostCommentsMap", userpostCommentsMap);

        model.addAttribute("userposts", posts);

        model.addAttribute("userpostCommentsMap", userpostCommentsMap);

        Optional<Usertable> currentUserOpt = amistadesService.findUserBycorreo(username);

        List<Amistades> requests = amistadesService.getFriendRequests(currentUserOpt.get());
        System.out.println(requests);
        model.addAttribute("requests", requests);

        List<Usertable> amistades = amistadesService.getAmistadesAceptadas(usertable);
        model.addAttribute("amistades", amistades);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("username") String newUsername,
            @RequestParam("bio") String newBio,
            @RequestParam("pfp") MultipartFile pfp,
            Model model) {
        String currentUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
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
        Usertable profileUser = userService.findByUsername(username);

        model.addAttribute("pfp", usertable.getPfp());

        if (profileUser != null) {
            model.addAttribute("profileUser", profileUser);

            if (usertable != null) {
                String loggedInUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                        .getUsername();
                Usertable loggedInUser = userService.findByCorreo(loggedInUsername);
                List<Usertable> amistades = amistadesService.getAmistadesAceptadas(loggedInUser);
                model.addAttribute("amistades", amistades);

                model.addAttribute("profileUser", usertable);
                boolean isOwnProfile = authentication != null &&
                        authentication.getName().equals(usertable.getCorreo());
                model.addAttribute("isOwnProfile", isOwnProfile);

                String currentUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                        .getUsername();
                Usertable usertable2 = userService.findByCorreo(currentUsername);
                boolean sonAmigos = amistadesService.sonAmigos(usertable2, usertable);
                model.addAttribute("profileUserTieneSolicitud", sonAmigos);
                model.addAttribute("sonAmigos", sonAmigos);

                if (isOwnProfile || sonAmigos) {
                    Integer userId = usertable.getId();
                    List<Post> posts = postService.getPostUserId(userId);
                    List<PostCompartido> postsCompartidos = postCompartidoService.getPostsCompartidosByUser(userId);

                    Map<Integer, List<Comentario>> userpostCommentsMap = new HashMap<>();
                    for (Post post : posts) {
                        List<Comentario> comentarios = comentarioService.getCommentsByPostId(post.getIdpost());
                        userpostCommentsMap.put(post.getIdpost(), comentarios);
                    }

                    model.addAttribute("userposts", posts);
                    model.addAttribute("postsCompartidos", postsCompartidos);
                    model.addAttribute("userpostCommentsMap", userpostCommentsMap);
                }

                return "profile-search";
            }

            boolean isOwnProfile = authentication != null &&
                    authentication.getName().equals(profileUser.getCorreo());
            model.addAttribute("isOwnProfile", isOwnProfile);
            return "profile-search";
        } else {
            return "error";
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}