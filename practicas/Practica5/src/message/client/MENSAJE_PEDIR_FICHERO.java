package message.client;

import message.Message;

public class MENSAJE_PEDIR_FICHERO extends Message{
	private static final long serialVersionUID = 599189595739864567L;
	private String name;
	
	public MENSAJE_PEDIR_FICHERO(String fichName){
		this.tipo = msgType.MENSAJE_PEDIR_FICHERO;
		this.name = fichName;
	}

	public String getFichero() {
		return name;
	}
}
