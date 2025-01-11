/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Persistencia;

/**
 *
 * @author jhoan
 */

import java.sql.*;
import java.util.*;
import java.util.logging.*;

public abstract class CRUD {
    
    private static Connection con;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
    
    
    // METODO DE PREPARED STATEMENT
    public static ResultSet consultarBD1(String sql, List<Object> parametros) {
        ResultSet rs = null;

        try {
            // Preparar la consulta
            PreparedStatement pstmt = con.prepareStatement(sql);

            // Asignar valores a los parámetros
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            // Ejecutar la consulta y obtener el ResultSet
            rs = pstmt.executeQuery();

        } catch (SQLException sqlex) {
            System.out.println("Error de SQL: " + sqlex.getMessage());
        } catch (RuntimeException rex) {
            System.out.println("Error de tiempo de ejecución: " + rex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex.getMessage());
        }

        // Retorna el ResultSet (puede ser null si ocurrió algún error)
        return rs;
    }
    
    
    // METODO DE PREPARED STATEMENT
    public static boolean insertarBD1(String sql, List<Object> parametros) {
        try {
            // Preparar la consulta
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                // Asignar valores a los parámetros
                for (int i = 0; i < parametros.size(); i++) {
                    pstmt.setObject(i + 1, parametros.get(i));
                }

                // Ejecutar la consulta
                pstmt.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex.getMessage());
            return false;
        }
        return true;
    }
    
    
    
     // METODO DE PREPARED STATEMENT
    public static boolean borrarBD1(String sql, List<Object> parametros) {
        try {
            // Preparar la consulta
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                // Asignar valores a los parámetros
                for (int i = 0; i < parametros.size(); i++) {
                    pstmt.setObject(i + 1, parametros.get(i));
                }

                // Ejecutar la consulta
                int filasAfectadas = pstmt.executeUpdate();
                System.out.println("Filas eliminadas: " + filasAfectadas);

                return filasAfectadas > 0; // Retorna true si al menos una fila fue afectada
            }
        } catch (SQLException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex.getMessage());
            return false;
        }
    }
    
    
     // METODO DE PREPARED STATEMENT
    public static boolean actualizarBD1(String sentencia, List<Object> parametros) {
        try {
            // Preparar la consulta
            try (PreparedStatement pstmt = con.prepareStatement(sentencia)) {
                // Asignar valores a los parámetros
                for (int i = 0; i < parametros.size(); i++) {
                    pstmt.setObject(i + 1, parametros.get(i));
                }

                // Ejecutar la actualización
                int filasAfectadas = pstmt.executeUpdate();
                System.out.println("Filas actualizadas: " + filasAfectadas);

                return filasAfectadas > 0; // Retorna true si al menos una fila fue afectada
            }
        } catch (SQLException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex.getMessage());
            return false;
        }
    }
    
     //-------------------------------------------------------------------------------  
    
        //Asigna la conexión
    public static Connection setConnection(Connection connection) {
        CRUD.con = connection;
        return connection;
    }

    //Retornar la conexin
    private static Connection getConnection() {
        return con;
    }

    //Cerrar la conexin
    private static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static boolean setAutoCommitBD(boolean parametro) {
        try {
            con.setAutoCommit(parametro);
        } catch (SQLException sqlex) {
            System.out.println("Error al configurar el autoCommit " + sqlex.getMessage());
            return false;
        }
        return true;
    }

    public static void cerrarConexion() {
        closeConnection(con);
    }

    public static boolean commitBD() {
        try {
            con.commit();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Error al hacer commit " + sqlex.getMessage());
            return false;
        }
    }

    public static boolean rollbackBD() {
        try {
            con.rollback();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Error al hacer rollback " + sqlex.getMessage());
            return false;
        }
    }
}
