package message.server;

import message.Message;
import message.Message.msgType;

public class MENSAJE_CONFIRMACION_CERRAR_CONEXION extends Message{
	private static final long serialVersionUID = 9134463984741230556L;
	
	public MENSAJE_CONFIRMACION_CERRAR_CONEXION(){
		this.tipo = msgType.MENSAJE_CONFIRMACION_CERRAR_CONEXION;
		
	}
}
