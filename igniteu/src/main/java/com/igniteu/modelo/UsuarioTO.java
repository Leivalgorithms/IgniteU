package com.igniteu.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UsuarioTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String direccion;
    private String correo;
    private int contrasena;

    public UsuarioTO() {
    }

    public UsuarioTO(int id, String nombre, String direccion, String correo, int contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getContrasena() {
        return contrasena;
    }

    public void setContrasena(int contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, direccion, correo, contrasena);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UsuarioTO usuarioTO = (UsuarioTO) obj;
        return id == usuarioTO.id &&
                contrasena == usuarioTO.contrasena &&
                Objects.equals(nombre, usuarioTO.nombre) &&
                Objects.equals(direccion, usuarioTO.direccion) &&
                Objects.equals(correo, usuarioTO.correo);
    }
}
