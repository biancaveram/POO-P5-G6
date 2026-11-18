/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author NATHALIA
 */


public class LlamadaEmergencia {

    // Contador de llamadas
    private static int contador;

    // Atributos de la llamada
    private String codigoLlamada;
    private String fechaHora;
    private TipoEmergencia tipoEmergencia;
    private String ciudad;
    private String barrio;
    private Estado estado;
    private String detalle;

    // Constructor
    public LlamadaEmergencia(String codigoLlamada, String fechaHora,
            String datoAdicional, TipoEmergencia tipoEmergencia,
            String ciudad, String barrio, Estado estado, String detalle) {

        this.codigoLlamada = codigoLlamada;
        this.fechaHora = fechaHora;
        this.tipoEmergencia = tipoEmergencia;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.estado = estado;
        this.detalle = detalle;

        contador++;
    }

    // Actualiza el estado de la llamada
    public void actualizarEstado() {
        if (estado == Estado.REGISTRADA) {
            estado = Estado.EN_ATENCION;
        } else if (estado == Estado.EN_ATENCION) {
            estado = Estado.ATENDIDA;
        }
    }

    // Comprueba si la llamada puede anularse
    public boolean anularLlamada() {
        return estado == Estado.REGISTRADA;
    }

    // Obtiene el código
    public String getCodigoLlamada() {
        return codigoLlamada;
    }

    // Obtiene la fecha y hora
    public String getFechaHora() {
        return fechaHora;
    }

    // Obtiene el tipo de emergencia
    public String getTipoEmergencia() {
        return tipoEmergencia.toString();
    }

    // Modifica el tipo de emergencia
    public void setTipoEmergencia(TipoEmergencia tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getEstado() {
        return estado.toString();
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}