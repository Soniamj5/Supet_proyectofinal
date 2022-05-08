package com.animalhelpapp.proyectofinal_supet;

//tiene los datos del user que creamos en el registerActivity
public class User {
    public String nombre;
    public String email;

    //si no está este constructor sale error
    public User() {};

    //este constructor me ayuda e inicializar las variables
    public User(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
}

