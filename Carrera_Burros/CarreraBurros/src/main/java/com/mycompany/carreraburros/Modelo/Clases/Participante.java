/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Clases;

/**
 *
 * @author jhoan
 */
public class Participante extends Usuario {

    private double saldo;
    
    
    // CONSTRUCTORS
    
    public Participante() {
    }

    public Participante(String cedula) {
        super(cedula);
    }

    public Participante(String cedula, String telefono) {
        super(cedula, telefono);
    }

    public Participante(double saldo, String cedula) {
        super(cedula);
        this.saldo = saldo;
    }
    
    public Participante(double saldo, String cedula, String nombre, String telefono) {
        super(cedula, nombre, telefono);
        this.saldo = saldo;
    }


    // GETTERS SETTERS

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    
    // METODOS
    
    public void añadirSaldo(double saldo){
        this.saldo = this.saldo + saldo;
    }
    
}
