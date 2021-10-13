package message.client;

import java.util.List;

import message.Message;
import message.Message.msgType;
import servidor.Usuario;

public class MENSAJE_LISTA_FICHEROS extends Message {
	private static final long serialVersionUID = -1786071513511768420L;
	private List<Usuario> usuarios;

	public MENSAJE_LISTA_FICHEROS(List<Usuario> usuarios) {
		this.usuarios = usuarios;
		this.tipo = msgType.MENSAJE_LISTA_FICHEROS;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

}
