/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Clases;

/**
 *
 * @author jhoan
 */
public abstract class Usuario {
    
    protected String cedula;
    protected String nombre;
    protected String telefono;
    
    // CONSTRUCTOR

    public Usuario() {
    }

    public Usuario(String cedula) {
        this.cedula = cedula;
    }
    
    public Usuario(String cedula, String telefono) {
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public Usuario(String cedula, String nombre, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
    // GETTER SETTERS

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    
    
    
}
