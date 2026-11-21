/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package administradores;
import modelo.Operador;
import java.util.ArrayList;
import modelo.Turno;


/**
 *
 * @author BIANCA VERA
 */
public class AdmiOperador {
    private ArrayList<Operador> operadores=new ArrayList<>();
    
    //metodo para agregar operador
    public void agregarOperador(String nombres, String apellidos, Turno turno, String email){
        Operador nuevoOperador=new Operador(nombres, apellidos, turno, email);
        operadores.add(nuevoOperador);
    }
    
    //metodo para buscar un operador por el codigo
    public Operador buscarOperador(String codigo){
        for(Operador op: operadores){
            if(op.getCodigoOp().equals(codigo)){
                return op;
            }
        }
        return null;
    }
    
    //metodo para obtener la lista de operadores
    public ArrayList<Operador> listaOperadores(){
        return operadores;
    }
    
    //metodo para eliminar un operador
    public boolean eliminarOperador(String codigo){
        Operador op=buscarOperador(codigo);
        if(op==null){
            return false;
        }
        operadores.remove(op);
        return true;
    }

}

