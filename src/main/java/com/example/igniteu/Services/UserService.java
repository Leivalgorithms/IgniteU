package com.example.igniteu.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.igniteu.Repository.UserRepository;
import com.example.igniteu.models.Usertable;

import lombok.Data;

@Service
@Data
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usertable usertable = repo.findByCorreo(correo);

        if (usertable != null) {
            var springUser = User.withUsername(usertable.getCorreo())
                    .password(usertable.getContrasena())
                    .build();

            return springUser;
        }

        throw new UsernameNotFoundException("User not found with email: " + correo);
    }

    public List<Usertable> searchUsers(String query) {
        return repo.findByUsernameContainingIgnoreCase(query);
    }

    public Usertable findByCorreo(String correo) {
        return repo.findByCorreo(correo);
    }

    public Integer getUserIdByUsername(String username) {
        Optional<Usertable> userOptional = repo.findByUsername(username);
        return userOptional.map(Usertable::getId).orElse(null);
    }
}