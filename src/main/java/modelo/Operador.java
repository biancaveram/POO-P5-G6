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
    private static int contador = 0;

    // Atributos 
    private String codigoOp;
    private String nombres;
    private String apellidos;
    private Turno turno;
    private String email;

    // Constructor
    public Operador(String nombres, String apellidos,
                    Turno turno, String email) {

        contador++;
        this.codigoOp = String.format("OP-%02d", contador);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.turno = turno;
        this.email = (email == null) ? "" : email.trim();
    }

    public String getCodigoOp() {
        return codigoOp;
    }
    
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = (email == null) ? "" : email.trim();
    }
}
