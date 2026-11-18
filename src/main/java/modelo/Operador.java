/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author NATHALIA
 */


public class Operador {

    // Contador de operadores
    private static int contador;

    // Atributos del operador
    private String codigoOp;
    private String nombres;
    private String apellidos;
    private Turno turno;
    private String email;

    // Constructor
    public Operador(String codigoOp, String nombres, String apellidos,
                    Turno turno, String email) {

        this.codigoOp = codigoOp;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.turno = turno;
        this.email = email;

        contador++;
    }

    // Obtiene el código
    public String getCodigoOp() {
        return codigoOp;
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

    // Obtiene el turno
    public Turno getTurno() {
        return turno;
    }

    // Modifica el turno
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    // Obtiene el correo
    public String getEmail() {
        return email;
    }

    // Modifica el correo
    public void setEmail(String email) {
        this.email = email;
    }
}