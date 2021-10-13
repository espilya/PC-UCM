package message.client;


import message.Message;
import message.Message.msgType;

public class MENSAJE_CERRAR_CONEXION extends Message{
	private static final long serialVersionUID = -1786071523511768420L;
	
	public MENSAJE_CERRAR_CONEXION(){
		this.tipo = msgType.MENSAJE_CERRAR_CONEXION;
		
	}

}
