/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;




import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Representa una llamada de emergencia.
 * @author Nathalia
 */

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
    /**
     * Crea una llamada con sus datos y genera automáticamente
     * el código, la fecha y el estado inicial.
     *
     * @param tipoEmergencia tipo de emergencia.
     * @param ciudad ciudad donde ocurre.
     * @param barrio barrio donde ocurre.
     * @param detalle descripción de la emergencia.
     * @param ciudadano ciudadano que realiza la llamada.
     * @param operador operador que atiende la llamada.
     */
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

    /**
     * Cambia el estado de la llamada al siguiente estado.
     */
    public void actualizarEstado() {
        if (estado == Estado.REGISTRADA) {
            estado = Estado.EN_ATENCION;
        } else if (estado == Estado.EN_ATENCION) {
            estado = Estado.ATENDIDA;
        }
    }

    /**
     * Comprueba si la llamada se puede anular.
     *
     * @return true si sigue registrada y fue creada hoy.
     */
    public boolean puedeAnularse() {
        return estado == Estado.REGISTRADA
                && fechaHora.toLocalDate().equals(LocalDate.now());
    }

    /**
     * Devuelve el código de la llamada.
     *
     * @return código de la llamada.
     */
    public String getCodigoLlamada() {
        return codigoLlamada;
    }

    /**
     * Devuelve la fecha y hora de la llamada.
     *
     * @return fecha y hora de registro.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Devuelve el tipo de emergencia.
     *
     * @return tipo de emergencia.
     */
    public TipoEmergencia getTipoEmergencia() {
        return tipoEmergencia;
    }

    /**
     * Cambia el tipo de emergencia.
     *
     * @param tipoEmergencia nuevo tipo de emergencia.
     */
    public void setTipoEmergencia(TipoEmergencia tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
    }

    /**
     * Devuelve la ciudad de la emergencia.
     *
     * @return ciudad.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Devuelve el barrio de la emergencia.
     *
     * @return barrio.
     */
    public String getBarrio() {
        return barrio;
    }
 
    /**
     * Devuelve el estado actual.
     *
     * @return estado de la llamada.
     */
    // Obtiene el estado
    public Estado getEstado() {
        return estado;
    }

    /**
     * Cambia el estado de la llamada.
     *
     * @param estado nuevo estado.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    
    /**
     * Devuelve el detalle de la emergencia.
     *
     * @return detalle de la llamada.
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Modifica el detalle de la emergencia.
     *
     * @param detalle nuevo detalle.
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    /**
     * Devuelve el ciudadano que realizó la llamada.
     *
     * @return ciudadano asociado.
     */
    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    /**
     * Devuelve el operador que atiende la llamada.
     *
     * @return operador asociado.
     */
    public Operador getOperador() {
        return operador;
    }
}
