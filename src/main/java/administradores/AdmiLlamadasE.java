/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package administradores;
import java.util.ArrayList;
import modelo.LlamadaEmergencia;
import modelo.Estado;
import modelo.Operador;
import modelo.Ciudadano;

/**
 *
 * @author BIANCA VERA 
 * Administra la lista de llamadas de emergencia del sistema
 * Permite registrar, buscar, listar, actualizar, anular llamadas
 * Permite comprobar las llamadas registradas, llamadas atendidas y llamadas totales por operador
 * Permite obtener el historial de llamadas asociadas a un ciudadano
 */
public class AdmiLlamadasE {
    private ArrayList<LlamadaEmergencia> llamadas=new ArrayList<>();
    
    //metodo para registrar llamadas a la lista
    public void registrarLlamada(LlamadaEmergencia llamada){
        llamadas.add(llamada);
    }
    
    /**
     * Busca una llamada por su codigo
     * 
     * @param codigo codigo de la llamada que se quiere buscar
     * @return la llamada encontrada o null en caso de no existir
     */
    public LlamadaEmergencia buscarLlamada(String codigo){
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getCodigoLlamada().equals(codigo)){
                return Ll;
            }
        }
        return null;
    }
    
    //metodo para obtener la lista de llamadas
    public ArrayList<LlamadaEmergencia> listaLlamadas(){
        return llamadas;
    }
    
    /**
     * Metodo para actualizar el estado de una llamada
     * 
     * @param codigo codigo de la llamada que se quiere actualizar
     * @return true si se actualizo, false si no cumple las condiciones
     */
    public boolean actualizarEstadoG(String codigo){
        LlamadaEmergencia llamada= buscarLlamada(codigo);
        if (llamada==null || llamada.getEstado()==Estado.ATENDIDA){
            return false;
        }
        llamada.actualizarEstado();
        return true;
    }
    
    /**
     * Metodo para anular una llamada
     * 
     * @param codigo codigo de la llamada que se desea anular
     * @return true si se elimino la llamada, false si no cumple las condiciones
     */
    public boolean anularLlamada(String codigo){
        LlamadaEmergencia llamada= buscarLlamada(codigo);
        if(llamada==null || !llamada.puedeAnularse()){
            return false;
        }
        llamadas.remove(llamada);
        return true;
    }
    
    /**
     * Metodo para comprobar si un operador tiene llamadas registradas
     * 
     * @param operador objeto operador del cual se desea conocer si registra llamadas 
     * @return true si registra llamadas, false si no tiene llamadas registradas
     */
    public boolean tieneLlamadasRegistradas(Operador operador){
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getOperador()==operador){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metodo para obtener el historial de llamadas de un ciudadano
     * 
     * @param ciudadano objeto ciudadano del cual se desea conocer el historial
     * @return el historial de llamadas del ciudadano
     */
    public ArrayList<LlamadaEmergencia> historialLlamadas(Ciudadano ciudadano){
        ArrayList<LlamadaEmergencia> historial= new ArrayList<>();
        
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getCiudadano()==ciudadano){
                historial.add(Ll);
            }
        }
        return historial;
    }
    
    /**
     * Metodo para contar las llamadas atendidas por operador
     * 
     * @param operador objeto operador del cual se desea conocer las llamadas atendidas
     * @return la cantidad de llamadas atendidas del operador
     */
    public int contadorLlamadasAtendidas(Operador operador){
        int contador=0;
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getOperador()==operador && Ll.getEstado()==Estado.ATENDIDA){
                contador++;
            } 
        }
        return contador;
    }
    
    /**
     * Metodo para contar el total de llamadas de un operador
     * 
     * @param operador objeto operador del cual se desea conocer el total de llamadas
     * @return el total de llamadas del operador
     */
    public int contadorLlamadasOperador(Operador operador){
        int contador=0;
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getOperador()==operador){
                contador++;
            }
        }
        return contador;
    }
    
}
