/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Persistencia;

/**
 *
 * @author jhoan
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class  Conexion {
    
    private static final String URL = "jdbc:mysql://localhost:3306/carrera_burros";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    
     // Singleton para la conexión
    private static Connection con = null;

    
    // Constructor privado para evitar instanciación
    private Conexion() {
    }

    
    
    public static Connection getConexion() {
        synchronized (Conexion.class) { // Bloqueo para hilos múltiples
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                Logger.getLogger(Conexion.class.getName()).log(Level.INFO, "¡Conexión exitosa a la base de datos!");
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "No se pudo establecer la conexión", ex);
            }
        }
        return con;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        if (con != null) {
            try {
                con.close();
                Logger.getLogger(Conexion.class.getName()).log(Level.INFO, "Conexión cerrada correctamente.");
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
            } finally {
                con = null; // Libera el recurso
            }
        }
    }
    
}
