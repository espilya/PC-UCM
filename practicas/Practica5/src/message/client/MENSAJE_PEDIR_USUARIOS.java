package message.client;

import java.util.List;

import message.Message;
import message.Message.msgType;
import servidor.Usuario;

public class MENSAJE_PEDIR_USUARIOS extends Message {
	private static final long serialVersionUID = -1786071513511768420L;

	public MENSAJE_PEDIR_USUARIOS() {
		this.tipo = msgType.MENSAJE_PEDIR_USUARIOS;
	}


}
