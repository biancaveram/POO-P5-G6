/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package administradores;
import java.util.ArrayList;
import modelo.NotaSeguimiento;
import modelo.LlamadaEmergencia;

/**
 *
 * @author BIANCA VERA
 * Administra la lista de notas de seguimiento del sistema
 * Permite agregar, buscar, listar y obtener notas de seguimiento 
 */
public class AdmiNotas {
    private ArrayList<NotaSeguimiento> notas=new ArrayList<>();
    
    //metodo para agregar una nota a la lista
    public void agregarNota(NotaSeguimiento nota){
        notas.add(nota);
    }
    
    /**
     * Busca una nota por su codigo
     * 
     * @param codigo codigo de la nota que se quiere buscar
     * @return la nota encontrada o null si no existe
     */
    public NotaSeguimiento buscarNota(String codigo){
            for(NotaSeguimiento nota: notas){
                if(nota.getCodigoNota().equals(codigo)){
                    return nota;
                }
            }
            return null;
    }
    
    //metodo para obtener la lista de notas
    public ArrayList<NotaSeguimiento> listaNotas(){
        return notas;
    }
    
    /**
     * Obtiene y almacena las notas de una llamada
     * 
     * @param llamada objeto de tipo LlamadaEmergencia
     * @return la lista de notas ordenada
     */
    public ArrayList<NotaSeguimiento> notaDeLlamada(LlamadaEmergencia llamada){
        ArrayList<NotaSeguimiento> notasLlamada=new ArrayList<>();
        for(NotaSeguimiento nota: notas){
            if(nota.getLlamada()==llamada){
                notasLlamada.add(nota);
            }
        }
        
        //ordenar desde la mas reciente
        for (int a=0; a<notasLlamada.size()-1;a++){
            for(int d=a+1;d<notasLlamada.size();d++){
                if(notasLlamada.get(a).getFechaHora().isBefore(notasLlamada.get(d).getFechaHora())){
                    NotaSeguimiento aux= notasLlamada.get(a);
                    notasLlamada.set(a, notasLlamada.get(d));
                    notasLlamada.set(d, aux);
                }
            }
        }
        return notasLlamada;
    }
    
}
