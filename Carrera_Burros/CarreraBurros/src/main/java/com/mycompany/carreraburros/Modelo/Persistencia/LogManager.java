/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carreraburros.Modelo.Persistencia;

/**
 *
 * @author jhoan
 */



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {

    private static final String LOG_FILE = "logs.txt";

    // Método para registrar errores
    public static void logError(String origen, String descripcion, Exception exception) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            // Obtener la fecha y hora actual
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Construir el mensaje de error
            String mensaje = String.format("[%s] Origen: %s | Descripción: %s | Error: %s%n",
                                            fechaHora, origen, descripcion, exception.toString());

            // Escribir en el archivo
            writer.write(mensaje);
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el archivo de logs: " + e.getMessage());
        }
    }
    
}
