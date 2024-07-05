package com.example.igniteu.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.igniteu.Repository.UserRepository;
import com.example.igniteu.models.Usertable;

@Service
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

        return null;
    }

}
