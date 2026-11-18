/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author NATHALIA
 */


public class Ciudadano {

    // Atributos 
    private String cedula;
    private String nombres;
    private String apellidos;

    // Constructor
    public Ciudadano(String cedula, String nombres, String apellidos) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    // Obtiene la cédula
    public String getCedula() {
        return cedula;
    }

    // Obtiene los nombres
    public String getNombres() {
        return nombres;
    }

    // Modifica los nombres
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    // Obtiene los apellidos
    public String getApellidos() {
        return apellidos;
    }

    // Modifica los apellidos
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // Obtiene el nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}