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
import java.time.LocalDate;

public class LlamadaEmergencia {

    // Contador de llamadas
    private static int contador = 0;

    // Atributos de la llamada
    private String codigoLlamada;
    private LocalDateTime fechaHora;
    private TipoEmergencia tipoEmergencia;
    private String ciudad;
    private String barrio;
    private Estado estado;
    private String detalle;

    // Relaciones
    private Ciudadano ciudadano;
    private Operador operador;

    // Constructor
    public LlamadaEmergencia(TipoEmergencia tipoEmergencia,
                             String ciudad, String barrio,
                             String detalle, Ciudadano ciudadano,
                             Operador operador) {

        contador++;
        this.codigoLlamada = String.format("LL-%03d", contador);

        this.fechaHora = LocalDateTime.now();
        this.tipoEmergencia = tipoEmergencia;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.estado = Estado.REGISTRADA;
        this.detalle = detalle;
        this.ciudadano = ciudadano;
        this.operador = operador;
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
    public boolean puedeAnularse() {
        return estado == Estado.REGISTRADA
                && fechaHora.toLocalDate().equals(LocalDate.now());
    }

    // Obtiene el código
    public String getCodigoLlamada() {
        return codigoLlamada;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public TipoEmergencia getTipoEmergencia() {
        return tipoEmergencia;
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

    // Obtiene el estado
    public Estado getEstado() {
        return estado;
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

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    // Obtiene el operador
    public Operador getOperador() {
        return operador;
    }
}
