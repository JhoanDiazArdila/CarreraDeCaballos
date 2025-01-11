/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Controlador;

import com.mycompany.carreraburros.Modelo.Clases.Burro;
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
public class ControlBurro {
    
    
    public static boolean createBurro(Dueño dueño, String nombre, 
            int edad, String raza)throws SQLException{
        
        Burro b1 = new Burro(dueño, nombre, edad, raza);
        
        CRUD.setConnection(Conexion.getConexion());
        
        String insercion = "INSERT INTO burros (cedula_dueño, nombre, edad, raza) "
                + "VALUES (?, ?, ?, ?)";

        List<Object> parametros = Arrays.asList(b1.getCedulaDueño(),b1.getNombre(),
                b1.getEdad(),b1.getRaza());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.insertarBD1(insercion, parametros)){
                    System.out.println("Registro exitoso!!");
                        CRUD.commitBD();
                        return true;
                } else {
                    System.out.println("Error al registrar el Burro");
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
    
    
    public static boolean cambiarDueño(int idBurro, Dueño dueño)throws SQLException {
        
        Burro b1 = new Burro(idBurro, dueño);
        CRUD.setConnection(Conexion.getConexion());
        
        String actualizacion = "UPDATE burros SET cedula_dueño = ? WHERE id_burro = ?";
        
        List<Object> parametros = Arrays.asList(b1.getCedulaDueño(), b1.getIdBurro());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al actualizar el nuevo Dueño del Burro");
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
    
    
    public static boolean deleteBurro(int identification) throws SQLException {
        
        CRUD.setConnection(Conexion.getConexion());
        
        String borrar = "DELETE FROM burros WHERE id_burro = ?";
        
        List<Object> parametros = Arrays.asList(identification);
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.borrarBD1(borrar, parametros)){
                    System.out.println("El Burro fue eliminado exitosamente.");
                    CRUD.commitBD(); 
                    return true;
                }else{
                    System.out.println("Error al eliminar el Burro.");
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
    
    
    public static Burro getBurro(int identification) throws SQLException {
        
        Burro b1 = new Burro();
        CRUD.setConnection(Conexion.getConexion());
        String obtener = "SELECT * FROM burros WHERE id_burro = ?";
        List<Object> parametros = Arrays.asList(identification);
        
        try {
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if(rs.next()){
                b1.setIdBurro(rs.getInt("id_burro"));
                b1.setDueño(new Dueño(rs.getString("cedula_dueño")));
                b1.setNombre(rs.getString("nombre"));
                b1.setEdad(rs.getInt("edad"));
                b1.setRaza(rs.getString("raza"));
            }
            rs.close();  
            
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Burro: " + ex.getMessage());
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return b1;
    }
    
    
    public static List<Burro> getBurroList() throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Burro> burroList = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM burros";
            ResultSet rs = CRUD.consultarBD1(obtener, new ArrayList<>());
            while(rs.next()){
                Burro b1 = new Burro();
                b1.setIdBurro(rs.getInt("id_burro"));
                b1.setDueño(new Dueño(rs.getString("cedula_dueño")));
                b1.setNombre(rs.getString("nombre"));
                b1.setEdad(rs.getInt("edad"));
                b1.setRaza(rs.getString("raza"));

                burroList.add(b1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener el Burro: " + ex.getMessage());
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return burroList;
    }
    
    
    public static void showBurroList(){
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Burro> burros = getBurroList();
            System.out.println("~~~~~~~ LISTA DE BURROS ~~~~~~~");
            for(Burro b1 : burros){
                System.out.println("ID Burro: " + b1.getIdBurro());
                System.out.println("Nombre: " + b1.getNombre());
                System.out.println("Dueño ID: " + b1.getCedulaDueño());
                System.out.println("Edad: " + b1.getEdad());
                System.out.println("Raza: " + b1.getRaza());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al obtener los Burros: " + e.getMessage());
            CRUD.cerrarConexion();
        }
    }
    
    
    public static void listaBurrosPorDueño(String cedula) throws SQLException{
        
        try{
            CRUD.setConnection(Conexion.getConexion());
            ArrayList<Burro> burroList = new ArrayList<>();
            List<Object> parametros = Arrays.asList(cedula);

            String obtener = "SELECT * FROM burros WHERE cedula_dueño = ?";
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if (!rs.isBeforeFirst()) { // No hay resultados
                System.out.println("No burros registrados al Dueño.");
            } else {
                while(rs.next()){
                    Burro b1 = new Burro();
                    b1.setIdBurro(rs.getInt("id_burro"));
                    b1.setDueño(new Dueño(rs.getString("cedula_dueño")));
                    b1.setNombre(rs.getString("nombre"));
                    b1.setEdad(rs.getInt("edad"));
                    b1.setRaza(rs.getString("raza"));

                    burroList.add(b1);  
                }
                rs.close();
                System.out.println("~~~~~~~ LISTA DE BURROS DEL DUEÑO ~~~~~~~");
                for(Burro b1 : burroList){
                    System.out.println("ID Burro: " + b1.getIdBurro());
                    System.out.println("Nombre: " + b1.getNombre());
                    System.out.println("Dueño ID: " + b1.getCedulaDueño());
                    System.out.println("Edad: " + b1.getEdad());
                    System.out.println("Raza: " + b1.getRaza());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener la lista de animales: " + ex.getMessage());
            throw ex; // Relanzar excepción para manejarla en capas superiores
        } finally {
            // Cerrar la conexión
            CRUD.cerrarConexion();
        }
    }
    
    
    
    public static boolean cumplirAños(int idBurro) throws SQLException{
        
        try{
            CRUD.setConnection(Conexion.getConexion());
            
            Burro b1 = getBurro(idBurro);
            b1.cumplirAños();
            
            String actualizacion = "UPDATE burros SET edad = ? WHERE id_burro = ?";
            List<Object> parametros = Arrays.asList(b1.getEdad(), b1.getIdBurro());
        
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al actualizar la edad del Burro");
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
    
    
    public static boolean existeBurro(int identification) throws SQLException {
        
        boolean existe = false;
        CRUD.setConnection(Conexion.getConexion());
        
        String verificar = "SELECT 1 FROM burros WHERE id_burro = ?";
        List<Object> parametros = Arrays.asList(identification);

        try {
            ResultSet rs = CRUD.consultarBD1(verificar, parametros);

            if (rs.next()) {
                existe = true;
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println("Error al verificar el Burro: " + ex.getMessage());
            throw ex;
        } finally {
            CRUD.cerrarConexion();
        }
        return existe;
    }
    
    
}
