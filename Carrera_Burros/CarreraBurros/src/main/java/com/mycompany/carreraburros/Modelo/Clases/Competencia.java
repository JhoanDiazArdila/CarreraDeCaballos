/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Clases;

import java.time.LocalDate;
import java.sql.Date;

/**
 *
 * @author jhoan
 */
public class Competencia {
    
    private int idCompetencia;
    private String nombre;
    private LocalDate fechaInicio;
    private String lugar;
    private boolean finalizada;
    
    // Constructor

    public Competencia() {
    }

    public Competencia(int idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public Competencia(int idCompetencia, LocalDate fechaInicio) {
        this.idCompetencia = idCompetencia;
        this.fechaInicio = fechaInicio;
    }

    public Competencia(int idCompetencia, boolean finalizada) {
        this.idCompetencia = idCompetencia;
        this.finalizada = finalizada;
    }
    
    public Competencia(String nombre, LocalDate fechaInicio, String lugar) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.lugar = lugar;
        this.finalizada = false;
    }
    
    // GETTERS SETTERS

    public int getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(int idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
// ---------------
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public Date getFechaInicioAsDate() {
        return fechaInicio != null ? Date.valueOf(fechaInicio) : null;
    }
 // ---------------   
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
    
    
    
    
}
