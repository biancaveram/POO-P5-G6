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
 */
public class AdmiLlamadasE {
    private ArrayList<LlamadaEmergencia> llamadas=new ArrayList<>();
    
    //metodo para registrar llamadas
    public void registrarLlamada(LlamadaEmergencia llamada){
        llamadas.add(llamada);
    }
    
    //metodo para buscar una llamada por su codigo
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
    
    //metodo para actualizar el estado de una llamada
    public boolean actualizarEstadoG(String codigo){
        LlamadaEmergencia llamada= buscarLlamada(codigo);
        if (llamada==null || llamada.getEstado()==Estado.ATENDIDA){
            return false;
        }
        llamada.actualizarEstado();
        return true;
    }
    
    //metodo para anular una llamada
    public boolean anularLlamada(String codigo){
        LlamadaEmergencia llamada= buscarLlamada(codigo);
        if(llamada==null || !llamada.puedeAnularse()){
            return false;
        }
        llamadas.remove(llamada);
        return true;
    }
    
    //metodo para comprobar si un operador tiene llamadas registradas
    public boolean tieneLlamadasRegistradas(Operador operador){
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getOperador()==operador){
                return true;
            }
        }
        return false;
    }
    
    //metodo para obtener el historial de llamadas de un ciudadano
    public ArrayList<LlamadaEmergencia> historialLlamadas(Ciudadano ciudadano){
        ArrayList<LlamadaEmergencia> historial= new ArrayList<>();
        
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getCiudadano()==ciudadano){
                historial.add(Ll);
            }
        }
        return historial;
    }
    
    //metodo para contar las llamadas atendidas por operador
    public int contadorLlamadasAtendidas(Operador operador){
        int contador=0;
        for(LlamadaEmergencia Ll: llamadas){
            if(Ll.getOperador()==operador && Ll.getEstado()==Estado.ATENDIDA){
                contador++;
            } 
        }
        return contador;
    }
    
    //metodo para contar el total de llamadas de un operador
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
