package message.client;

import java.util.List;

import message.Message;
import message.Message.msgType;

public class MENSAJE_CONEXION extends Message {
	private static final long serialVersionUID = 4020597045041496991L;
	private List<String> listaFicheros;
	private String idCliente;
	private String ipCliente;

	public MENSAJE_CONEXION(String id, String ip, List<String> listaFicheros) {
		super();
		this.listaFicheros = listaFicheros;
		this.idCliente = id;
		this.ipCliente = ip;
		this.tipo = msgType.MENSAJE_CONEXION;
	}

	public List<String> getListaFicheros() {
		return listaFicheros;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public String getIpCliente() {
		return ipCliente;
	}

}
