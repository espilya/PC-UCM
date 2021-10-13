package message.server;

import java.util.List;

import message.Message;
import message.Message.msgType;
import servidor.Usuario;

public class MENSAJE_CONFIRMACION_LISTA_FICHEROS extends Message{
	private static final long serialVersionUID = 8660446819442219668L;
	
	private List<Usuario> usuarios;

	public MENSAJE_CONFIRMACION_LISTA_FICHEROS(List<Usuario> usuarios) {
		this.usuarios = usuarios;
		this.tipo = msgType.MENSAJE_CONFIRMACION_LISTA_FICHEROS;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

}
