/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Clases;

/**
 *
 * @author jhoan
 */
public class Burro {
    
    private int idBurro;
    private Dueño dueño;
    private String nombre;
    private int edad;
    private String raza;
    
    // Constructor

    public Burro() {
    }

    public Burro(int idBurro) {
        this.idBurro = idBurro;
    }

    public Burro(int idBurro, Dueño dueño) {
        this.idBurro = idBurro;
        this.dueño = dueño;
    }

    public Burro(Dueño dueño, String nombre, int edad, String raza) {
        this.dueño = dueño;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
    }
    
    // GETTERS SETTERS

    public int getIdBurro() {
        return idBurro;
    }

    public void setIdBurro(int idBurro) {
        this.idBurro = idBurro;
    }

    public Dueño getDueño() {
        return dueño;
    }
    public String getCedulaDueño(){
        return (dueño != null) ? dueño.getCedula(): "No Cedula";
    }

    
    public void setDueño(Dueño dueño) {
        this.dueño = dueño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
    
    
    // METODOS
    
    public void cumplirAños(){
        this.edad = this.edad + 1;
    }
    
    
    
    
}
