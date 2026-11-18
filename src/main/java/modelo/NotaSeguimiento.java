/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author NATHALIA
 */



public class NotaSeguimiento {

    // Contador de notas
    private static int contador;

    // Atributos de la nota
    private String codigoNota;
    private String fechaHora;
    private String nota;

    // Constructor
    public NotaSeguimiento(String codigoNota, String fechaHora, String nota) {
        this.codigoNota = codigoNota;
        this.fechaHora = fechaHora;
        this.nota = nota;

        contador++;
    }

    // Obtiene el código de la nota
    public String getCodigoNota() {
        return codigoNota;
    }

    // Obtiene la fecha y hora
    public String getFechaHora() {
        return fechaHora;
    }

    // Obtiene el contenido
    public String getNota() {
        return nota;
    }

    // Modifica el contenido
    public void setNota(String nota) {
        this.nota = nota;
    }
}
