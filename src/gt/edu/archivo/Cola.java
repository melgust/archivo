package gt.edu.archivo;

public class Cola {    
    
    private Nodo raiz, cima;
    
    public Cola() {
        raiz = null;
        cima = null;
    }
    
    public boolean colaVacia (){
        if (raiz == null)
            return true;
        else
            return false;
    }

    public void insertar (int dato)
    {
        Nodo nuevo = new Nodo(dato);        
        nuevo.siguiente = null;
        if (colaVacia ()) {
            raiz = nuevo;
            cima = nuevo;
        } else {
            cima.siguiente = nuevo;
            cima = nuevo;
        }
    }

    public int quitar(){
        if (colaVacia()) {
        	System.out.println("La cola esta vacia");
        	return -1;
        } 
        int aux = raiz.elemento;
        if (raiz == cima){
            raiz = null;
            cima = null;
        } else {
            raiz = raiz.siguiente;
        }
        System.out.println("Dato eliminado: " + aux);
        return aux;
    }

    public void listar() {
        Nodo t = raiz;
        while (t != null) {
            System.out.println("Dato: " + t.elemento);
            t = t.siguiente;
        }
    }
    
    public void vaciar() {
		while (!colaVacia()) {
			raiz = raiz.siguiente;
		}
    }
    
}
