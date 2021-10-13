package message.p2p;

import message.Message;

public class MENSAJE_FICHERO extends Message {
	private static final long serialVersionUID = 316533022677813708L;
	private String fichero;

	public MENSAJE_FICHERO(String fichero) {
		this.tipo = msgType.MENSAJE_FICHERO;
		this.fichero = fichero;
	}

	public String getFichero() {
		return fichero;
	}

}
