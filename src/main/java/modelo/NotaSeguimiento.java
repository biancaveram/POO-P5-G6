/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


import java.time.LocalDateTime;

/**
 * Representa una nota de seguimiento de una llamada.
 * @author Nathalia
 */





public class NotaSeguimiento {

    /** Contador para generar los códigos de las notas. */
    private static int contador = 0;

    /** Código de la nota. */
    private final String codigoNota;

    /** Fecha y hora en que se creó la nota. */
    private final LocalDateTime fechaHora;

    /** Texto de la nota. */
    private String nota;

    /** Llamada a la que pertenece la nota. */
    private final LlamadaEmergencia llamada;

    /** Operador que registró la nota. */
    private final Operador operador;

    /**
     * Crea una nota y genera automáticamente su código y fecha.
     * @param nota texto que se va a registrar.
     * @param llamada llamada relacionada.
     * @param operador operador que registra la nota.
     */
   
    public NotaSeguimiento(String nota, LlamadaEmergencia llamada, Operador operador) {
        contador++;
        this.codigoNota = String.format("NT-%03d", contador);
        this.fechaHora = LocalDateTime.now();
        this.nota = nota;
        this.llamada = llamada;
        this.operador = operador;
    }

    /** Devuelve el código de la nota.
     * @return  */
    public String getCodigoNota() {
        return codigoNota;
    }

    /** Devuelve la fecha y hora de creación.
     * @return  */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /** Devuelve el texto de la nota.
     * @return  */
    public String getNota() {
        return nota;
    }

    /** Modifica el texto de la nota.
     * @param nota nuevo texto.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /** Devuelve la llamada relacionada.
     * @return  */
    public LlamadaEmergencia getLlamada() {
        return llamada;
    }

    /** Devuelve el operador que registró la nota.
     * @return  */
    public Operador getOperador() {
        return operador;
    }
}