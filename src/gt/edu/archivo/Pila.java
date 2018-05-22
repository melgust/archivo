package gt.edu.archivo;

//codigo fuente copiado de https://pastebin.com/7JXij3uk

public class Pila {

	private Nodo cima;
	public Pila() {
		cima = null;
	}

	// operaciones
	public boolean pilaVacia() {
		if (cima == null) {
			return true;
		} else {
			return false;
		}
	}

	public void insertar(int elemento) {
		Nodo nuevo;
		nuevo = new Nodo(elemento);
		nuevo.siguiente = cima;
		cima = nuevo;
	}

	public int quitar() {
		if (pilaVacia()) {
			return -1;
		}
		int aux = cima.elemento;
		cima = cima.siguiente;
		return aux;
	}

	public void limpiarPila() {
		Nodo t;
		while (!pilaVacia()) {
			t = cima;
			cima = cima.siguiente;
			t.siguiente = null;
		}
	}
	
	public void listar() {
		Nodo t;
		t = cima;
		while (t.siguiente != null) {			
			t = t.siguiente;
		}
	}

}
