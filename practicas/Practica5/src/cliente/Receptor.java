package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.TimeUnit;

import message.Message;
import message.client.MENSAJE_CONEXION;
import message.p2p.*;

public class Receptor implements Runnable {

	private Socket s;
	private String idCliente;
	private DataBase data;
	private ObjectInputStream din;
	private ObjectOutputStream dout;

	Receptor(String numCliente, DataBase data, Socket s) {
		this.idCliente = numCliente;
		this.data = data;
		this.s = s;
	}

	public void run() {
		try {
			dout = new ObjectOutputStream(s.getOutputStream());
			send(new MENSAJE_CONEXION(null, null, null));
			Message msg;
			String fichero = null;
			din = new ObjectInputStream(s.getInputStream());
			msg = (Message) din.readObject();
			switch (msg.getTipo()) {
			case MENSAJE_FICHERO:
				log("MENSAJE_FICHERO from 'Emisor'.");
				fichero = ((MENSAJE_FICHERO) msg).getFichero();
				log("Recibido el fichero: " + fichero);
				data.takeLockFP();
				data.addFicheroPropio(fichero);
				data.releaseLockFP();
				break;
			default:
				log("mal mensaje from 'receptor'.");
				break;
			}
			dout.close();
			s.close();
		} catch (Exception e) {
			return;
		}

	}

	private void send(Message msg) {
		log("sent: " + msg);
		try {
			dout.writeObject(msg);
			dout.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void log(String data) {
		System.out.println("[Client Thread-Receptor: " + idCliente + "]: " + data);
	}
}
