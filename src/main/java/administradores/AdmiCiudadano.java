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
 */
public class AdmiCiudadano {
    private ArrayList<Ciudadano> ciudadanos=new ArrayList<>();
    
    //metodo para agregar un ciudadano
    public void agregarCiudadano(String cedula, String nombres, String apellidos){
        Ciudadano nuevoCiudadano=new Ciudadano(cedula, nombres, apellidos);
        ciudadanos.add(nuevoCiudadano);
    }
    
    //metodo para buscar a un ciudadano por su cedula
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
