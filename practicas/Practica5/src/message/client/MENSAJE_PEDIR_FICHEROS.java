package message.client;

import message.Message;
import message.Message.msgType;

public class MENSAJE_PEDIR_FICHEROS extends Message{
	private static final long serialVersionUID = 599189595739864567L;
	
	public MENSAJE_PEDIR_FICHEROS(){
		this.tipo = msgType.MENSAJE_PEDIR_FICHEROS;
	}
}
