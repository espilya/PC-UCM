package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import message.*;
import message.client.*;
import message.server.*;

// oyenteServidor 
// El thread del cliente que escucha al servidor
public class ClientListener implements Runnable {
	private Socket s;
	private String idCliente;
	private DataBase data;
	private ObjectInputStream din;
	private ObjectOutputStream dout;
	private String ip;
	private int puertoEmisor = 9999;
	private int puerto;
	private Thread thr2;

	ClientListener(String numCliente, String ip, DataBase data, Thread thr2) {
		this.idCliente = numCliente;
		this.data = data;
		this.ip = ip;
		this.thr2 = thr2;
	}

	public void run() {
		try {
			Message msg;
			dout = new ObjectOutputStream(s.getOutputStream());
			din = new ObjectInputStream(s.getInputStream());
			establecerConexion();
			while (!s.isClosed()) {
				msg = (Message) din.readObject();
				switch (msg.getTipo()) {
				case MENSAJE_CONFIRMACION_CONEXION:
					log("MENSAJE_CONFIRMACION_CONEXION from 'Server'.");
					break;

				case MENSAJE_CONFIRMACION_LISTA_USUARIOS:
					log("MENSAJE_CONFIRMACION_LISTA_USUARIOS from 'Server'.");
					data.setListaUsuarios(((MENSAJE_CONFIRMACION_LISTA_USUARIOS) msg).getUsuarios());
					data.releaseLockLector(1);
					break;

				case MENSAJE_CONFIRMACION_LISTA_FICHEROS:
					log("MENSAJE_CONFIRMACION_LISTA_USUARIOS from 'Server'.");
					data.setFicherosServidor(((MENSAJE_CONFIRMACION_LISTA_FICHEROS) msg).getUsuarios());
					data.releaseLockLector(2);
					break;

				case MENSAJE_REQUEST_FICHERO:
					log("MENSAJE_REQUEST_FICHERO request from from 'Server'.");
					// preparar
					// enviar MENSAJE_PREPARADO_CLIENTESERVIDOR con puerto y ip. y abrir thread
					// emisor
					send(new MENSAJE_PREPARADO_EMITIR_FICHERO(((MENSAJE_REQUEST_FICHERO) msg).getIdReceptor(), ip,
							puertoEmisor));
					prepararEmisor(((MENSAJE_REQUEST_FICHERO) msg).getFichero());
					break;

				case MENSAJE_CONFIRMACION_FICHERO_PREPARADO:
					log("MENSAJE_CONFIRMACION_FICHERO_PREPARADO from from 'Server'.");
					// leer, abrir thread receptor. conectarse.
					ip = ((MENSAJE_CONFIRMACION_FICHERO_PREPARADO) msg).getIpEmisor();
					puerto = ((MENSAJE_CONFIRMACION_FICHERO_PREPARADO) msg).getpEmisor();
					// leer y parsear mensaje
					// Message m = algo;
					prepararReceptor(ip, puerto);
					// un nuevo thread
					// el thread tiene que ir al threadMain del cliente, el cliente tiene que tener
					// dos threads. unooyente y otro emisor/receptor
					break;

				case MENSAJE_CONFIRMACION_CERRAR_CONEXION:
					log("MENSAJE_CONFIRMACION_CERRAR_CONEXION from 'Server'.");
					break;
				default:
					break;
				}
			}
			dout.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	void pedirFichero(String fichName) {
		send(new MENSAJE_PEDIR_FICHERO(fichName));
	}

	void pedirFicheros() {
		data.takeLockEscritor(2);
		send(new MENSAJE_PEDIR_FICHEROS());
	}

	void pedirUsuarios() {
		data.takeLockEscritor(1);
		send(new MENSAJE_PEDIR_USUARIOS());
	}

	private void prepararReceptor(String ip, int puerto) {
		Socket s2 = null;
		try {
			s2 = new Socket(ip, puerto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread thr = new Thread(new Receptor(idCliente, data, s2));
		log("start receptor thread");
		thr.start();
	}

	private void prepararEmisor(String fich) {
		try {
			ServerSocket ss = new ServerSocket(puertoEmisor);
			log("Waiting for p2p connections...");

			Socket s2 = ss.accept();
			log("Client1 accepted...");
			// creaar threar emisor receptor y t.start
			thr2 = new Thread(new Emisor(idCliente, data, ss, s2, fich));
			thr2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void send(Message msg) {
//		log("sent: " + msg);
		try {
			dout.writeObject(msg);
			dout.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void log(String data) {
		System.out.println("[Client Thread: " + idCliente + "]: " + data);
	}

	public void addSocket(Socket s) {
		this.s = s;
	}

	void salir() {
		send(new MENSAJE_CERRAR_CONEXION());
	}

	private void establecerConexion() {
		Message t = new MENSAJE_CONEXION(idCliente, ip, data.getListaFicherosPropios());
		send(t);
	}

}
