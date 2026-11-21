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
 */
public class AdmiNotas {
    private ArrayList<NotaSeguimiento> notas=new ArrayList<>();
    
    //metodo para agregar una nota
    public void agregarNota(NotaSeguimiento nota){
        notas.add(nota);
    }
    
    //metodo para buscar una nota por su codigo
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
    
    //metodo para obtener las notas de una llamada
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
