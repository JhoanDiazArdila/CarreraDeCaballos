/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Controlador;

import com.mycompany.carreraburros.Modelo.Clases.Competencia;
import com.mycompany.carreraburros.Modelo.Persistencia.CRUD;
import com.mycompany.carreraburros.Modelo.Persistencia.Conexion;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

/**
 *
 * @author jhoan
 */
public class ControlCompetencia {
    
    
    public static boolean createCompetencia(String nombre, LocalDate fechaInicio, 
        String lugar)throws SQLException{
        
        Competencia c1 = new Competencia(nombre, fechaInicio, lugar);
        
        CRUD.setConnection(Conexion.getConexion());
        
        String insercion = "INSERT INTO competencias (nombre, fecha_inicio, lugar, finalizada) "
                + "VALUES (?, ?, ?, ?)";

        List<Object> parametros = Arrays.asList(c1.getNombre(),c1.getFechaInicioAsDate(),
                c1.getLugar(),c1.isFinalizada());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.insertarBD1(insercion, parametros)){
                    System.out.println("Registro exitoso!!");
                        CRUD.commitBD();
                        return true;
                } else {
                    System.out.println("Error al registrar la Competencia");
                    CRUD.rollbackBD();  
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            CRUD.rollbackBD();
            throw ex;
        } finally {
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion(); 
        }
        return false;
    }
    
    
    public static boolean updateCompetencia(int idCompetencia, 
            LocalDate fechaInicio)throws SQLException {
        
        Competencia c1 = new Competencia(idCompetencia, fechaInicio);
        CRUD.setConnection(Conexion.getConexion());
        
        String actualizacion = "UPDATE competencias SET fecha_inicio = ? WHERE id_competencia = ?";
        
        List<Object> parametros = Arrays.asList(c1.getFechaInicioAsDate(), c1.getIdCompetencia());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al actualizar la Competencia");
                    CRUD.rollbackBD();  
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            CRUD.rollbackBD(); 
            throw ex; 
        } finally {
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion();  
        }
        return false;
    }
    
    
    public static boolean deleteCompetencia(int idCompetencia) throws SQLException {
        
        CRUD.setConnection(Conexion.getConexion());
        
        String borrar = "DELETE FROM competencias WHERE id_competencia = ?";
        
        List<Object> parametros = Arrays.asList(idCompetencia);
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.borrarBD1(borrar, parametros)){
                    System.out.println("El Competencia fue eliminada exitosamente.");
                    CRUD.commitBD(); 
                    return true;
                }else{
                    System.out.println("Error al eliminar la Competencia.");
                    CRUD.rollbackBD(); 
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            CRUD.rollbackBD(); 
            throw ex; 
        } finally {
            
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion();
        }
        return false;
    }
    
    
    public static Competencia getCompetencia(int idCompetencia) throws SQLException {
        
        Competencia c1 = new Competencia();
        CRUD.setConnection(Conexion.getConexion());
        
        String obtener = "SELECT * FROM competencias WHERE id_competencia = ?";
        List<Object> parametros = Arrays.asList(idCompetencia);
        
        try {
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if(rs.next()){
                c1.setIdCompetencia(rs.getInt("id_competencia"));
                c1.setNombre(rs.getString("nombre"));
                c1.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                c1.setLugar(rs.getString("lugar"));
                c1.setFinalizada(rs.getBoolean("finalizada"));
            }
            rs.close();  
            
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Competencia: " + ex.getMessage());
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return c1;
    }
    
    
    public static List<Competencia> getCompetenciaList(int filtro) throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Competencia> competenciaList = new ArrayList<>();
        
        try{
            String obtener = "";
            switch (filtro) {
                case 1:
                    obtener = "SELECT * FROM competencias WHERE finalizada = true";
                    break;
                case 2:
                    obtener = "SELECT * FROM competencias WHERE finalizada = false";
                    break;
                case 3:
                    obtener = "SELECT * FROM competencias";
                    break;
                default:
                    System.out.println("Filtro invalido");
                    return competenciaList;
            }
            
            ResultSet rs = CRUD.consultarBD1(obtener, new ArrayList<>());
            while(rs.next()){
                Competencia c1 = new Competencia();
                c1.setIdCompetencia(rs.getInt("id_competencia"));
                c1.setNombre(rs.getString("nombre"));
                c1.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                c1.setLugar(rs.getString("lugar"));
                c1.setFinalizada(rs.getBoolean("finalizada"));

                competenciaList.add(c1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Competencia: " + ex.getMessage());
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return competenciaList;
    }
    
    
    public static void showCompetenciaList(int filtro){
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Competencia> competencias = getCompetenciaList(filtro);
            System.out.println("~~~~~~~ LISTA DE COMPETENCIAS ~~~~~~~");
            for(Competencia c1 : competencias){
                System.out.println("ID Competencia: " + c1.getIdCompetencia());
                System.out.println("Nombre: " + c1.getNombre());
                System.out.println("Fecha Inicio: " + c1.getFechaInicioAsDate());
                System.out.println("Lugar: " + c1.getLugar());
                System.out.println("Finalizada: " + c1.isFinalizada());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al obtener los Competencias: " + e.getMessage());
            CRUD.cerrarConexion();
        }
    }
    
    
    
    public static boolean finalizarCompetencia(int idCompetencia, 
            boolean finalizada)throws SQLException {
        
        Competencia c1 = new Competencia(idCompetencia, finalizada);
        CRUD.setConnection(Conexion.getConexion());
        
        String actualizacion = "UPDATE competencias SET finalizada = ? WHERE id_competencia = ?";
        
        List<Object> parametros = Arrays.asList(c1.isFinalizada(), c1.getIdCompetencia());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al finalizar la Competencia");
                    CRUD.rollbackBD();  
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            CRUD.rollbackBD(); 
            throw ex; 
        } finally {
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion();  
        }
        return false;
    }
    
    
    
    
    
}
