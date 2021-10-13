package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import message.Message;
import message.client.MENSAJE_CONEXION;
import message.p2p.MENSAJE_FICHERO;
import message.server.MENSAJE_CONFIRMACION_CONEXION;

public class Emisor implements Runnable {
	private Socket s;
	private String idCliente;
	private String ipCliente;
	private ObjectInputStream din;
	private ObjectOutputStream dout;
	private DataBase data;
	private String fichero;
	private ServerSocket ss;

	Emisor(String numCliente, DataBase data, ServerSocket ss, Socket s, String fich) {
		this.idCliente = numCliente;
		this.data = data;
		this.s = s;
		this.fichero = fich;
		this.ss = ss;
	}

	public void run() {
		try {
			log("emisor thread started");
			din = new ObjectInputStream(s.getInputStream());
			log("emisor thread read");
			Message msg;
//			while (!s.isClosed()) {
			dout = new ObjectOutputStream(s.getOutputStream());
			msg = (Message) din.readObject();
			switch (msg.getTipo()) {
			case MENSAJE_CONEXION:
				log("MENSAJE_CONEXION from 'receptor'.");
//					idCliente = ((MENSAJE_CONEXION) msg).getIdCliente();
//					ipCliente = ((MENSAJE_CONEXION) msg).getIpCliente();
				// buscar fichero en data y usando semaforo accedar al list de ficheros y coger
				// el fichero
				String ficheroParaMandar = null;
				data.takeLockFP();
				for (String str : data.getListaFicherosPropios()) {
					if (str.equals(fichero)) {
						ficheroParaMandar = str;
					}
				}
				data.releaseLockFP();
				send(new MENSAJE_FICHERO(ficheroParaMandar));
				break;

			default:
				log("mal mensaje from 'receptor'.");
				break;
			}
//			}
			dout.close();
			s.close();
			ss.close();
		} catch (Exception e) {
			return;
		}
	}

	private void send(Message msg) {
		log("sent: " + msg);
		try {
			dout.writeObject(msg);
			dout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void log(String data) {
		System.out.println("[Client Thread-Emisor: " + idCliente + "]: " + data);
	}
}
