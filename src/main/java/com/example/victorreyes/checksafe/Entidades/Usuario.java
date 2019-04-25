package com.example.victorreyes.checksafe.Entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Usuario {

    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private Integer grado;
    private String grupo;
    private String sexo;

    private String dato;
    private Bitmap imagen;

    public Usuario( Integer id, String nombre, String apellido, String email, Integer grado, String grupo, String sexo, Bitmap imagen ){

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.grado = grado;
        this.grupo = grupo;
        this.sexo = sexo;
        this.imagen = imagen;

    }

    public Usuario(){

    }
    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;

        try{
            byte[] byteCode = Base64.decode(dato, Base64.DEFAULT);
            this.imagen = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGrado() {
        return grado;
    }

    public void setGrado(Integer grado) {
        this.grado = grado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
