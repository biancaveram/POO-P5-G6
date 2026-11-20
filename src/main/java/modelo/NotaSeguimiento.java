/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author NATHALIA
 */


import java.time.LocalDateTime;


public class NotaSeguimiento {

    // Contador para generar códigos secuenciales
    private static int contador = 0;

    // Atributos de la nota
    private String codigoNota;
    private LocalDateTime fechaHora;
    private String nota;

    // Relaciones
    private LlamadaEmergencia llamada;
    private Operador operador;

    // Constructor
    public NotaSeguimiento(String nota,
                           LlamadaEmergencia llamada,
                           Operador operador) {

        contador++;

        this.codigoNota = String.format("NT-%03d", contador);
        this.fechaHora = LocalDateTime.now();
        this.nota = nota;
        this.llamada = llamada;
        this.operador = operador;
    }


    public String getCodigoNota() {
        return codigoNota;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }


    public String getNota() {
        return nota;
    }

    // Modifica el contenido
    public void setNota(String nota) {
        this.nota = nota;
    }

    public LlamadaEmergencia getLlamada() {
        return llamada;
    }

    public Operador getOperador() {
        return operador;
    }
}
