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
import com.mycompany.carreraburros.Modelo.Persistencia.LogManager;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class CarreraBurros {

    private static Scanner scanner = new Scanner(System.in);
    
    
    public static String obtenerEntrada(String mensaje, String nombreCampo) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Error: El campo '" + nombreCampo + "' no puede estar vacío. Intenta nuevamente.");
            }
        } while (input.isEmpty());
        return input;
    }

    
    
    public static void main(String[] args) throws SQLException {
        int opcion1 = -1;
        System.out.println("\n~~~ Menu Administrador ~~~");
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

                String opcion = obtenerEntrada("","Opcion Menu");
                opcion1 = Integer.parseInt(opcion);
                switch (opcion1){
                    case 1 ->
                        tablaDueños();
                    case 2 ->
                        tablaBurros();
                    case 3 ->
                        tablaCompetencias();
                    case 4 ->
                        tablaParticipantes();
                    case 5 ->
                        tablaApuestas();
                    case 0 ->
                        System.out.println("Saliendo del programa...");
                    default ->
                        System.out.println("Opción no válida, intenta de nuevo.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                LogManager.logError("CarreraBurros", "Entrada inválida en el menú principal", e);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                LogManager.logError("CarreraBurros", "Error inesperado en el menú principal", e);
            }
        } while (opcion1 != 0);
    }

    
    public static void tablaDueños() throws SQLException {
        int opcion1 = -1;
        System.out.println("\n~~~ Manejo de Datos Dueños ~~~");
        do {
            System.out.println("1. Crear Dueño");
            System.out.println("2. Actualizar telefono Dueño");
            System.out.println("3. Eliminar Dueño");
            System.out.println("4. Mostrar Lista de Dueños");
            System.out.println("5. Mostrar Lista de Burros segun dueño");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                String opcion = obtenerEntrada("","Opcion Sub-menu");
                opcion1 = Integer.parseInt(opcion);
                switch (opcion1){
                    case 1 -> {
                        String identification = obtenerEntrada("Identificación del dueño: ", 
                                "Identificación del dueño");
                        if(ControlDueño.existeDueño(identification)){
                            System.out.println("El Dueño ya fue creado");
                            break;
                        }
                        String name = obtenerEntrada("Nombre Completo: ", "Nombre completo");
                        String phone = obtenerEntrada("Teléfono: ", "Teléfono");
                        
                        ControlDueño.createDueño(identification, name, phone);
                        break;
                    }
                    case 2 -> {
                        String identification = obtenerEntrada("Identificación del dueño: ", 
                                "Identificación del dueño");
                        if(!ControlDueño.existeDueño(identification)){
                            System.out.println("El Dueño no Existe");
                            break;
                        }
                        String phone = obtenerEntrada("Teléfono: ", "Teléfono");
                        ControlDueño.updateDueño(identification, phone);
                        break;
                    }
                    case 3 -> {
                        String identification = obtenerEntrada("Identificación del dueño: ", 
                                "Identificación del dueño");
                        if(!ControlDueño.existeDueño(identification)){
                            System.out.println("El Dueño no Existe");
                            break;
                        }
                        ControlDueño.deleteDueño(identification);
                        break;
                    }
                    case 4 ->{
                        ControlDueño.getDueñoList();
                        break;
                    }
                    case 5 -> {
                        String identification = obtenerEntrada("Identificación del dueño: ", 
                                "Identificación del dueño");
                        if(!ControlDueño.existeDueño(identification)){
                            System.out.println("El Dueño no Existe");
                            break;
                        }
                        ControlBurro.listaBurrosPorDueño(identification);
                        break;
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: La edad debe ser un número válido.");
                LogManager.logError("Tabla Burro", "Entrada inválida", e);
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                LogManager.logError("CarreraBurros", "Entrada inválida en el sub-menú", e);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                LogManager.logError("CarreraBurros", "Error inesperado en el menú", e);
            }
        } while (opcion1 != 0);
    }

    
    public static void tablaBurros() throws SQLException {
        int opcion1 = -1;
        System.out.println("\n~~~ Manejo de Datos Burros ~~~");
        do {
            System.out.println("1. Crear Burro");
            System.out.println("2. Actualizar Dueño del burro");
            System.out.println("3. Eliminar Burro");
            System.out.println("4. Mostrar Lista de Burros");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                String opcion = obtenerEntrada("","Opcion Sub-menu");
                opcion1 = Integer.parseInt(opcion);
                switch (opcion1){
                    case 1 -> {

                        String identification = obtenerEntrada("Identificación del dueño: ", 
                                "Identificación del dueño");
                        if(!ControlDueño.existeDueño(identification)){
                            System.out.println("El Dueño no Existe");
                            break;
                        }
                        String name = obtenerEntrada("Nombre Completo: ", "Nombre completo");
                        
                        String ageInput = obtenerEntrada("Edad: ", "Edad");
                        int age = Integer.parseInt(ageInput); // Convertir edad a número
                        
                        String raza = obtenerEntrada("Raza: ", "Raza");
                        
                        Dueño dueño = ControlDueño.getDueño(identification);
                        ControlBurro.createBurro(dueño, name, age, raza);
                        break;
                    }
                    case 2 -> {

                        String identificationBurro = obtenerEntrada("Identificación Burro: ","Identificación Burro");
                        int identificationBurroInt = Integer.parseInt(identificationBurro);
                        
                        if(!ControlBurro.existeBurro(identificationBurroInt)){
                            System.out.println("El Burro no Existe");
                            break;
                        }
                        
                        String identification = obtenerEntrada("Identificación del dueño: ", 
                                "Identificación del dueño");
                        if(!ControlDueño.existeDueño(identification)){
                            System.out.println("El Dueño no Existe");
                            break;
                        }
                        
                        Dueño dueño = ControlDueño.getDueño(identification);
                        ControlBurro.cambiarDueño(identificationBurroInt, dueño);
                        break;
                        
                    }
                    case 3 -> {
                        String identificationBurro = obtenerEntrada("Identificación Burro: ","Identificación Burro");
                        int identificationBurroInt = Integer.parseInt(identificationBurro);
                        
                        if(!ControlBurro.existeBurro(identificationBurroInt)){
                            System.out.println("El Burro no Existe");
                            break;
                        }
                        ControlBurro.deleteBurro(identificationBurroInt);
                        break;
                    }
                    case 4 ->{
                        ControlBurro.showBurroList();
                        break;
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Error: La edad debe ser un número válido.");
                LogManager.logError("Tabla Burro", "Entrada inválida ", e);
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                LogManager.logError("CarreraBurros", "Entrada inválida en el sub-menú", e);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                LogManager.logError("CarreraBurros", "Error inesperado en el menú", e);
            }
        } while (opcion1 != 0);
    }

    
    public static void tablaCompetencias() throws SQLException {
        int opcion1 = -1;
        System.out.println("\n~~~ Manejo de Datos Competencia ~~~");
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
                String opcion = obtenerEntrada("","Opcion Sub-menu");
                opcion1 = Integer.parseInt(opcion);
                switch (opcion1){
                    case 1 -> {
                        LocalDate currentDay = LocalDate.now();
                        int dia, mes, año;
                        
                        String name = obtenerEntrada("Nombre de Competencia: ", "Nombre de Competencia");
                        
                        while (true) {
                            try {
                                dia = Integer.parseInt(obtenerEntrada("Día de Inicio: ", "Día de inicio"));
                                mes = Integer.parseInt(obtenerEntrada("Mes de Inicio: ", "Mes de inicio"));
                                año = Integer.parseInt(obtenerEntrada("Año de Inicio: ", "Año de inicio"));

                                LocalDate fecha = LocalDate.of(año, mes, dia);

                                if (fecha.isBefore(currentDay)) {
                                    System.out.println("La fecha ingresada es anterior a la fecha actual."
                                            + "\nVuelva a intentar");
                                } else {
                                    String lugar = obtenerEntrada("Lugar: ", "Lugar");
                                    ControlCompetencia.createCompetencia(name, fecha, lugar);
                                    break; // Salir del ciclo si la fecha es válida
                                }
                            } catch (DateTimeException e) {
                                System.out.println("Error: La fecha ingresada no es válida. Intenta nuevamente.");
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Debes ingresar un número válido para el día, mes o año.");
                            }
                        }
                    }
                    case 2 -> {
                        LocalDate currentDay = LocalDate.now();
                        int dia, mes, año;
                        
                        String idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                        int idCompetenciaInt = Integer.parseInt(idCompetencia);
                        if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                            System.out.println("La competencia no Existe");
                            break;
                        }
                        
                        while (true) {
                            try {
                                dia = Integer.parseInt(obtenerEntrada("Nuevo Día de Inicio: ", "Día de inicio"));
                                mes = Integer.parseInt(obtenerEntrada("Nuevo Mes de Inicio: ", "Mes de inicio"));
                                año = Integer.parseInt(obtenerEntrada("Nuevo Año de Inicio: ", "Año de inicio"));

                                LocalDate fecha = LocalDate.of(año, mes, dia);

                                if (fecha.isBefore(currentDay)) {
                                    System.out.println("La fecha ingresada es anterior a la fecha actual."
                                            + "\nVuelva a intentar");
                                } else {
                                    String lugar = obtenerEntrada("Lugar: ", "Lugar");
                                    ControlCompetencia.updateCompetencia(idCompetenciaInt, fecha);
                                    break; // Salir del ciclo si la fecha es válida
                                }
                            } catch (DateTimeException e) {
                                System.out.println("Error: La fecha ingresada no es válida. Intenta nuevamente.");
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Debes ingresar un número válido para el día, mes o año.");
                            }
                        }
                        break;
                    }
                    case 3 -> {
                        String idCompetencia = obtenerEntrada("Id de Competencia: : ","Id de Competencia");
                        int idCompetenciaInt = Integer.parseInt(idCompetencia);
                        if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                            System.out.println("La competencia no Existe");
                            break;
                        }
                        ControlCompetencia.deleteCompetencia(idCompetenciaInt);
                        break;
                    }
                    case 4 -> { //Lista competencia
                        int filtro = Integer.parseInt(obtenerEntrada("\n1. Competencias Finalizadas. "
                                + "\n2. Competencias SIN Finalizar. "
                                + "\n3. Todas las competencias", "Opcion filtrar"));
                        
                        ControlCompetencia.showCompetenciaList(filtro);
                        break;

                    }
                    case 5 -> { // Finalizar competencia
                        boolean finalizada = true;
                        
                        String idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                        int idCompetenciaInt = Integer.parseInt(idCompetencia);
                        
                        if(ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                        
                            if(!ControlCompetencia.validarCompetenciaFinalizada(idCompetenciaInt)){
                                Competencia competencia = ControlCompetencia.getCompetencia(idCompetenciaInt);

                                for(int i = 1; i <= 3; i++){ //Recorre 3 posiciones para asignar puestos

                                    String idBurro = obtenerEntrada("Id del burro en la posicion -> "+ i,"Id del burro");
                                    int idBurroInt = Integer.parseInt(idBurro);

                                    if(ControlBurroCompetencia.existeBurroEnCompetencia(idCompetenciaInt, idBurroInt)){
                                        Burro burro = ControlBurro.getBurro(idBurroInt);
                                        BurroCompetencia bc = ControlBurroCompetencia.getBurroCompetencia(burro, competencia);
                                        ControlBurroCompetencia.asignarPosicionBurroCompetencia(bc, i);
                                        
                                    }else{
                                        System.out.println("Burro no existente en competencia. Ningun cambio se aplico");
                                        break;
                                    }
                                }
                                ControlCompetencia.finalizarCompetencia(idCompetenciaInt, finalizada);
                                break;
                            }else{
                                System.out.println("La competencia fue finalizada con anterioridad");
                                break;
                            }
                        }else{
                            System.out.println("La competencia no Existe");
                            break;
                        }
                    }
                    case 6 -> {
                        String idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                        int idCompetenciaInt = Integer.parseInt(idCompetencia);
                        if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                            System.out.println("La competencia no Existe");
                            break;
                        }
                        
                        String idBurro = obtenerEntrada("Identificación Burro: ","Identificación Burro");
                        int idBurroInt = Integer.parseInt(idBurro);
                        if(!ControlBurro.existeBurro(idBurroInt)){
                            System.out.println("El Burro no Existe");
                            break;
                        }
                        
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetenciaInt);
                        Burro burro = ControlBurro.getBurro(idBurroInt);

                        ControlBurroCompetencia.añadirBurroCompetencia(burro, competencia);
                        break;

                    }
                    case 7 -> {
                        String idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                        int idCompetenciaInt = Integer.parseInt(idCompetencia);
                        if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                            System.out.println("La competencia no Existe");
                            break;
                        }
                        String idBurro = obtenerEntrada("Identificación Burro: ","Identificación Burro");
                        int idBurroInt = Integer.parseInt(idBurro);
                        if(!ControlBurro.existeBurro(idBurroInt)){
                            System.out.println("El Burro no Existe");
                            break;
                        }
                        
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetenciaInt);
                        Burro burro = ControlBurro.getBurro(idBurroInt);

                        ControlBurroCompetencia.deleteBurroCompetencia(burro, competencia);
                        break;
                    }
                    case 8 -> {
                        String idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                        int idCompetenciaInt = Integer.parseInt(idCompetencia);
                        if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                            System.out.println("La competencia no Existe");
                            break;
                        }
                        Competencia competencia = ControlCompetencia.getCompetencia(idCompetenciaInt);

                        ControlBurroCompetencia.showBurroCompetenciaList(competencia);
                        break;
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: La edad debe ser un número válido.");
                LogManager.logError("Tabla Competencia", "Entrada inválida ", e);
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                LogManager.logError("CarreraBurros", "Entrada inválida en el sub-menú", e);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                LogManager.logError("CarreraBurros", "Error inesperado en el menú", e);
            }
        } while (opcion1 != 0);
    }

    
    public static void tablaParticipantes() throws SQLException {
        int opcion1 = -1;
        System.out.println("\n~~~ Manejo de Datos Participantes ~~~");
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
                String opcion = obtenerEntrada("","Opcion Sub-menu");
                opcion1 = Integer.parseInt(opcion);
                switch (opcion1) {
                    case 1 -> {
                        String identification = obtenerEntrada("Identificación del Participante: ", 
                                "Identificación del Participante");
                        if(ControlParticipante.existeParticipante(identification)){
                            System.out.println("El Participante ya fue creado");
                            break;
                        }
                        String name = obtenerEntrada("Nombre Completo: ", "Nombre completo");
                        String phone = obtenerEntrada("Teléfono: ", "Teléfono");
                        double saldo = Double.parseDouble(obtenerEntrada("Saldo para apuestas: ", "Saldo"));

                        if (saldo > 0) {
                            ControlParticipante.createParticipante(saldo,
                                    identification, name, phone);
                            break;
                        } else {
                            System.out.println("El valor de recarga no es valido");
                            System.out.println("Intente Nuevamente");
                        }
                    }
                    case 2 -> {
                        String identification = obtenerEntrada("Identificación del Participante: ", 
                                "Identificación del Participante");
                        if(!ControlParticipante.existeParticipante(identification)){
                            System.out.println("El Participante No existe");
                            break;
                        }
                        String phone = obtenerEntrada("Nuevo Teléfono: ", "Teléfono");

                        ControlParticipante.updateParticipante(identification, phone);
                        break;
                    }
                    case 3 -> {
                        String identification = obtenerEntrada("Identificación del Participante: ", 
                                "Identificación del Participante");
                        if(!ControlParticipante.existeParticipante(identification)){
                            System.out.println("El Participante No existe");
                            break;
                        }
                        ControlParticipante.deleteParticipante(identification);
                        break;
                    }
                    case 4 -> {
                        ControlParticipante.showParticipanteList();
                        break;
                    }
                    case 5 -> { // Recargar Saldo
                        String identification = obtenerEntrada("Identificación del Participante: ", 
                                "Identificación del Participante");
                        if(!ControlParticipante.existeParticipante(identification)){
                            System.out.println("El Participante No existe");
                            break;
                        }
                        double saldo = Double.parseDouble(obtenerEntrada("Saldo para Recargar: ", "Saldo"));
                        if (saldo > 0) {
                            ControlParticipante.agregarSaldo(identification, saldo);
                            break;
                        } else {
                            System.out.println("El valor de recarga no es valido");
                            break;
                        }
                    }
                    case 6 -> {
                        String identification = obtenerEntrada("Identificación del Participante: ", 
                                "Identificación del Participante");
                        if(!ControlParticipante.existeParticipante(identification)){
                            System.out.println("El Participante No existe");
                            break;
                        }
                        ControlParticipante.showParticipante(identification);
                        break;
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: La edad debe ser un número válido.");
                LogManager.logError("Tabla Burro", "Entrada inválida", e);
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                LogManager.logError("CarreraBurros", "Entrada inválida en el sub-menú", e);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                LogManager.logError("CarreraBurros", "Error inesperado en el menú", e);
            }
        } while (opcion1 != 0);
    }

    
    public static void tablaApuestas() throws SQLException {
        int opcion1 = -1;
        System.out.println("~~~ Manejo de Datos Apuestas ~~~");
        do {
            System.out.println("1. Crear Apuesta");
            System.out.println("2. Actualizar monto de Apuesta");
            System.out.println("3. Eliminar Apuesta");
            System.out.println("4. Mostrar Lista de Apuestas");
            System.out.println("5. Mostrar Informacion de Apuesta");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                String opcion = obtenerEntrada("\n","Opcion Sub-menu");
                opcion1 = Integer.parseInt(opcion);
                switch (opcion1) {
                    case 1 -> {
                        String idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                        int idCompetenciaInt = Integer.parseInt(idCompetencia);
                        if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                            System.out.println("La competencia no Existe");
                            break;
                        }

                        String idBurro = obtenerEntrada("Identificación Burro: ","Identificación Burro");
                        int idBurroInt = Integer.parseInt(idBurro);
                        if(!ControlBurro.existeBurro(idBurroInt)){
                            System.out.println("El Burro no Existe");
                            break;
                        }

                        String identification = obtenerEntrada("Identificación del Participante: ", 
                                "Identificación del Participante");
                        if(!ControlParticipante.existeParticipante(identification)){
                            System.out.println("El Participante No existe");
                            break;
                        }
                        
                        double monto = Double.parseDouble(obtenerEntrada("Saldo para apuestas: ", "Saldo"));
                        if (monto > 0) {
                            
                            Competencia competencia = ControlCompetencia.getCompetencia(idCompetenciaInt);
                            Burro burro = ControlBurro.getBurro(idBurroInt);
                            BurroCompetencia brc = ControlBurroCompetencia.getBurroCompetencia(burro, competencia);
                            
                            Participante participante = ControlParticipante.getParticipante(identification);
                            
                            // CREO APUESTA
                            ControlApuesta.crearApuesta(brc, participante, monto);
                            break;
                        } else {
                            System.out.println("El valor de apuesta no es valido");
                            System.out.println("Intente Nuevamente");
                            break;
                        }
                    }
                    case 2 -> {
                        String idApuesta = obtenerEntrada("Id de Apuesta: ","Id de Apuesta");
                        int idApuestaInt = Integer.parseInt(idApuesta);
                        if(!ControlApuesta.existeApuesta(idApuestaInt)){
                            System.out.println("La apuesta no Existe");
                            break;
                        }
                        double monto = Double.parseDouble(obtenerEntrada("Nuevo monto para apuesta: ", "Nuevo Monto"));
                        if (monto > 0) {
                            ControlApuesta.actualizarApuesta(idApuestaInt, monto);
                            break;
                        } else {
                            System.out.println("El valor de la apuesta no es valido");
                            break;
                        }
                    }
                    case 3 -> {
                        String idApuesta = obtenerEntrada("Id de Apuesta: ","Id de Apuesta");
                        int idApuestaInt = Integer.parseInt(idApuesta);
                        if(!ControlApuesta.existeApuesta(idApuestaInt)){
                            System.out.println("La apuesta no Existe");
                            break;
                        }
                        ControlApuesta.borrarApuesta(idApuestaInt);
                        break;
                    }
                    case 4 -> { //Lista Apuestas General
                        System.out.println("1. Ver apuestas de Competencia ");
                        System.out.println("2. Ver apuestas de Participante ");
                        System.out.println("3. Ver apuestas al Burro en Competencia ");
                        System.out.println("4. Ver Todas las apuestas");
                        System.out.println("0. Salir ");
                        String opc = obtenerEntrada("","Opcion Filtro");
                        int opcInt = Integer.parseInt(opc);

                        String idCompetencia, cedula, idBurro;

                        switch (opcInt) {
                            case 1 ->{
                                idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                                int idCompetenciaInt = Integer.parseInt(idCompetencia);
                                if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                                    System.out.println("La competencia no Existe");
                                    break;
                                }
                                ControlApuesta.verListaApuestasByCompetencia(idCompetenciaInt);
                                break;
                            }

                            case 2 ->{
                                cedula = obtenerEntrada("Identificación del Participante: ", 
                                "Identificación del Participante");
                                if(!ControlParticipante.existeParticipante(cedula)){
                                    System.out.println("El Participante No existe");
                                    break;
                                }
                                ControlApuesta.verListaApuestasByParticipante(cedula);
                                break;
                            }

                            case 3 ->{
                                idCompetencia = obtenerEntrada("Id de Competencia: ","Id de Competencia");
                                int idCompetenciaInt = Integer.parseInt(idCompetencia);
                                if(!ControlCompetencia.existeCompetencia(idCompetenciaInt)){
                                    System.out.println("La competencia no Existe");
                                    break;
                                }

                                idBurro = obtenerEntrada("Identificación Burro: ","Identificación Burro");
                                int idBurroInt = Integer.parseInt(idBurro);
                                if(!ControlBurro.existeBurro(idBurroInt)){
                                    System.out.println("El Burro no Existe");
                                    break;
                                }

                                ControlApuesta.verListaApuestasByBurro(idCompetenciaInt, idBurroInt);
                                break;
                            }

                            case 4 ->{
                                ControlApuesta.verListaApuestas();
                                break;
                            }
                            case 0 ->{
                                System.out.println("Volviendo al Menú Principal...");
                                return;
                            }
                            default ->{
                                System.out.println("Opcion no valida");
                                break;
                            }
                        }

                    }
                    case 5 -> { // Ver apuesta
                        String idApuesta = obtenerEntrada("Id de Apuesta: ","Id de Apuesta");
                        int idApuestaInt = Integer.parseInt(idApuesta);

                        ControlApuesta.showApuesta(idApuestaInt);
                    }
                    case 0 ->
                        System.out.println("Volviendo al Menú Principal...");
                    default ->
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                // Capturar errores por entrada de datos no numéricos
                System.out.println("Error: Debes ingresar un número válido.");
                LogManager.logError("CarreraBurros", "Entrada inválida en el sub-menú", e);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
                LogManager.logError("CarreraBurros", "Error inesperado en el menú", e);
            }
        } while (opcion1 != 0);
    }

}
