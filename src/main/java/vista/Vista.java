/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Henry De la Torre
 * Vista de consola para el Sistema de Registro de Llamadas ECU 911.
 */
public class Vista {

    private final Scanner scanner;
    private final DateTimeFormatter formatoFechaHora;

    /**
     * #Inicializa la vista de consola con su lector de entrada y formato de fecha/hora.
     */
    public Vista() {
        this.scanner = new Scanner(System.in);
        this.formatoFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }
    
    public void mostrarEncabezado(String rutaNavegacion) {
        System.out.println("\n==============================================================");
        System.out.println("SISTEMA DE REGISTRO DE LLAMADAS ECU 911 (Version 1.0)");
        System.out.println(rutaNavegacion);
        System.out.println("==============================================================");
    }

    public int mostrarMenuPrincipal() {
        mostrarEncabezado("[Menu Principal]");
        System.out.println("[1] Administrar llamadas de emergencia");
        System.out.println("[2] Administrar operadores");
        System.out.println("[3] Administrar ciudadanos");
        System.out.println("[4] Administrar notas de seguimiento");
        System.out.println("[5] Salir");
        System.out.println("Fecha y hora actuales del sistema: " + LocalDateTime.now().format(formatoFechaHora));
        return leerEnteroEnRango("> Seleccione una opcion (1-5): ", 1, 5);
    }

    /**
     * Muestra el submenu de llamadas de emergencia.
     */
    public int mostrarMenuLlamadas() {
        mostrarEncabezado("[Menu Principal > 1. Llamadas de emergencia]");
        System.out.println("[1] Lista de llamadas registradas");
        System.out.println("[2] Registrar llamada");
        System.out.println("[3] Actualizar estado de una llamada");
        System.out.println("[4] Anular llamada");
        System.out.println("[5] Regresar al Menu Principal");
        return leerEnteroEnRango("> Seleccione una opcion (1-5): ", 1, 5);
    }

    /**
     * Muestra el submenu de administracion de operadores.
     */
    public int mostrarMenuOperadores() {
        System.out.println("\n¿Que accion desea realizar?");
        System.out.println("[1] Agregar un nuevo operador");
        System.out.println("[2] Eliminar un operador existente");
        System.out.println("[3] Regresar al Menu Principal");
        return leerEnteroEnRango("> Seleccione una opcion (1-3): ", 1, 3);
    }

    /**
     * Muestra el submenu de administracion de ciudadanos.
     */
    public int mostrarMenuCiudadanos() {
        System.out.println("\n¿Que accion desea realizar?");
        System.out.println("[1] Consultar historial de llamadas de un ciudadano");
        System.out.println("[2] Regresar al Menu Principal");
        return leerEnteroEnRango("> Seleccione una opcion (1-2): ", 1, 2);
    }

    /**
     * Muestra el submenu de notas de seguimiento.
     */
    public int mostrarMenuNotas() {
        System.out.println("\n¿Que accion desea realizar?");
        System.out.println("[1] Anadir una nueva nota");
        System.out.println("[2] Regresar al Menu Principal");
        return leerEnteroEnRango("> Seleccione una opcion (1-2): ", 1, 2);
    }

    /**
     * Lee un entero dentro de un rango inclusivo sin lanzar excepciones por texto invalido.
     *
     * @param prompt mensaje a mostrar
     * @param min valor minimo permitido
     * @param max valor maximo permitido
     * @return entero valido dentro del rango
     */
    public int leerEnteroEnRango(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("¡Error! Ingrese una opcion valida entre " + min + " y " + max + ".");
            } catch (NumberFormatException ex) {
                System.out.println("¡Error! Debe ingresar un numero valido.");
            }
        }
    }

    /**
     * Lee un campo de texto obligatorio (no puede quedar vacio).
     * @param nombreCampo nombre del campo para el mensaje de error
     * @return texto no vacio
     */
    public String leerTextoObligatorio(String prompt, String nombreCampo) {
        while (true) {
            System.out.print(prompt);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("¡Error! El campo '" + nombreCampo + "' es obligatorio y no puede estar vacio.");
        }
    }

    /**
     * Lee un texto opcional (puede quedar vacio)
     * @return texto ingresado (posiblemente vacio)
     */
    public String leerTextoOpcional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Lee y valida una cedula de exactamente 10 digitos numericos sin guiones.
     * @return cedula valida de 10 digitos
     */
    public String leerCedulaValida(String prompt) {
        while (true) {
            System.out.print(prompt);
            String cedula = scanner.nextLine().trim();
            if (cedula.matches("\\d{10}")) {
                return cedula;
            }
            System.out.println("¡Error! La cedula debe contener exactamente 10 digitos numericos sin guiones. Intente nuevamente.");
        }
    }

    /**
     * Solicita confirmacion S/N al usuario
     * @param prompt pregunta de confirmacion
     * @return true si responde S/s, false si responde N/n
     */
    public boolean confirmarSN(String prompt) {
        while (true) {
            System.out.print(prompt + " (S/N): ");
            String resp = scanner.nextLine().trim().toUpperCase();
            if (resp.equals("S")) {
                return true;
            }
            if (resp.equals("N")) {
                return false;
            }
            System.out.println("¡Error! Responda unicamente con 'S' o 'N'.");
        }
    }

    /**
     * Pausa la ejecucion hasta que el usuario presione ENTER.
     * @param mensaje texto de pausa
     */
    public void pausarEnter(String mensaje) {
        System.out.print("\n" + mensaje);
        scanner.nextLine();
    }

    /**
     * Imprime un mensaje en consola.
     * @param mensaje texto a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Cierra el recurso Scanner al finalizar la aplicacion.
     */
    public void cerrar() {
        scanner.close();
    }
}
