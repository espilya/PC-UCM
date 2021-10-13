package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// OyenteServidor == ClientListener
// OyenteCliente == ServerListener

/**
 * 
 * @Alumnos <p> 
 *  Ilya Lapshin <p> 
 *  Long Lin
 *
 */
public class Servidor {
	private ListaUsuarios usuarios;
	private List<ServerListener> threads;
	public static int numClientes;
	private int port = 9876;

	public static void main(String[] args) throws IOException {
		numClientes = 0;
		Servidor server = new Servidor();
	}

	private Servidor() throws IOException {
		ServerSocket ss = new ServerSocket(port);
//		leer fichero y anadirlo a usuarios@ListaUsuarios
		threads = new ArrayList<>();
		usuarios = new ListaUsuarios();
		log("Waiting for connections...");
		while (true) {
			try {
				Socket s = ss.accept();
				log("Client accepted...");
				ServerListener sl = new ServerListener(s, numClientes, usuarios, threads);
				Thread t = new Thread(sl);
				threads.add(sl);
				t.start();
				numClientes++;
			} catch (IOException e) {
				e.printStackTrace();
			}
//			onClosedConnection();
		}
	}

	private void leerJSON() {
//		try {
//
////			String jsonString = ... ; //assign your JSON String here
////			JSONObject obj = new JSONObject(jsonString);
////			String pageName = obj.getJSONObject("pageInfo").getString("pageName");
////
////			JSONArray arr = obj.getJSONArray("posts"); // notice that `"posts": [...]`
////			for (int i = 0; i < arr.length(); i++)
////			{
////			    String post_id = arr.getJSONObject(i).getString("post_id");
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}
	
	


	private void log(String data) {
		System.out.println("[Server]: " + data);
	}

	private void onClosedConnection() {
		log("Client '" + numClientes + "' has disconnected.");
		log("Deleting '" + numClientes + "' host from list.");
//		actualizar la tabla
		numClientes--;
	}

}