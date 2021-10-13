package utils;
import java.io.Serializable;

public abstract class Fichero implements Serializable{
    private int tipo;
    private String origen;
    private String destino;
    
    Fichero (int tipo){
    	this.tipo = tipo;
    }
    
    public int getTipo() {
    	return tipo;
    }
    public String getOrigen() {
    	return origen;
    }
    public String getDestino() {
    	return destino;
    }
    
//    sererializar
//    desererializar

}
