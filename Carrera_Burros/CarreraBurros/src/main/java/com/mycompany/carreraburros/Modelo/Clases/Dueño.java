/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Clases;

/**
 *
 * @author jhoan
 */
public class Dueño extends Usuario{

    public Dueño() {
    }

    public Dueño(String cedula) {
        super(cedula);
    }

    public Dueño(String cedula, String telefono) {
        super(cedula, telefono);
    }

    public Dueño(String cedula, String nombre, String telefono) {
        super(cedula, nombre, telefono);
    }
    
}
