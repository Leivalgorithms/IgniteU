package com.example.igniteu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/").permitAll()
                                                .requestMatchers("contact").permitAll()
                                                .requestMatchers("/igniteu/**").permitAll()
                                                .requestMatchers("/register").permitAll()
                                                .requestMatchers("/login").permitAll()
                                                .requestMatchers("/logout").permitAll()
                                                .requestMatchers("/forgot-password").permitAll()
                                                .requestMatchers("/verify-otp").permitAll()
                                                .requestMatchers("/validate-otp").permitAll()
                                                .requestMatchers("/change-password").permitAll()
                                                .requestMatchers("/send-otp").permitAll()
                                                .requestMatchers("/home").permitAll()
                                                .requestMatchers("/search").permitAll()
                                                .requestMatchers("/profile-search/**").permitAll()
                                                .requestMatchers("/send-request").permitAll()
                                                .requestMatchers("/profile").permitAll()
                                                .requestMatchers("/accept-request").permitAll()
                                                .requestMatchers("/deny-request").permitAll()
                                                .requestMatchers("/remove-friend").permitAll()
                                                .requestMatchers("/bandeja").permitAll()
                                                .requestMatchers("/app").permitAll()
                                                .requestMatchers("/chat/**").permitAll()
                                                .requestMatchers("/topic").permitAll()

                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/home", true)
                                                .permitAll())
                                .logout(config -> config
                                                .logoutSuccessUrl("/")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
