/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package administradores;
import java.util.ArrayList;
import modelo.Ciudadano;
/**
 *
 * @author BIANCA VERA
 * Administra la lista de ciudadanos del sistema
 * Permite agregar, buscar, listar ciudadanos
 */
public class AdmiCiudadano {
    private ArrayList<Ciudadano> ciudadanos=new ArrayList<>();
    
    /**
     * Agrega un ciudadano a la lista
     * 
     * @param cedula cedula del ciudadano
     * @param nombres nombres del ciudadano
     * @param apellidos apellidos del ciudadano
     * @return el nuevo ciudadano
     */
    public Ciudadano agregarCiudadano(String cedula, String nombres, String apellidos){
        Ciudadano nuevoCiudadano=new Ciudadano(cedula, nombres, apellidos);
        ciudadanos.add(nuevoCiudadano);
        return nuevoCiudadano;
    }
    
    /**
     * Metodo para buscar a un ciudadano por su cedula
     * 
     * @param cedula cedula del ciudadano que se desea buscar
     * @return el ciudadano encontrado o null si no existe
     */
    public Ciudadano buscarCiudadano(String cedula){
        for(Ciudadano c: ciudadanos){
            if(c.getCedula().equals(cedula)){
                return c;
            }
        }
        return null;
    }
    
    //metodo para obtener la lista de ciudadanos
    public ArrayList<Ciudadano> listaCiudadanos(){
        return ciudadanos;
    }
    
}
