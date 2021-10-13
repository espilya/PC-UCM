package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import message.Message;
import servidor.Usuario;

public class Cliente {

	DataBase data;
	private String id;
	private String ip;
	private int puerto = 9876;
	private ClientListener cl;
	private Thread thr2;
	private ObjectInputStream din;
	private ObjectOutputStream dout;
	private Scanner sc;

	public static void main(String[] args) throws IOException {
		Cliente cliente = new Cliente();
	}

	public Cliente() {
		sc = new Scanner(System.in);
		data = new DataBase();
		data.setListaUsuarios(new ArrayList<String>());
		data.setListaFicherosPropios(new ArrayList<String>());

		int cliente = 1;
		
		ArrayList<String> tt = new ArrayList<String>();
//		menuInicial();
		if (cliente == 1) {
			id = "a";
			ip = "127.0.0.1";
			tt.add("Don_Quijote_de_la_Mancha.pdf");
			tt.add("Oldboy.mp4");
			tt.add("Pulp_Fiction.mp4");
			data.setListaFicherosPropios(tt);
		} else if (cliente == 2) {
			id = "b";
			ip = "127.0.0.2";
			tt.add("Gregory R. Andrews - Foundations of Multithreaded, Parallel, and Distributed Programming-Pearson (1999).pdf");
			data.setListaFicherosPropios(tt);
		} else if (cliente == 3) {
			id = "c";
			ip = "127.0.0.3";
			tt.add("Serial_Experiments_Lain.zip");
			tt.add("Ghost.in.the.Shell_(1995)_BluRay.mkv");
			tt.add("Trainspotting_1996_BDRip_1080p.mp4");
			data.setListaFicherosPropios(tt);
		}
		cl = new ClientListener(id, ip, data, thr2);

		log("Connecting to server...");

		try {
			Socket s = new Socket(ip, puerto);
			cl.addSocket(s);
			Thread t = new Thread(cl);
			t.start();
			mainLoop();
		} catch (ConnectException e) {
			log("Error al conectarse con el servidor");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int menu() {
		System.out.println("           -Menu-           ");
		System.out.println("1 - Consultar lista ficheros");
		System.out.println("2 - Consultar lista usuarios");
		System.out.println("3 - Consultar lista fichero propios");
		System.out.println("4 - Pedir fichero");
		System.out.println("0 - Salir");
		int t = sc.nextInt();
		return t;
	}

	private void mainLoop() {
		while (true) {
			int op = menu();

			if (op == 1) {
				cl.pedirFicheros();
				mostrarFicheros();
			} else if (op == 2) {
				cl.pedirUsuarios();
				mostrarUsuarios();
			} else if (op == 3) {
				mostrarFicherosPropios();
			} else if (op == 4) {
				cl.pedirFicheros();
				cl.pedirFichero(elegirFichero());
				// acceder base de datos con comprobacion de lock y mostart fichero
			} else if (op == 0) {
				cl.salir();
				System.exit(0);
			}
		}
	}

	private void log(String data) {
		System.out.println("[Cliente]: " + data);
	}

	private void menuInicial() {
		System.out.print("Enter id- ");
		id = sc.next();
		System.out.print("Enter ip- ");
		ip = sc.next();
	}

	private void mostrarFicherosPropios() {
		data.takeLockFP();
		System.out.println("Ficheros propios: ");
		for (String str : data.getListaFicherosPropios()) {
			System.out.println(" " + str);
		}
		System.out.println(" ");
		data.releaseLockFP();
		;
	}

	private void mostrarFicheros() {
		data.takeLockLector(2);
		System.out.println("Ficheros red: ");
		for (Usuario user : data.getFicherosServidor()) {
			for (String str : user.getListaFicheros()) {
				System.out.println(" " + str);
			}
		}
		System.out.println(" ");
		data.releaseLockEscritor(2);
		;
	}

	private void mostrarUsuarios() {
		data.takeLockLector(1);
		System.out.println("Usuarios red: ");
		for (String user : data.getListaUsuarios()) {
			System.out.println(" " + user);
		}
		System.out.println(" ");
		data.releaseLockEscritor(1);
		;
	}

	private String elegirFichero() {
		data.takeLockLector(2);
		int x;
		int i = 0;
		List<String> nombreFic = new ArrayList<String>();
		for (Usuario user : data.getFicherosServidor()) {
			if (!id.equals(user.getClientID())) {
				for (String str : user.getListaFicheros()) {
					System.out.println(i + " - " + str);
					nombreFic.add(str);
					i++;
				}
			}
		}
		x = sc.nextInt();

		System.out.println(" ");
		data.releaseLockEscritor(2);
		return nombreFic.get(x);
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

//	Cliente -- main
//	========
//
//	+ leer nombre teclado
//	+ crear socket con servidor
//	+ crear nuevo thread OyenteServidor para leer el socket
//	- enviar MENSAJE_CONEXION
//	- establecer menu con usuario:
//	   * consultar lista usuarios:
//	        enviar MENSAJE_LISTA_USUARIOS
//	   * pedir fichero
//	        enviar MENSAJE_PEDIR_FICHERO 3
//	   * salir
//	        enviar MENSAJE_CERRAR_CONEXION
//	  
	/*
	 * private static String ID;
	 * 
	 * public static void main(String[] args) {
	 * 
	 * InputStreamReader isr = new InputStreamReader(System.in); BufferedReader br =
	 * new BufferedReader (isr); ID = br.readLine(); ServerSocket ss = new
	 * ServerSocket(500); Socket socket;
	 * 
	 * // public void run(){ // while(true){ // s = new Socket(ss.accept()); //
	 * Emitir th = new Emitir(s); // th.start(); // } // } // ......
	 * ObjectInputStream objectInput = new
	 * ObjectInputStream(socket.getInputStream()); / ) {
	 * 
	 * ClientListener os = new ClientListener();
	 * 
	 * Fichero f = (Fichero) objectInput.readObject();
	 * 
	 * 
	 * // .... }
	 */
}
