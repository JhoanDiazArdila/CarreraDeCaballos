/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Clases;

/**
 *
 * @author jhoan
 */
public class Apuesta {
    
    private int idApuesta;
    private Participante participante;
    private BurroCompetencia burroCompetencia;
    private double monto;
    
    // CONSTRUCTOR

    public Apuesta() {
    }

    public Apuesta(int idApuesta) {
        this.idApuesta = idApuesta;
    }

    public Apuesta(int idApuesta, double monto) {
        this.idApuesta = idApuesta;
        this.monto = monto;
    }

    public Apuesta(Participante participante, BurroCompetencia burro, double monto) {
        this.participante = participante;
        this.burroCompetencia = burro;
        this.monto = monto;
    }
    
    // GETTERS AND SETTERS

    public int getIdApuesta() {
        return idApuesta;
    }

    public void setIdApuesta(int idApuesta) {
        this.idApuesta = idApuesta;
    }

    public Participante getParticipante() {
        return participante;
    }
    public String getParticipanteId(){
        return participante.getCedula();
    }
    

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public BurroCompetencia getBurro() {
        return burroCompetencia;
    }
    public int getBurroId(){
        return burroCompetencia.getIdBurroCompetencia();
    }
    
    
    public void setBurro(BurroCompetencia burro) {
        this.burroCompetencia = burro;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    
    
    
    
}
