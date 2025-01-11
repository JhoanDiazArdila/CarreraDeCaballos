/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Controlador;

import com.mycompany.carreraburros.Modelo.Clases.Dueño;
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
public class ControlDueño {
    
    
    public static boolean createDueño(String cedula, String nombre, String telefono)throws SQLException{
        
        Dueño d1 = new Dueño(cedula, nombre, telefono);
        
        CRUD.setConnection(Conexion.getConexion());
        
        String insercion = "INSERT INTO dueños (cedula_dueño, nombre, telefono) "
                + "VALUES (?, ?, ?)";

        List<Object> parametros = Arrays.asList(d1.getCedula(),d1.getNombre(),d1.getTelefono());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.insertarBD1(insercion, parametros)){
                    System.out.println("Registro exitoso!!");
                        CRUD.commitBD();
                        return true;
                } else {
                    System.out.println("Error al registrar el Dueño");
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
    
    
    public static boolean updateDueño(String cedula, String telefono)throws SQLException {
        
        Dueño d1 = new Dueño(cedula, telefono);
        CRUD.setConnection(Conexion.getConexion());
        
        String actualizacion = "UPDATE dueños SET telefono = ? WHERE cedula_dueño = ?";
        
        List<Object> parametros = Arrays.asList(d1.getTelefono(), d1.getCedula());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al actualizar el Dueño");
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
    
    
    public static boolean deleteDueño(String identification) throws SQLException {
        
        CRUD.setConnection(Conexion.getConexion());
        
        String borrar = "DELETE FROM dueños WHERE cedula_dueño = ?";
        
        List<Object> parametros = Arrays.asList(identification);
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.borrarBD1(borrar, parametros)){
                    System.out.println("El Dueño fue eliminado exitosamente.");
                    CRUD.commitBD(); 
                    return true;
                }else{
                    System.out.println("Error al eliminar el Dueño.");
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
    
    
    public static Dueño getDueño(String identification) throws SQLException {
        
        Dueño d1 = new Dueño();
        CRUD.setConnection(Conexion.getConexion());
        String obtener = "SELECT * FROM dueños WHERE cedula_dueño = ?";
        List<Object> parametros = Arrays.asList(identification);
        
        try {
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if(rs.next()){
                d1.setCedula(rs.getString("cedula_dueño"));
                d1.setNombre(rs.getString("nombre"));
                d1.setTelefono(rs.getString("telefono"));
            }
            rs.close();  
            
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Dueño: " + ex.getMessage());
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return d1;
    }
    
    
    public static boolean existeDueño(String identification) throws SQLException {
        
        boolean existe = false;
        CRUD.setConnection(Conexion.getConexion());
        
        String verificar = "SELECT 1 FROM dueños WHERE cedula_dueño = ?";
        List<Object> parametros = Arrays.asList(identification);

        try {
            ResultSet rs = CRUD.consultarBD1(verificar, parametros);

            // Si hay un resultado, significa que el dueño existe
            if (rs.next()) {
                existe = true;
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println("Error al verificar el Dueño: " + ex.getMessage());
            throw ex;
        } finally {
            CRUD.cerrarConexion();
        }
        return existe;
    }
    
    
    public static List<Dueño> getDueñoList() throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Dueño> dueñoList = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM dueños";
            ResultSet rs = CRUD.consultarBD1(obtener, new ArrayList<>());
            while(rs.next()){
                Dueño d1 = new Dueño();
                d1.setCedula(rs.getString("cedula_dueño"));
                d1.setNombre(rs.getString("nombre"));
                d1.setTelefono(rs.getString("telefono"));

                dueñoList.add(d1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Dueño: " + ex.getMessage());
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return dueñoList;
    }
    
    
    public static void showDueñoList(){
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Dueño> dueños = getDueñoList();
            System.out.println("~~~~~~~ LISTA DE DUEÑOS ~~~~~~~");
            for(Dueño d1 : dueños){
                System.out.println("Cedula Dueño: " + d1.getCedula());
                System.out.println("Nombre: " + d1.getNombre());
                System.out.println("Telefono: " + d1.getTelefono());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al obtener los Dueños: " + e.getMessage());
            CRUD.cerrarConexion();
        }
        
    }
    
    
}
