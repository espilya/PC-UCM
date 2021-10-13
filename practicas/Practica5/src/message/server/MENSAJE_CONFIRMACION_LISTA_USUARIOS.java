package message.server;

import java.util.List;

import message.Message;
import message.Message.msgType;
import servidor.Usuario;

public class MENSAJE_CONFIRMACION_LISTA_USUARIOS extends Message {
	private static final long serialVersionUID = 8660446819442219668L;
	private List<String> usuarios;

	public MENSAJE_CONFIRMACION_LISTA_USUARIOS(List<String> usuarios) {
		this.tipo = msgType.MENSAJE_CONFIRMACION_LISTA_USUARIOS;
		this.usuarios = usuarios;
	}

	public List<String> getUsuarios() {
		return usuarios;
	}

}
