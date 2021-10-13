package message.server;

import java.util.List;

import message.Message;

public class MENSAJE_REQUEST_FICHERO  extends Message{
	private static final long serialVersionUID = 8660446819441219668L;
	private String idReceptor;
	private String fichero;

	public MENSAJE_REQUEST_FICHERO(String idReceptor, String fichero) {
		this.tipo = msgType.MENSAJE_REQUEST_FICHERO;
		this.idReceptor = idReceptor;
		this.fichero = fichero;
	}

	public String getIdReceptor() {
		return idReceptor;
	}

	public String getFichero() {
		return fichero;
	}
}
