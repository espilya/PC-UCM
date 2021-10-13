package cliente;

import java.util.List;
import java.util.concurrent.Semaphore;

import servidor.Usuario;

class DataBase {
	private List<String> listaFicherosPropios;
	private Semaphore sFP;
	private List<String> listaUsuarios;
	private Semaphore sLectorU, sEscritorU;
	private List<Usuario> listaFicherosServidor;
	private Semaphore sLectorFS, sEscritorFS;
	private Boolean lock;

	DataBase() {
		sFP = new Semaphore(1);
		sLectorU = new Semaphore(0);
		sEscritorU = new Semaphore(1);
		sLectorFS = new Semaphore(0);
		sEscritorFS = new Semaphore(1);
		lock = false;
	}
	
	
	public void takeLockFP() {
//		try {
			while(lock);
			lock = true;
//			sFP.acquire();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public void releaseLockFP() {
		lock = false;
//		sFP.release();
	}
/**
 * int var:
 * 0 - List<String> listaFicherosPropios;
 * 1 - List<String> listaUsuarios;
 * 2 - List<Usuario> listaFicherosServidor;
 * @param x
 */
	public void takeLockLector(int x) {
		try {
			switch (x) {
			case 0:
				sFP.acquire();
				break;
			case 1:
				sLectorU.acquire();
				break;
			case 2:
				sLectorFS.acquire();
				break;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * int var:
	 * 0 - List<String> listaFicherosPropios;
	 * 1 - List<String> listaUsuarios;
	 * 2 - List<Usuario> listaFicherosServidor;
	 * @param x
	 */
	public void takeLockEscritor(int x) {
		try {
			switch (x) {
			case 0:
				sFP.acquire();
				break;
			case 1:
				sEscritorU.acquire();
				break;
			case 2:
				sEscritorFS.acquire();
				break;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * int var:
	 * 0 - List<String> listaFicherosPropios;
	 * 1 - List<String> listaUsuarios;
	 * 2 - List<Usuario> listaFicherosServidor;
	 * @param x
	 */
	public void releaseLockLector(int x) {
		switch (x) {
		case 0:
			sFP.release();
			break;
		case 1:
			sLectorU.release();
			break;
		case 2:
			sLectorFS.release();
			break;
		}

	}
	/**
	 * int var:
	 * 0 - List<String> listaFicherosPropios;
	 * 1 - List<String> listaUsuarios;
	 * 2 - List<Usuario> listaFicherosServidor;
	 * @param x
	 */
	public void releaseLockEscritor(int x) {
		switch (x) {
		case 0:
			sFP.release();
			break;
		case 1:
			sEscritorU.release();
			break;
		case 2:
			sEscritorFS.release();
			break;
		}

	}

	public List<String> getListaFicherosPropios() {
		return listaFicherosPropios;
	}

	public void setListaFicherosPropios(List<String> listaFicherosPropios) {
		this.listaFicherosPropios = listaFicherosPropios;
	}

	public List<String> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<String> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Usuario> getFicherosServidor() {
		return listaFicherosServidor;
	}

	public void setFicherosServidor(List<Usuario> ficherosServidor) {
		this.listaFicherosServidor = ficherosServidor;
	}
	
	public void addFicheroPropio(String ficheros) {
		listaFicherosPropios.add(ficheros);
	}

}