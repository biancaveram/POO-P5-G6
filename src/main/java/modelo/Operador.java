/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Representa a un operador del sistema ECU 911.
 * Cada operador tiene un código generado automáticamente,
 * nombres, apellidos, turno y correo electrónico.
 *
 * @author Nathalia
 */



public class Operador {

    /** Contador estático para generar códigos consecutivos. */
    private static int contador = 0;

    /** Código único del operador. */
    private String codigoOp;

    /** Nombres del operador. */
    private String nombres;

    /** Apellidos del operador. */
    private String apellidos;

    /** Turno asignado al operador. */
    private Turno turno;

    /** Correo electrónico del operador. */
    private String email;

    /**
     * Crea un operador y genera automáticamente su código.
     *
     * @param nombres nombres del operador.
     * @param apellidos apellidos del operador.
     * @param turno turno asignado.
     * @param email correo electrónico, puede ser nulo o vacío.
     */
    public Operador(String nombres, String apellidos,
                    Turno turno, String email) {

        contador++;
        this.codigoOp = String.format("OP-%02d", contador);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.turno = turno;
        this.email = (email == null) ? "" : email.trim();
    }

    /**
     * Obtiene el código del operador.
     *
     * @return código del operador.
     */
    public String getCodigoOp() {
        return codigoOp;
    }

    /**
     * Obtiene los nombres del operador.
     *
     * @return nombres del operador.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Modifica los nombres del operador.
     *
     * @param nombres nuevos nombres.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos del operador.
     *
     * @return apellidos del operador.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Modifica los apellidos del operador.
     *
     * @param apellidos nuevos apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el nombre completo del operador.
     *
     * @return nombres y apellidos del operador.
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    /**
     * Obtiene el turno del operador.
     *
     * @return turno asignado.
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * Modifica el turno del operador.
     *
     * @param turno nuevo turno.
     */
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    /**
     * Obtiene el correo electrónico.
     *
     * @return correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica el correo electrónico.
     *
     * @param email nuevo correo electrónico.
     */
    public void setEmail(String email) {
        this.email = (email == null) ? "" : email.trim();
    }
}
