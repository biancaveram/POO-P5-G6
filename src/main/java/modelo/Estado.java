/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo;

/**
 * Estados por los que puede pasar una llamada de emergencia.
 * @author Nathalia
 */

public enum Estado {

    /** La llamada acaba de ser registrada. */
    REGISTRADA,

    /** La llamada está siendo atendida. */
    EN_ATENCION,

    /** La atención de la llamada ha finalizado. */
    ATENDIDA
}
