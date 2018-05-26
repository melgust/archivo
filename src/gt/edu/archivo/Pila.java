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
		System.out.println("Dato agregado");
	}

	public int quitar() {
		if (pilaVacia()) {
			return -1;
		}
		int aux = cima.elemento;
		cima = cima.siguiente;
		System.out.println("Dato eliminado: " + aux);
		return aux;
	}

	public void vaciar() {
		Nodo t;
		while (!pilaVacia()) {
			t = cima;
			cima = cima.siguiente;
			t.siguiente = null;
		}
	}
	
	public void listar() {
		if (pilaVacia()) {
			System.out.println("La pila esta vacia");
		} else {
			Nodo t;
			t = cima;			
			while (t != null) {
				System.out.println("Dato: " + t.elemento);
				t = t.siguiente;
			}
		}
	}

}
