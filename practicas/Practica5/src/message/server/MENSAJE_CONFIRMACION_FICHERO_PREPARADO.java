package message.server;

import message.Message;

public class MENSAJE_CONFIRMACION_FICHERO_PREPARADO extends Message{
	private static final long serialVersionUID = 6496556393859551907L;
	private String ipEmisor;
	private int pEmisor;

	public MENSAJE_CONFIRMACION_FICHERO_PREPARADO(String ipEmisor, int i) {
		this.tipo = msgType.MENSAJE_CONFIRMACION_FICHERO_PREPARADO;
		this.ipEmisor = ipEmisor;
		this.pEmisor = i;
	}


	public String getIpEmisor() {
		return ipEmisor;
	}

	public int getpEmisor() {
		return pEmisor;
	}
}
