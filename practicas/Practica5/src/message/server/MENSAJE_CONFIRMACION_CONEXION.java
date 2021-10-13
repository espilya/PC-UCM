package message.server;

import message.Message;
import message.Message.msgType;

public class MENSAJE_CONFIRMACION_CONEXION extends Message{
	private static final long serialVersionUID = 7296260598420028400L;

	public MENSAJE_CONFIRMACION_CONEXION(){
		this.tipo = msgType.MENSAJE_CONFIRMACION_CONEXION;
		
	}
}
