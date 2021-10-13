package servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class ListaUsuarios {

	List<Usuario> informacionUsuarios;
	private Semaphore s;
	private int N;

	public ListaUsuarios() {
		s = new Semaphore(1);
		informacionUsuarios = new ArrayList();
	}

	public List<Usuario> accesData() {
		return informacionUsuarios;
	}

	public void takeLock(int i) {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void releaseLock(int i) {
		s.release();
	}

	public void addUser(Usuario user) {
		informacionUsuarios.add(user);
	}

	public void removeUser(String idCliente) {
		int i = 0;
		while (informacionUsuarios.get(i).getClientID() != idCliente && i < informacionUsuarios.size()) {
			i++;
		}
		informacionUsuarios.remove(i);
	}

}
