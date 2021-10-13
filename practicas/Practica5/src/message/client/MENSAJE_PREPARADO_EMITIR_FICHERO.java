package message.client;

import message.Message;

public class MENSAJE_PREPARADO_EMITIR_FICHERO extends Message {
	private static final long serialVersionUID = -3732844278728172320L;
	private String idReceptor;
	private String ipEmisor;
	private int pEmisor;

	public MENSAJE_PREPARADO_EMITIR_FICHERO(String idReceptor, String ipEmisor, int puertoEmisor) {
		this.tipo = msgType.MENSAJE_PREPARADO_EMITIR_FICHERO;
		this.idReceptor = idReceptor;
		this.ipEmisor = ipEmisor;
		this.pEmisor = puertoEmisor;
	}

	public String getIdReceptor() {
		return idReceptor;
	}

	public String getIpEmisor() {
		return ipEmisor;
	}

	public int getpEmisor() {
		return pEmisor;
	}

}
