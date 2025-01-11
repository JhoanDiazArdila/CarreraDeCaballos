/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Clases;

/**
 *
 * @author jhoan
 */
public class BurroCompetencia {
    
    private int idBurroCompetencia;
    private Burro burro;
    private Competencia competencia;
    private int puesto;
    
    // CONSTRUCTOR

    public BurroCompetencia() {
    }

    public BurroCompetencia(int idBurroCompetencia) {
        this.idBurroCompetencia = idBurroCompetencia;
    }
    
    public BurroCompetencia(int idBurroCompetencia, int puesto) {
        this.idBurroCompetencia = idBurroCompetencia;
        this.puesto = puesto;
    }

    public BurroCompetencia(Burro burro, Competencia competencia) {
        this.burro = burro;
        this.competencia = competencia;
    }
    
    // GETTERS SETTERS

    public int getIdBurroCompetencia() {
        return idBurroCompetencia;
    }

    public void setIdBurroCompetencia(int idBurroCompetencia) {
        this.idBurroCompetencia = idBurroCompetencia;
    }

    public Burro getBurro() {
        return burro;
    }
    public int getBurroId(){
        return burro.getIdBurro();
    }
    

    public void setBurro(Burro burro) {
        this.burro = burro;
    }

    public Competencia getCompetencia() {
        return competencia;
    }
    public int getCompetenciaId(){
        return competencia.getIdCompetencia();
    }
    
    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }
    
    
    
    
    
}
