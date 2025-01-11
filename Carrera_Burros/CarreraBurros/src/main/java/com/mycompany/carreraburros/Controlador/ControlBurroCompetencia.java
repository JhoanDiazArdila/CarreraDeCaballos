/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Controlador;

import com.mycompany.carreraburros.Modelo.Clases.Burro;
import com.mycompany.carreraburros.Modelo.Clases.BurroCompetencia;
import com.mycompany.carreraburros.Modelo.Clases.Competencia;
import com.mycompany.carreraburros.Modelo.Persistencia.CRUD;
import com.mycompany.carreraburros.Modelo.Persistencia.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jhoan
 */
public class ControlBurroCompetencia {
    
    
    public static boolean añadirBurroCompetencia(
            Burro burro, Competencia competencia)throws SQLException{
        
        BurroCompetencia bc1 = new BurroCompetencia(burro, competencia);
        
        CRUD.setConnection(Conexion.getConexion());
        
        String insercion = "INSERT INTO burro_competencia (id_burro, id_competencia) "
                + "VALUES (?, ?)";

        List<Object> parametros = Arrays.asList(bc1.getBurroId(),bc1.getCompetenciaId());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.insertarBD1(insercion, parametros)){
                    System.out.println("Registro exitoso!!");
                        CRUD.commitBD();
                        return true;
                } else {
                    System.out.println("Error al registrar el Burro en la Competencia");
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
    
    
    public static boolean deleteBurroCompetencia(Burro burro, 
            Competencia competencia) throws SQLException {
        
        CRUD.setConnection(Conexion.getConexion());
        
        String borrar = "DELETE FROM burro_competencia WHERE id_burro = ? AND id_competencia = ?";
        
        List<Object> parametros = Arrays.asList(burro.getIdBurro(), competencia.getIdCompetencia());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.borrarBD1(borrar, parametros)){
                    System.out.println("El Burro de la Competencia fue eliminado exitosamente.");
                    CRUD.commitBD(); 
                    return true;
                }else{
                    System.out.println("Error al eliminar el Burro de la Competencia.");
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
    
    
    public static BurroCompetencia getBurroCompetencia(Burro burro, 
            Competencia competencia) throws SQLException {
        
        BurroCompetencia bc1 = new BurroCompetencia();
        CRUD.setConnection(Conexion.getConexion());
        
        String obtener = "SELECT * FROM burro_competencia WHERE id_burro = ? AND id_competencia = ?";
        List<Object> parametros = Arrays.asList(burro.getIdBurro(), competencia.getIdCompetencia());
        
        try {
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if(rs.next()){
                bc1.setIdBurroCompetencia(rs.getInt("id_burro_competencia"));
                bc1.setBurro(new Burro(rs.getInt("id_burro")));
                bc1.setCompetencia(new Competencia(rs.getInt("id_competencia")));
                bc1.setPuesto(rs.getInt("puesto"));
            }
            rs.close();  
            
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Burro de la Competencia: " + ex.getMessage());
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return bc1;
    }
    
    
    public static BurroCompetencia getBurroCompetenciaDato(
            int codigo) throws SQLException {
        
        BurroCompetencia bc1 = new BurroCompetencia();
        CRUD.setConnection(Conexion.getConexion());
        
        String obtener = "SELECT * FROM burro_competencia WHERE id_burro_competencia = ?";
        List<Object> parametros = Arrays.asList(codigo);
        
        try {
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if(rs.next()){
                bc1.setIdBurroCompetencia(rs.getInt("id_burro_competencia"));
                bc1.setBurro(new Burro(rs.getInt("id_burro")));
                bc1.setCompetencia(new Competencia(rs.getInt("id_competencia")));
                bc1.setPuesto(rs.getInt("puesto"));
            }
            rs.close();  
            
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Burro de la Competencia: " + ex.getMessage());
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return bc1;
    }
    
    
    public static List<BurroCompetencia> getBurroCompetenciaList(Competencia competencia) throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<BurroCompetencia> burroCompetenciaList = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM burro_competencia WHERE id_competencia = ?";
            List<Object> parametros = Arrays.asList(competencia.getIdCompetencia());
            
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            while(rs.next()){
                BurroCompetencia bc1 = new BurroCompetencia();
                bc1.setIdBurroCompetencia(rs.getInt("id_burro_competencia"));
                bc1.setBurro(new Burro(rs.getInt("id_burro")));
                bc1.setCompetencia(new Competencia(rs.getInt("id_competencia")));
                bc1.setPuesto(rs.getInt("puesto"));

                burroCompetenciaList.add(bc1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Burro de la Competencia: " + ex.getMessage());
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return burroCompetenciaList;
    }
    
    
    public static void showBurroCompetenciaList(Competencia competencia){
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<BurroCompetencia> burrosCompetencia = getBurroCompetenciaList(competencia);
            
            System.out.println("~~~~~~~ LISTA DE BURROS EN COMPETENCIAS ~~~~~~~");
            
            for(BurroCompetencia bc1 : burrosCompetencia){
                Burro brr = ControlBurro.getBurro(bc1.getBurroId());
                Competencia cp = ControlCompetencia.getCompetencia(bc1.getCompetenciaId());
                
                System.out.println("~~~~~~~ BURRO EN COMPETENCIA ~~~~~~~");
                System.out.println("ID Burro en Competencia: " + bc1.getIdBurroCompetencia());
                System.out.println("ID Burro: " + brr.getIdBurro());
                System.out.println("Nombre: " + brr.getNombre());
                System.out.println("ID Competencia: " + cp.getIdCompetencia());
                System.out.println("Nombre Competencia: " + cp.getNombre());
                System.out.println("Fecha Inicio: " + cp.getFechaInicio());
                System.out.println("Puesto: " + bc1.getPuesto());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al obtener los Burros de las Competencias: " + e.getMessage());
            CRUD.cerrarConexion();
        }
    }
    
    
    public static boolean asignarPosicionBurroCompetencia(
            BurroCompetencia burroCompetencia, int puesto)throws SQLException {
        
        try{
            
            burroCompetencia.setPuesto(puesto);
            
            CRUD.setConnection(Conexion.getConexion());
            String actualizacion = "UPDATE burro_competencia SET puesto = ? WHERE id_burro_competencia = ?";

            List<Object> parametros = Arrays.asList(burroCompetencia.getPuesto(), burroCompetencia.getIdBurroCompetencia());
        
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al actualizar el la posicion del Burro en la Competencia");
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
