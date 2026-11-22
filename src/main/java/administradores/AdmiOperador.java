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
 * Administra la lista de operadores del sistema
 * Permite agregar, buscar, listar y eliminar operadores
 */
public class AdmiOperador {
    private ArrayList<Operador> operadores=new ArrayList<>();
    
    /**
     * Agrega un nuevo operador a la lista
     * 
     * @param nombres nombres del operador
     * @param apellidos apellidos del operador
     * @param turno turno del operador
     * @param email email del operador
     * @return  el nuevo operador
     */
    
    public Operador agregarOperador(String nombres, String apellidos, Turno turno, String email){
        Operador nuevoOperador=new Operador(nombres, apellidos, turno, email);
        operadores.add(nuevoOperador);
        return nuevoOperador;
    }
    
    /**
     * Busca un operador por su codigo
     * 
     * @param codigo codigo del operador que se desea buscar
     * @return el operador encontrado o null en caso de no existir
     */
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
    
    /**
     * Elimina un operador de la lista
     * 
     * @param codigo codigo del operador que se eliminara
     * @return true si se elimino el operador, false si no se encontro 
     */
    public boolean eliminarOperador(String codigo){
        Operador op=buscarOperador(codigo);
        if(op==null){
            return false;
        }
        operadores.remove(op);
        return true;
    }

}

