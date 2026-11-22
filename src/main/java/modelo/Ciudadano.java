/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

/**
 * Representa los datos de un ciudadano.
 * @author Nathalia
 */


public class Ciudadano {

    /** Cédula del ciudadano. */
    private String cedula;

    /** Nombres del ciudadano. */
    private String nombres;

    /** Apellidos del ciudadano. */
    private String apellidos;

    /**
     * Crea un ciudadano con sus datos personales.
     * @param cedula número de cédula.
     * @param nombres nombres del ciudadano.
     * @param apellidos apellidos del ciudadano.
     */
    public Ciudadano(String cedula, String nombres, String apellidos) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    /** Devuelve la cédula. */
    public String getCedula() {
        return cedula;
    }

    /** Devuelve los nombres. */
    public String getNombres() {
        return nombres;
    }

    /** Modifica los nombres.
     * @param nombres nuevos nombres.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /** Devuelve los apellidos. */
    public String getApellidos() {
        return apellidos;
    }

    /** Modifica los apellidos.
     * @param apellidos nuevos apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /** Devuelve los nombres y apellidos juntos. */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}