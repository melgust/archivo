package gt.edu.archivo;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
	
	private int indice;
	private String nombre;
	private int cantidad;
	private long posicion;
	private byte[] bytesNombre;
	private int bytes = 1; //inicia en uno que representa el cambio de linea
	
	private List<Atributo> atributos;

	/**
	 * @return the indice
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * @param indice the indice to set
	 */
	public void setIndice(int indice) {
		this.indice = indice;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
		bytesNombre = new byte[30]; //arreglo de bytes de longitud 30
		//convertir caracter por caracter a byte y agregarlo al arreglo
		for (int i = 0; i < nombre.length(); i++) {
			bytesNombre[i] = (byte)nombre.charAt(i);
		}
	}
	
	public byte[] getBytesNombre() {
		return bytesNombre;
	}
	
	public void setBytesNombre(byte[] bytesNombre) {
		this.bytesNombre = bytesNombre;
		nombre = new String(bytesNombre);
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the atributos
	 */
	public List<Atributo> getAtributos() {
		return atributos;
	}

	/**
	 * @param atributos the atributos to set
	 */
	public void setAtributos(List<Atributo> atributos) {
		this.atributos = atributos;
	}
	
	public void setAtributo(Atributo atributo) {
		if (this.atributos == null) {
			this.atributos = new ArrayList<>();
		}
		this.atributos.add(atributo);
		this.cantidad = this.atributos.size();
	}
	
	public void removeAtributo(Atributo atributo) {
		if (this.atributos != null) {
			if (this.atributos.size() > 0) {
				this.atributos.remove(atributo);
				this.cantidad = this.atributos.size();
			}
		}
	}

	/**
	 * @return the posicion
	 */
	public long getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(long posicion) {
		this.posicion = posicion;
	}
	
	/**
	 * @return the bytes
	 */
	public int getBytes() {	
		bytes = 1;
		for (Atributo atributo : atributos) {
			bytes += atributo.getBytes();
		}
		return bytes;
	}
	
	public void setBytes(int bytes) {
		this.bytes = bytes;
	}

}
