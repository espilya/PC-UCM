package message;

import java.io.Serializable;

public abstract class Message implements Serializable {
	private static final long serialVersionUID = 5565483917645207777L;

	public enum msgType {
		MENSAJE_CONFIRMACION_CONEXION, MENSAJE_CONFIRMACION_LISTA_USUARIOS, MENSAJE_EMITIR_FICHERO,
		MENSAJE_PREPARADO_EMITIR_FICHERO, MENSAJE_CONFIRMACION_CERRAR_CONEXION, MENSAJE_CONEXION,
		MENSAJE_PEDIR_USUARIOS, MENSAJE_CERRAR_CONEXION, MENSAJE_PEDIR_FICHERO, MENSAJE_PREPARADO_CLIENTESERVIDOR,
		MENSAJE_PEDIR_FICHEROS, MENSAJE_CONFIRMACION_LISTA_FICHEROS, MENSAJE_LISTA_FICHEROS, MENSAJE_REQUEST_FICHERO,
		MENSAJE_CONFIRMACION_FICHERO_PREPARADO, MENSAJE_FICHERO
	};

	protected msgType tipo;
	int src;
	int dst;

	public Message() {

	}

	public msgType getTipo() {
		return tipo;
	}

	public int getOrigen() {
		return src;
	}

	public int getDestino() {
		return dst;
	}

}
