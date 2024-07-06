package com.example.igniteu.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.igniteu.models.Usertable;

public interface UserRepository extends JpaRepository<Usertable, Integer> {

    public Usertable findByCorreo(String correo);
}
