package message;

import java.util.List;

import servidor.Usuario;

public class MENSAJE_LISTA_USARIOS extends Message {
	private static final long serialVersionUID = -1786071513511768420L;
	private List<Usuario> usuarios;

	public MENSAJE_LISTA_USARIOS(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

}
