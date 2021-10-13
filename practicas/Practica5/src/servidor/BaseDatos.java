package servidor;

import java.util.List;

public class BaseDatos {
	public volatile int counter = 0;
	public int N;
	public Locks state;
	List<String> listaFicheros;

	BaseDatos() {

	}
	
	void addFichero(String str){
		listaFicheros.add("str");
	}
	
	void askFichero(int n) {
		state =  new LockRompeEmpate(n*2);
		counter = 0;
		this.N = n;
	}
	
	List<String> askListaFicheros() {
		return listaFicheros;
	}
	
}

