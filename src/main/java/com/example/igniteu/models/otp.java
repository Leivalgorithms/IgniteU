package com.example.igniteu.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "otp_table")
public class otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String correo;
    private String otp;

}
