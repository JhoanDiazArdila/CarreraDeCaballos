/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Controlador;

import com.mycompany.carreraburros.Modelo.Clases.Participante;
import com.mycompany.carreraburros.Modelo.Clases.Dueño;
import com.mycompany.carreraburros.Modelo.Persistencia.CRUD;
import com.mycompany.carreraburros.Modelo.Persistencia.Conexion;
import com.mycompany.carreraburros.Modelo.Persistencia.LogManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jhoan
 */
public class ControlParticipante {
    
    
    public static boolean createParticipante(Double saldo, String cedula, 
        String nombre, String telefono)throws SQLException{
        
        Participante p1 = new Participante(saldo, cedula, nombre, telefono);
        
        CRUD.setConnection(Conexion.getConexion());
        
        String insercion = "INSERT INTO participantes (cedula_part, nombre, telefono, saldo) "
                + "VALUES (?, ?, ?, ?)";

        List<Object> parametros = Arrays.asList(p1.getCedula(),p1.getNombre(),
                p1.getTelefono(),p1.getSaldo());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.insertarBD1(insercion, parametros)){
                    System.out.println("Registro exitoso!!");
                        CRUD.commitBD();
                        return true;
                } else {
                    System.out.println("Error al registrar el Participante");
                    CRUD.rollbackBD();  
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.rollbackBD();
            throw ex;
        } finally {
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion(); 
        }
        return false;
    }
    
    
    public static boolean updateParticipante(String cedula, String telefono)throws SQLException {
        
        Participante p1 = new Participante(cedula, telefono);
        CRUD.setConnection(Conexion.getConexion());
        
        String actualizacion = "UPDATE participantes SET telefono = ? WHERE cedula_part = ?";
        
        List<Object> parametros = Arrays.asList(p1.getTelefono(), p1.getCedula());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al actualizar el Participante");
                    CRUD.rollbackBD();  
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.rollbackBD(); 
            throw ex; 
        } finally {
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion();  
        }
        return false;
    }
    
    
    public static boolean deleteParticipante(String identification) throws SQLException {
        
        CRUD.setConnection(Conexion.getConexion());
        
        String borrar = "DELETE FROM participantes WHERE cedula_part = ?";
        
        List<Object> parametros = Arrays.asList(identification);
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.borrarBD1(borrar, parametros)){
                    System.out.println("El Participante fue eliminado exitosamente.");
                    CRUD.commitBD(); 
                    return true;
                }else{
                    System.out.println("Error al eliminar el Participante.");
                    CRUD.rollbackBD(); 
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.rollbackBD(); 
            throw ex; 
        } finally {
            
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion();
        }
        return false;
    }
    
    
    public static Participante getParticipante(String identification) throws SQLException {
        
        Participante p1 = new Participante();
        CRUD.setConnection(Conexion.getConexion());
        String obtener = "SELECT * FROM participantes WHERE cedula_part = ?";
        List<Object> parametros = Arrays.asList(identification);
        
        try {
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if(rs.next()){
                p1.setCedula(rs.getString("cedula_part"));
                p1.setNombre(rs.getString("nombre"));
                p1.setTelefono(rs.getString("telefono"));
                p1.setSaldo(rs.getDouble("saldo"));
            }
            rs.close();  
            
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Participante: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return p1;
    }
    
    
    public static void showParticipante(String identification){
        try{
            
            Participante p1 = getParticipante(identification);
            
            System.out.println("~~~~~~~ INFORMACION DE PARTICIPANTE ~~~~~~~");
            System.out.println("Cedula Participante: " + p1.getCedula());
            System.out.println("Nombre: " + p1.getNombre());
            System.out.println("Telefono: " + p1.getTelefono());
            System.out.println("Saldo: " + p1.getSaldo());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            
        } catch (SQLException ex) {
            System.out.println("Error al obtener los Participantes: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.cerrarConexion();
        }
    }
    
    
    public static List<Participante> getParticipanteList() throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Participante> participanteList = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM participantes";
            ResultSet rs = CRUD.consultarBD1(obtener, new ArrayList<>());
            while(rs.next()){
                Participante p1 = new Participante();
                p1.setCedula(rs.getString("cedula_part"));
                p1.setNombre(rs.getString("nombre"));
                p1.setTelefono(rs.getString("telefono"));
                p1.setSaldo(rs.getDouble("saldo"));

                participanteList.add(p1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Participante: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return participanteList;
    }
    
    
    public static void showParticipanteList(){
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Participante> participantes = getParticipanteList();
            System.out.println("~~~~~~~ LISTA DE PARTICIPANTES ~~~~~~~");
            for(Participante p1 : participantes){
                System.out.println("Cedula Participante: " + p1.getCedula());
                System.out.println("Nombre: " + p1.getNombre());
                System.out.println("Telefono: " + p1.getTelefono());
                System.out.println("Saldo: " + p1.getSaldo());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println("Error al obtener los Participantes: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.cerrarConexion();
        }
    }
    
    
    
    public static boolean agregarSaldo(String cedula, double saldo)throws SQLException {
        
        try{
            
            Participante p1 = getParticipante(cedula);
            p1.añadirSaldo(saldo);
            
            CRUD.setConnection(Conexion.getConexion());
            
            String actualizacion = "UPDATE participantes SET saldo = ? WHERE cedula_part = ?";
            List<Object> parametros = Arrays.asList(p1.getSaldo(), p1.getCedula());
        
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    showParticipante(cedula);
                    return true;
                } else {
                    System.out.println("Error al actualizar el nuevo Saldo del Participante");
                    CRUD.rollbackBD();  
                }
            }
        }catch (Exception ex) {
            System.out.println("Error en la transacción: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.rollbackBD(); 
            throw ex; 
        } finally {
            CRUD.setAutoCommitBD(true);
            CRUD.cerrarConexion();  
        }
        return false;
    }
    
    
    public static boolean existeParticipante(String identification) throws SQLException {
        
        boolean existe = false;
        CRUD.setConnection(Conexion.getConexion());
        
        String verificar = "SELECT 1 FROM participantes WHERE cedula_part = ?";
        List<Object> parametros = Arrays.asList(identification);

        try {
            ResultSet rs = CRUD.consultarBD1(verificar, parametros);

            // Si hay un resultado, significa que existe
            if (rs.next()) {
                existe = true;
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println("Error al verificar el Participante: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex;
        } finally {
            CRUD.cerrarConexion();
        }
        return existe;
    }
    
}
