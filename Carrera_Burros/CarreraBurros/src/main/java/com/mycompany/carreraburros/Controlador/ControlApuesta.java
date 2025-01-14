/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Controlador;

import com.mycompany.carreraburros.Modelo.Clases.Apuesta;
import com.mycompany.carreraburros.Modelo.Clases.Burro;
import com.mycompany.carreraburros.Modelo.Clases.BurroCompetencia;
import com.mycompany.carreraburros.Modelo.Clases.Competencia;
import com.mycompany.carreraburros.Modelo.Clases.Participante;
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
public class ControlApuesta {
    
    
     public static boolean crearApuesta(BurroCompetencia competenciaBurro, 
             Participante participante, double monto)throws SQLException{
    
        if (monto <= 0){
            System.out.println("El monto debe ser mayor a 0");
            return false;
        }
        
        if(monto > participante.getSaldo()){
            System.out.println("El monto debe estar en el rango del saldo disponible del participante");
             return false;
        }
        
        Apuesta a1 = new Apuesta(participante, competenciaBurro, monto);
        
        CRUD.setConnection(Conexion.getConexion());
        
        String insercion = 
                "INSERT INTO apuestas "
                + "(cedula_part, id_burro_competencia, monto)"
                + "VALUES (?, ?, ?)";
    
        List<Object> parametros = Arrays.asList(
                a1.getParticipanteId(),
                a1.getBurroId(),
                a1.getMonto());
                
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.insertarBD1(insercion, parametros)){
                    System.out.println("Registro exitoso!!");
                        CRUD.commitBD();
                        return true;
                } else {
                    System.out.println("Error al registrar");
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
    
     
    public static boolean actualizarApuesta(int id, double monto)throws SQLException{
        
        Apuesta a1 = new Apuesta(id, monto);
        
        CRUD.setConnection(Conexion.getConexion());
        String actualizacion = 
                "UPDATE apuestas SET monto = ?  WHERE id_apuesta = ?";
        
        List<Object> parametros = Arrays.asList(
                a1.getMonto(),
                a1.getIdApuesta());
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                
                if(CRUD.actualizarBD1(actualizacion, parametros)){
                    System.out.println("Actualizacion exitosa!!");
                    CRUD.commitBD();
                    return true;
                } else {
                    System.out.println("Error al actualizar ");
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
    
    
    public static boolean borrarApuesta(int id) throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        String delete = "DELETE FROM apuestas WHERE id_apuesta = ?";
        
        List<Object> parameters = Arrays.asList(id);
        
        try{
            if(CRUD.setAutoCommitBD(false)){
                if(CRUD.borrarBD1(delete, parameters)){
                    System.out.println("Fue eliminado exitosamente.");
                    CRUD.commitBD(); 
                    return true;
                }else{
                    System.out.println("Error al eliminar.");
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
    
    
    public static Apuesta obtenerApuesta(int id) throws SQLException {
        
        Apuesta a1 = new Apuesta();
        CRUD.setConnection(Conexion.getConexion());
        
        String obtener = "SELECT * FROM apuestas WHERE id_apuesta = ?";
        List<Object> parametros = Arrays.asList(id);
        
        try {
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            
            if(rs.next()){
                a1.setIdApuesta(rs.getInt("id_apuesta"));
                a1.setParticipante(new Participante(rs.getString("cedula_part")));
                a1.setBurro(new BurroCompetencia(rs.getInt("id_burro_competencia")));
                a1.setMonto(rs.getDouble("monto"));
            }
            rs.close();
        }catch (SQLException ex) {
            System.out.println("Error al obtenerlo " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex; 
        } finally {
            CRUD.cerrarConexion();
        }
        return a1;
    }
    
    
    public static void showApuesta(int idApuesta){
        try{
            
            Apuesta a1 = obtenerApuesta(idApuesta);
            Participante p1 = ControlParticipante.getParticipante(a1.getParticipanteId());
            BurroCompetencia bc1 = ControlBurroCompetencia.getBurroCompetenciaDato(a1.getBurroId());
            Burro b1 = ControlBurro.getBurro(bc1.getBurroId());
            Competencia c1 = ControlCompetencia.getCompetencia(bc1.getCompetenciaId());
            
            
            System.out.println("~~~~~~~ INFORMACION DE APUESTA ~~~~~~~");
            System.out.println("ID Apuesta: " + a1.getIdApuesta());
            System.out.println("Monto apostado: " + a1.getMonto());
            System.out.println("Cedula Participante: " + p1.getCedula());
            System.out.println("Nombre: " + p1.getNombre());
            System.out.println("Telefono: " + p1.getTelefono());
            System.out.println("ID Competencia: " + c1.getIdCompetencia());
            System.out.println("Nombre Competencia: " + c1.getNombre());
            System.out.println("Lugar Competencia: " + c1.getLugar());
            System.out.println("ID del Burro: " + b1.getIdBurro());
            System.out.println("Nombre del Burro: " + b1.getNombre());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            
        } catch (SQLException e) {
            System.out.println("Error al obtener los Participantes: " + e.getMessage());
            LogManager.logError("CarreraBurros", e.getMessage(), e);
            CRUD.cerrarConexion();
        }
    }
    
    
    public static List<Apuesta> getApuestaList() throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Apuesta> listaApuestas = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM apuestas";
            ResultSet rs = CRUD.consultarBD1(obtener, new ArrayList<>());
            while(rs.next()){
                Apuesta a1 = new Apuesta();
                a1.setIdApuesta(rs.getInt("id_apuesta"));
                a1.setParticipante(new Participante(rs.getString("cedula_part")));
                a1.setBurro(new BurroCompetencia(rs.getInt("id_burro_competencia")));
                a1.setMonto(rs.getDouble("monto"));
                
                listaApuestas.add(a1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener la lista : " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return listaApuestas;
    }
    public static void verListaApuestas(){
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Apuesta> apuestas = getApuestaList();
            
            System.out.println("~~~~~~~~~~~~~ LISTA DE APUESTAS ~~~~~~~~~~~~~");
            for(Apuesta a1 : apuestas){
                System.out.println("ID apuesta: " + a1.getIdApuesta());
                System.out.println("Cedula Participante: " + a1.getParticipanteId());
                System.out.println("Id Competencia Burro: " + a1.getBurroId());
                System.out.println("Monto: " + a1.getMonto());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println("Error al obtener lista: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.cerrarConexion();
        }
        
    }
    
    
    public static boolean existeApuesta(int identification) throws SQLException {
        boolean existe = false;
        CRUD.setConnection(Conexion.getConexion());

        String verificar = "SELECT 1 FROM apuestas WHERE id_apuesta = ?";
        List<Object> parametros = Arrays.asList(identification);

        try {
            ResultSet rs = CRUD.consultarBD1(verificar, parametros);
            if (rs.next()) {
                existe = true;
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error al verificar el Apuesta: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex;
        } finally {
            CRUD.cerrarConexion();
        }
        return existe;
    }
    
    
    // LISTAS POR FILTRO
    
    public static List<Apuesta> getApuestaListByCompetencia(int idCompetencia) throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Apuesta> listaApuestas = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM apuestas a "
                    + "JOIN burro_competencia bc ON a.id_burro_competencia = bc.id_burro_competencia "
                    + "WHERE bc.id_competencia = ?";
            List<Object> parametros = Arrays.asList(idCompetencia);
            
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            while(rs.next()){
                Apuesta a1 = new Apuesta();
                a1.setIdApuesta(rs.getInt("id_apuesta"));
                a1.setParticipante(new Participante(rs.getString("cedula_part")));
                a1.setBurro(new BurroCompetencia(rs.getInt("id_burro_competencia")));
                a1.setMonto(rs.getDouble("monto"));
                
                listaApuestas.add(a1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener la lista : " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return listaApuestas;
    }
    public static void verListaApuestasByCompetencia(int idCompetencia) throws SQLException{
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Apuesta> apuestas = getApuestaListByCompetencia(idCompetencia);
            
            
            System.out.println("~~~~~~~~~~~~~ LISTA DE APUESTAS ~~~~~~~~~~~~~");
            for(Apuesta a1 : apuestas){
                BurroCompetencia bc1 = ControlBurroCompetencia.getBurroCompetenciaDato(a1.getBurroId());
                Burro b1 = ControlBurro.getBurro(bc1.getBurroId());
                Participante p1 = ControlParticipante.getParticipante(a1.getParticipanteId());
                
                System.out.println("ID apuesta: " + a1.getIdApuesta());
                System.out.println("Monto: " + a1.getMonto());
                System.out.println("Cedula Participante: " + p1.getCedula());
                System.out.println("Nombre Participante: " + p1.getNombre());
                System.out.println("Telefono Participante: " + p1.getTelefono());
                System.out.println("Id Burro: " + b1.getIdBurro());
                System.out.println("Nombre Burro: " + b1.getNombre());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println("Error al obtener lista: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.cerrarConexion();
        }
        
    }
    
    
    public static List<Apuesta> getApuestaListByParticipante(String cedula) throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Apuesta> listaApuestas = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM apuestas a WHERE a.cedula_part = ?";
            List<Object> parametros = Arrays.asList(cedula);
            
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            while(rs.next()){
                Apuesta a1 = new Apuesta();
                a1.setIdApuesta(rs.getInt("id_apuesta"));
                a1.setParticipante(new Participante(rs.getString("cedula_part")));
                a1.setBurro(new BurroCompetencia(rs.getInt("id_burro_competencia")));
                a1.setMonto(rs.getDouble("monto"));
                
                listaApuestas.add(a1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener la lista : " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return listaApuestas;
    }
    public static void verListaApuestasByParticipante(String cedula) throws SQLException{
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Apuesta> apuestas = getApuestaListByParticipante(cedula);
            
            
            System.out.println("~~~~~~~~~~~~~ LISTA DE APUESTAS ~~~~~~~~~~~~~");
            for(Apuesta a1 : apuestas){
                BurroCompetencia bc1 = ControlBurroCompetencia.getBurroCompetenciaDato(a1.getBurroId());
                Burro b1 = ControlBurro.getBurro(bc1.getBurroId());
                Participante p1 = ControlParticipante.getParticipante(a1.getParticipanteId());
                
                System.out.println("ID apuesta: " + a1.getIdApuesta());
                System.out.println("Monto: " + a1.getMonto());
                System.out.println("Cedula Participante: " + p1.getCedula());
                System.out.println("Nombre Participante: " + p1.getNombre());
                System.out.println("Telefono Participante: " + p1.getTelefono());
                System.out.println("Id Burro: " + b1.getIdBurro());
                System.out.println("Nombre Burro: " + b1.getNombre());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println("Error al obtener lista: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.cerrarConexion();
        }
        
    }

    
    public static List<Apuesta> getApuestaListByBurro(int competencia, int burro) throws SQLException{
        
        CRUD.setConnection(Conexion.getConexion());
        List<Apuesta> listaApuestas = new ArrayList<>();
        
        try{
            String obtener = "SELECT * FROM apuestas a "
                    + "JOIN burro_competencia bc ON a.id_burro_competencia = bc.id_burro_competencia "
                    + "WHERE bc.id_burro = ? AND bc.id_competencia = ?";
            List<Object> parametros = Arrays.asList(burro, competencia);
            
            ResultSet rs = CRUD.consultarBD1(obtener, parametros);
            while(rs.next()){
                Apuesta a1 = new Apuesta();
                a1.setIdApuesta(rs.getInt("id_apuesta"));
                a1.setParticipante(new Participante(rs.getString("cedula_part")));
                a1.setBurro(new BurroCompetencia(rs.getInt("id_burro_competencia")));
                a1.setMonto(rs.getDouble("monto"));
                
                listaApuestas.add(a1);  
            }
        }catch (SQLException ex) {
            System.out.println("Error al obtener la lista : " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            throw ex; 
        } finally {
            
            CRUD.cerrarConexion();
        }
        return listaApuestas;
    }
    public static void verListaApuestasByBurro(int competencia, int burro) throws SQLException{
        try{
            CRUD.setConnection(Conexion.getConexion());
            List<Apuesta> apuestas = getApuestaListByBurro(competencia, burro);
            
            
            System.out.println("~~~~~~~~~~~~~ LISTA DE APUESTAS ~~~~~~~~~~~~~");
            for(Apuesta a1 : apuestas){
                BurroCompetencia bc1 = ControlBurroCompetencia.getBurroCompetenciaDato(a1.getBurroId());
                Burro b1 = ControlBurro.getBurro(bc1.getBurroId());
                Participante p1 = ControlParticipante.getParticipante(a1.getParticipanteId());
                
                System.out.println("ID apuesta: " + a1.getIdApuesta());
                System.out.println("Monto: " + a1.getMonto());
                System.out.println("Cedula Participante: " + p1.getCedula());
                System.out.println("Nombre Participante: " + p1.getNombre());
                System.out.println("Telefono Participante: " + p1.getTelefono());
                System.out.println("Id Burro: " + b1.getIdBurro());
                System.out.println("Nombre Burro: " + b1.getNombre());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            CRUD.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println("Error al obtener lista: " + ex.getMessage());
            LogManager.logError("CarreraBurros", ex.getMessage(), ex);
            CRUD.cerrarConexion();
        }
        
    }
    

    
    
}


