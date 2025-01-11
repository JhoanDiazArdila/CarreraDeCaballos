/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.carreraburros.Vista;

/**
 *
 * @author jhoan
 */

import com.mycompany.carreraburros.Controlador.ControlApuesta;
import com.mycompany.carreraburros.Controlador.ControlBurro;
import com.mycompany.carreraburros.Controlador.ControlBurroCompetencia;
import com.mycompany.carreraburros.Controlador.ControlCompetencia;
import com.mycompany.carreraburros.Controlador.ControlDueño;
import com.mycompany.carreraburros.Controlador.ControlParticipante;
import com.mycompany.carreraburros.Modelo.Clases.Burro;
import com.mycompany.carreraburros.Modelo.Clases.BurroCompetencia;
import com.mycompany.carreraburros.Modelo.Clases.Competencia;
import com.mycompany.carreraburros.Modelo.Clases.Dueño;
import com.mycompany.carreraburros.Modelo.Clases.Participante;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;



public class CarreraBurros {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws SQLException { 
        System.out.println("\n~~~ Menu Administrador ~~~"); 
        int opcion = -1; 
        do {
            try {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("1. Manejar Dueños");
                System.out.println("2. Manejar Burros");
                System.out.println("3. Manejar Competencias");
                System.out.println("4. Manejar Paricipantes");
                System.out.println("5. Manejar Apuestas");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1 -> tablaDueños();
                    case 2 -> tablaBurros();
                    case 3 -> tablaCompetencias();
                    case 4 -> tablaParticipantes();
                    case 5 -> tablaApuestas();
                    case 0 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción no válida, intenta de nuevo.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.nextLine(); 
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }
      
      
    public static void tablaDueños() throws SQLException {

        System.out.println("\n~~~ Manejo de Datos Dueños ~~~");
        int opcion = -1;
        do {
            System.out.println("1. Crear Dueño");
            System.out.println("2. Actualizar telefono Dueño");
            System.out.println("3. Eliminar Dueño");
            System.out.println("4. Mostrar Lista de Dueños");
            System.out.println("5. Mostrar Lista de Burros segun dueño");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        System.out.print("Nombre Completo: ");
                        String name = scanner.nextLine();
                        System.out.print("Teléfono: ");
                        String phone = scanner.nextLine();
                        ControlDueño.createDueño(identification, name, phone);
                    }
                    case 2 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        System.out.print("Nuevo Teléfono: ");
                        String phone = scanner.nextLine();
                        ControlDueño.updateDueño(identification, phone);
                    }
                    case 3 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        ControlDueño.deleteDueño(identification);
                    }
                    case 4 ->
                        ControlDueño.getDueñoList();
                    case 5 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        ControlBurro.listaBurrosPorDueño(identification);
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }
    
    
    public static void tablaBurros() throws SQLException {

        System.out.println("\n~~~ Manejo de Datos Burros ~~~");
        int opcion = -1;
        do {
            System.out.println("1. Crear Burro");
            System.out.println("2. Actualizar Dueño del burro");
            System.out.println("3. Eliminar Burro");
            System.out.println("4. Mostrar Lista de Burros");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        System.out.print("Nombre Completo: ");
                        String name = scanner.nextLine();
                        System.out.print("Edad: ");
                        int age = scanner.nextInt();
                        System.out.print("Raza: ");
                        String raza = scanner.nextLine();
                        Dueño dueño = ControlDueño.getDueño(identification);
                        ControlBurro.createBurro(dueño,name, age, raza);
                    }
                    case 2 -> {
                        System.out.print("Identificación Burro: ");
                        int identificationBurro = scanner.nextInt();
                        System.out.print("Identificación nuevo dueño: ");
                        String identification = scanner.nextLine();
                        Dueño dueño = ControlDueño.getDueño(identification);
                        ControlBurro.cambiarDueño(identificationBurro, dueño);
                    }
                    case 3 -> {
                        System.out.print("Identificación Burro: ");
                        int identification = scanner.nextInt();
                        ControlBurro.deleteBurro(identification);
                    }
                    case 4 ->
                        ControlBurro.showBurroList();
                    
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    
    public static void tablaCompetencias() throws SQLException {

        System.out.println("\n~~~ Manejo de Datos Competencia ~~~");
        int opcion = -1;
        do {
            System.out.println("1. Crear Competencia");
            System.out.println("2. Actualizar fecha de Competencia");
            System.out.println("3. Eliminar Competencia");
            System.out.println("4. Mostrar Lista de Competencias");
            System.out.println("5. Finalizar Competencia");
            System.out.println("6. Inscribir Burro en Competencia");
            System.out.println("7. Eliminar Burro de Competencia");
            System.out.println("8. Mostrar Burros de Competencia");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1 -> {
                        LocalDate currentDay = LocalDate.now();
                        System.out.print("Nombre Completo: ");
                        String name = scanner.nextLine();
                        System.out.print("Dia de Inicio: ");
                        int dia = scanner.nextInt();
                        System.out.print("Mes de Inicio: ");
                        int mes = scanner.nextInt();
                        System.out.print("Año de Inicio: ");
                        int año = scanner.nextInt();
                        
                        LocalDate fecha = LocalDate.of(año,mes,dia);
                        
                        if(fecha.isBefore(currentDay)){
                            System.out.println("La fecha ingresada es anterior a la fecha actual."
                                    + "\nVuelva a intentar");
                            return;
                        }                         
                        System.out.print("Lugar: ");
                        String lugar = scanner.nextLine();

                        ControlCompetencia.createCompetencia(name, fecha, lugar);
                        
                    }
                    case 2 -> {
                        LocalDate currentDay = LocalDate.now();
                        System.out.print("Id de Competencia: ");
                        int idCompetencia = scanner.nextInt();
                        
                        System.out.print("Nuevo Dia de Inicio: ");
                        int dia = scanner.nextInt();
                        System.out.print("Nuevo Mes de Inicio: ");
                        int mes = scanner.nextInt();
                        System.out.print("Nuevo Año de Inicio: ");
                        int año = scanner.nextInt();
                        
                        LocalDate fecha = LocalDate.of(año,mes,dia);
                        
                        if(fecha.isBefore(currentDay)){
                            System.out.println("La fecha ingresada es anterior a la fecha actual."
                                    + "\nVuelva a intentar");
                            return;
                        }
                        ControlCompetencia.updateCompetencia(idCompetencia, fecha);
                        
                    }
                    case 3 -> {
                        System.out.print("Id de Competencia: ");
                        int idCompetencia = scanner.nextInt();
                        
                        ControlCompetencia.deleteCompetencia(idCompetencia);
                        
                    }
                    case 4 -> { //Lista competencia
                        
                        System.out.print("\n1. Competencias Finalizadas. "
                                + "\n2. Competencias SIN Finalizar. "
                                + "\n3. Todas las competencias");
                        int filtro = scanner.nextInt();
                        
                        ControlCompetencia.showCompetenciaList(filtro);
                        
                    }
                    case 5 ->{ // Finalizar competencia
                        boolean finalizada = true;
                        System.out.print("Id de Competencia: ");
                        int idCompetencia = scanner.nextInt();
                        
                        ControlCompetencia.finalizarCompetencia(idCompetencia, finalizada);
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetencia);
                        
                        System.out.println("Id del burro en 1er Primer lugar");
                        int idBurro1 = scanner.nextInt();
                        Burro burro = ControlBurro.getBurro(idBurro1);
                        BurroCompetencia bc = ControlBurroCompetencia.getBurroCompetencia(burro, competencia);
                        ControlBurroCompetencia.asignarPosicionBurroCompetencia(bc, 1);
                        
                        System.out.println("Id del burro en 2nd Segundo lugar");
                        int idBurro2 = scanner.nextInt();
                        burro = ControlBurro.getBurro(idBurro2);
                        bc = ControlBurroCompetencia.getBurroCompetencia(burro, competencia);
                        ControlBurroCompetencia.asignarPosicionBurroCompetencia(bc, 2);
                        
                        
                        System.out.println("Id del burro en 3er Tercer lugar");
                        int idBurro3 = scanner.nextInt();
                        burro = ControlBurro.getBurro(idBurro3);
                        bc = ControlBurroCompetencia.getBurroCompetencia(burro, competencia);
                        ControlBurroCompetencia.asignarPosicionBurroCompetencia(bc, 3);
                        
                    }
                    case 6 ->{
                        System.out.print("Id de Competencia: ");
                        int idCompetencia = scanner.nextInt();
                        
                        System.out.print("Id del Burro: ");
                        int idBurro = scanner.nextInt();
                        
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetencia);
                        Burro burro = ControlBurro.getBurro(idBurro);
                        
                        ControlBurroCompetencia.añadirBurroCompetencia(burro, competencia);
                        
                    }
                    case 7 ->{
                        System.out.print("Id de Competencia: ");
                        int idCompetencia = scanner.nextInt();
                        
                        System.out.print("Id del Burro: ");
                        int idBurro = scanner.nextInt();
                        
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetencia);
                        Burro burro = ControlBurro.getBurro(idBurro);
                        
                        ControlBurroCompetencia.deleteBurroCompetencia(burro, competencia);
                        
                    }
                    case 8 ->{
                        System.out.print("Id de Competencia: ");
                        int idCompetencia = scanner.nextInt();
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetencia);
                        
                        ControlBurroCompetencia.showBurroCompetenciaList(competencia);
                        
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    
    public static void tablaParticipantes() throws SQLException {

        System.out.println("\n~~~ Manejo de Datos Participantes ~~~");
        int opcion = -1;
        do {
            System.out.println("1. Crear Participante");
            System.out.println("2. Actualizar Telefono de Participante");
            System.out.println("3. Eliminar Participante");
            System.out.println("4. Mostrar Lista de Participantes");
            System.out.println("5. Recargar Saldo Participante");
            System.out.println("6. Mostrar Informacion de Participante");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        System.out.print("Nombre Completo: ");
                        String name = scanner.nextLine();
                        System.out.print("Teléfono: ");
                        String phone = scanner.nextLine();
                        System.out.print("Saldo para apuestas: ");
                        double saldo = scanner.nextDouble();
                        
                        if (saldo > 0){
                            ControlParticipante.createParticipante(saldo,
                                identification, name, phone);
                        } else{
                            System.out.println("El valor de recarga no es valido");
                            System.out.println("Intente Nuevamente");
                        }
                        
                    }
                    case 2 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        System.out.print("Nuevo Teléfono: ");
                        String phone = scanner.nextLine();
                        
                        ControlParticipante.updateParticipante(identification, phone);
                        
                    }
                    case 3 -> {
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        ControlParticipante.deleteParticipante(identification);
                        
                    }
                    case 4 -> { 
                        ControlParticipante.showParticipanteList();
                        
                    }
                    case 5 ->{ // Recargar Saldo
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        System.out.print("Saldo para Recargar: ");
                        double saldo = scanner.nextDouble();
                        if (saldo > 0){
                            ControlParticipante.agregarSaldo(identification, saldo);
                        } else{
                            System.out.println("El valor de recarga no es valido");
                        }
                        
                    }
                    case 6 ->{
                        System.out.print("Identificación: ");
                        String identification = scanner.nextLine();
                        ControlParticipante.showParticipante(identification);
                        
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }
    
    
    public static void tablaApuestas() throws SQLException {

        System.out.println("~~~ Manejo de Datos Apuestas ~~~");
        int opcion = -1;
        do {
            System.out.println("1. Crear Apuesta");
            System.out.println("2. Actualizar monto de Apuesta");
            System.out.println("3. Eliminar Apuesta");
            System.out.println("4. Mostrar Lista de Apuestas");
            System.out.println("5. Mostrar Informacion de Apuesta");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Id de Competencia: ");
                        int idCompetencia = scanner.nextInt();
                        
                        System.out.print("Id del Burro: ");
                        int idBurro = scanner.nextInt();
                        
                        System.out.print("Id del Participante: ");
                        String cedula = scanner.nextLine();
                        
                        System.out.print("Monto a Apostar: ");
                        double monto = scanner.nextDouble();
                        
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetencia);
                        Burro burro = ControlBurro.getBurro(idBurro);
                        
                        Participante participante = ControlParticipante.getParticipante(cedula);
                        BurroCompetencia brc = ControlBurroCompetencia.getBurroCompetencia(burro, competencia);
                        
                        if (monto > 0){
                            ControlApuesta.crearApuesta(brc, participante, monto);
                        } else{
                            System.out.println("El valor de recarga no es valido");
                        }
                        
                        ControlApuesta.crearApuesta(brc, participante, monto);
                        
                    }
                    case 2 -> {
                        System.out.print("Id de Apuesta: ");
                        int idApuesta = scanner.nextInt();
                        
                        System.out.print("Nuevo Monto a Apostar: ");
                        double monto = scanner.nextDouble();
                        
                        
                        if (monto > 0){
                            ControlApuesta.actualizarApuesta(idApuesta, monto);
                        } else{
                            System.out.println("El valor de la apuesta no es valido");
                        }
                        
                    }
                    case 3 -> {
                        System.out.print("Id de Apuesta: ");
                        int idApuesta = scanner.nextInt();
                        
                        ControlApuesta.borrarApuesta(idApuesta);
                        
                    }
                    case 4 -> { //Lista Apuestas General
                        System.out.println("1. Ver apuestas de Competencia ");
                        System.out.println("2. Ver apuestas de Participante ");
                        System.out.println("3. Ver apuestas al Burro en Competencia ");
                        System.out.println("4. Ver Todas las apuestas");
                        System.out.println("0. Salir ");
                        int opc = scanner.nextInt();
                        
                        int idCompetencia;
                        String cedula;
                        int idBurro;
                                
                        switch (opc) {
                            case 1:
                                System.out.print("Id de Competencia: ");
                                idCompetencia = scanner.nextInt();
                                
                                ControlApuesta.verListaApuestasByCompetencia(idCompetencia);
                                break;
                                
                            case 2:
                                scanner.nextLine();
                                System.out.print("Id del Participante: ");
                                cedula = scanner.nextLine();
                                
                                ControlApuesta.verListaApuestasByParticipante(cedula);
                                break;
                                
                            case 3:
                                System.out.print("Id de Competencia: ");
                                idCompetencia = scanner.nextInt();
                                
                                System.out.print("Id del Burro: ");
                                idBurro = scanner.nextInt();
                                
                                ControlApuesta.verListaApuestasByBurro(idCompetencia, idBurro);
                                break;
                                
                            case 4:
                                ControlApuesta.verListaApuestas();
                                break;
                            case 0 :
                                System.out.println("Volviendo al Menú Principal...");
                                return;
                            default:
                                System.out.println("Opcion no valida");
                                break;
                        }

                    }
                    case 5 ->{ // Ver apuesta
                        System.out.print("Id de Apuesta: ");
                        int idApuesta = scanner.nextInt();
                        
                        ControlApuesta.showApuesta(idApuesta);
                        
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }
    
}
