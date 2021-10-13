package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import message.*;
import message.client.*;
import message.server.*;

class ServerListener implements Runnable {
	private Socket s;
	private ListaUsuarios dbUsuarios;
	private List<ServerListener> threads;
	private int numCliente;
	private String idCliente;
	private String ipCliente;
	private ObjectInputStream din;
	private ObjectOutputStream dout;

	ServerListener(Socket s, int numCliente, ListaUsuarios listaUsuarios, List<ServerListener> threads) {
		this.s = s;
		this.numCliente = numCliente;
		this.dbUsuarios = listaUsuarios;
		this.threads = threads;
	}

	public void run() {
		try {
			din = new ObjectInputStream(s.getInputStream());
			dout = new ObjectOutputStream(s.getOutputStream());

			log("Cliente '" + numCliente + "' has connected.");
			Message msg;
			while (!s.isClosed()) {
				msg = (Message) din.readObject();
				switch (msg.getTipo()) {
				case MENSAJE_CONEXION:
//			    * guardar informacion del usuario (en las tablas)
//				* envio mensaje confirmacion conexion fout
					log("Cliente '" + numCliente + "' sent MENSAJE_CONEXION.");
					idCliente = ((MENSAJE_CONEXION) msg).getIdCliente();
					ipCliente = ((MENSAJE_CONEXION) msg).getIpCliente();
					List<String> listaFicheros = ((MENSAJE_CONEXION) msg).getListaFicheros();
					anadirUsuario(listaFicheros);
					log("Base de datos actual: " + accederFicheroUsuarios());
					send(new MENSAJE_CONFIRMACION_CONEXION());
					break;
				case MENSAJE_PEDIR_USUARIOS:
//			    * crear un mensaje con la informacion de usuarios en sistema
//				* envio mensaje confirmacion lista usuarios fout
					log("Cliente '" + numCliente + "' sent MENSAJE_PEDIR_USUARIOS.");
					List<Usuario> usuarios = accederFicheroUsuarios();
					List<String> listaUsuarios = new ArrayList<>();
					for (Usuario i : usuarios)
						listaUsuarios.add(i.getClientID());
					Message m = new MENSAJE_CONFIRMACION_LISTA_USUARIOS(listaUsuarios);
					send(m);
					break;
				case MENSAJE_CERRAR_CONEXION:
//			    * eliminar informacion del usuario (en las tablas)
//				* envio mensaje confirmacion cerrar conexion fout
					log("Cliente '" + numCliente + "' sent MENSAJE_CERRAR_CONEXION.");
					eliminarUsuario();
					log("Base de datos actual: " + accederFicheroUsuarios());
					send(new MENSAJE_CONFIRMACION_CERRAR_CONEXION());
					return;
				case MENSAJE_PEDIR_FICHEROS:
					log("Cliente '" + numCliente + "' sent MENSAJE_PEDIR_FICHEROs.");
					send(new MENSAJE_CONFIRMACION_LISTA_FICHEROS(accederFicheroUsuarios()));
					break;
				case MENSAJE_PEDIR_FICHERO:
//				    * buscar usuario que contiene el fichero y obtener fout2
//					* envio mensaje MENSAJE_ EMITIR_FICHERO por fout2
					log("Cliente '" + numCliente + "' sent MENSAJE_PEDIR_FICHERO.");
					contactarUsuario(obtenerUsuarioDelFichero(((MENSAJE_PEDIR_FICHERO) msg).getFichero()), idCliente,
							((MENSAJE_PEDIR_FICHERO) msg).getFichero());
//					send(new MENSAJE_REQUEST_FICHERO(idCliente));
					break;

				case MENSAJE_PREPARADO_EMITIR_FICHERO:
//			    * buscar fout1 (flujo del cliente al que hay que enviar la informacion)
//			    * envio  fout1 mensaje  MENSAJE_PREPARADO_SERVIDORCLIENTE
					log("Cliente '" + numCliente + "' sent MENSAJE_PREPARADO_EMITIR_FICHERO.");
					contactarUsuario((MENSAJE_PREPARADO_EMITIR_FICHERO) msg);
					break;
				default:
					break;
				}
			}
			dout.close();
			s.close();
		} catch (SocketException e) {
			log("error de comunicacion con el usuario: " + getIdCliente());
		} catch (Exception e) {
			log("error de comunicacion con el usuario: " + getIdCliente());
			e.printStackTrace();
		}
		eliminarUsuario();
		Servidor.numClientes--;
		return;
	}

	private Usuario obtenerUsuarioDelFichero(String name) {
		dbUsuarios.takeLock(numCliente);
		List<Usuario> t = dbUsuarios.accesData();
		dbUsuarios.releaseLock(numCliente);
		Usuario user = null;
		for (Usuario u : t) {
			for (String str : u.getListaFicheros()) {
				if (str.equals(name))
					user = u;
			}
		}
		return user;
	}

	private void contactarUsuario(Usuario u, String idReceptor, String fichero) {
		boolean found = false;
		int i = 0;
		ServerListener t = null;
		while (!found && i < threads.size()) {
			if (threads.get(i).getIdCliente().equals(u.getClientID())) {
				found = true;
				t = (ServerListener) threads.get(i);
			}
			i++;
		}
		t.writeToYourSocketThis(new MENSAJE_REQUEST_FICHERO(idReceptor, fichero));
	}

	private void contactarUsuario(MENSAJE_PREPARADO_EMITIR_FICHERO msg) {
		String receptor = msg.getIdReceptor();
		boolean found = false;
		int i = 0;
		ServerListener t = null;
		while (!found && i < threads.size()) {
			if (threads.get(i).getIdCliente().equals(receptor)) {
				found = true;
				t = (ServerListener) threads.get(i);
			}
			i++;
		}
		t.writeToYourSocketThis(new MENSAJE_CONFIRMACION_FICHERO_PREPARADO(msg.getIpEmisor(), msg.getpEmisor()));
	}

	private void anadirUsuario(List<String> listaFicheros) {
		Usuario t = new Usuario(getIdCliente(), getIpCliente(), listaFicheros);
//		while (dbUsuarios.checkLock());
		dbUsuarios.takeLock(numCliente);
		dbUsuarios.addUser(t);
		dbUsuarios.releaseLock(numCliente);
	}

	private void eliminarUsuario() {
//		while (dbUsuarios.checkLock());
		dbUsuarios.takeLock(numCliente);
		dbUsuarios.removeUser(getIdCliente());
		dbUsuarios.releaseLock(numCliente);
	}

	private List<Usuario> accederFicheroUsuarios() {
//		while (dbUsuarios.checkLock());
		dbUsuarios.takeLock(numCliente);
		List<Usuario> t = dbUsuarios.accesData();
		dbUsuarios.releaseLock(numCliente);
		return t;
	}

	private void send(Message msg) {
		try {
			dout.writeObject(msg);
			dout.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void log(Object data) {
		System.out.println("[Server Thread: " + numCliente + "]: " + data);
	}

	public String getIdCliente() {
		return idCliente;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public ObjectOutputStream getDout() {
		return dout;
	}

	public void writeToYourSocketThis(Message msg) {
		send(msg);
	}

}

//
//import cliente.Cliente;
//
//// es el de server
//public class ServerListener extends Thread{
////  OyenteCliente == ServerListener
//
////	run
////	-----
//	
////	List<Cliente> listUsuario;
//	
////	ficheros <string- fichero1, fichero2, ...>
////	cliente <id -string, ficheros>
////	listaClientes <cliente1, cliente2...>
//	while (true){
////	   Mensaje m = (Mensaje) fin.readObject();
////	   case m:
////		  MSG m = fin.readObject();
//			switch (m) {
//			case "MENSAJE_CONEXION":
//				System.out.print(conexionEstablecida);
//				break;
//				
//			case "MENSAJE_LISTA_USARIOS":
//				
//				System.out.print(listUsuario);
//				break;
//				
//			case "MENSAJE_CERRAR_CONEXION":
//				break;
//				
//			case "MENSAJE_PEDIR_FICHERO":
//				break;
//				
//			case "MENSAJE_PREPARADO_CLIENTESERVIDOR":
//				System.out.print("Cerrado la conexion");
//				break;
//
//			default:
////				ERROR
//				break;
//			}
////	     - MENSAJE_CONEXION:
////	        * guardar informacion del usuario (en las tablas)
////		* envio mensaje confirmacion conexion fout
////
////	     - MENSAJE_LISTA_USARIOS:
////	        * crear un mensaje con la informacion de usuarios en sistema
////		* envio mensaje confirmacion lista usuarios fout
////
////	     - MENSAJE_CERRAR_CONEXION:
////	        * eliminar informacion del usuario (en las tablas)
////		* envio mensaje confirmacion cerrar conexion fout
////
////	     - MENSAJE_PEDIR_FICHERO:
////	        * buscar usuario que contiene el fichero y obtener fout2
////		* envio mensaje MENSAJE_ EMITIR_FICHERO por fout2
////
////	     - MENSAJE_PREPARADO_CLIENTESERVIDOR:
////	        * buscar fout1 (flujo del cliente al que hay que enviar la informacion)
////	        * envio  fout1 mensaje  MENSAJE_PREPARADO_SERVIDORCLIENTE
//	}
//
//
//}
