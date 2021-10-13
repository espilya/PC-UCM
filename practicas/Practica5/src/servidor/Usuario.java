package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable{
	private static final long serialVersionUID = -8547918813046532890L;
	private String clientID;
	private String clientIP;
	private List<String> listaFicheros;
	private ObjectInputStream din;
	private ObjectOutputStream dout;

	Usuario( String clientID, String clientIP,  List<String> listaFicheros){
		this.clientID = clientID;
		this.clientIP = clientIP;
		this.listaFicheros = listaFicheros;
//		 din = new ObjectInputStream(s.getInputStream());
//		 dout = new ObjectOutputStream(s.getOutputStream());
	}
	
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public List<String> getListaFicheros() {
		return listaFicheros;
	}
	public void setListaFicheros(List<String> listaFicheros) {
		this.listaFicheros = listaFicheros;
	}
	
	public String toString() {
	    return "Id: " +clientID + ", Ip: " + clientIP + ", Ficheros: " + listaFicheros;
	}

}
