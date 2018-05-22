package gt.edu.archivo;

public class Atributo {

	private int indice;
	private String nombre;
	private int valorTipoDato;
	private String nombreTipoDato;
	private int longitud;
	private boolean requiereLongitud;
	private byte[] bytesNombre;

	/**
	 * @return the indice
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * @param indice
	 *            the indice to set
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
	 * @param nombre
	 *            the nombre to set
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
	 * @return the valorTipoDato
	 */
	public int getValorTipoDato() {
		return valorTipoDato;
	}

	/**
	 * @param valorTipoDato
	 *            the valorTipoDato to set
	 */
	public void setValorTipoDato(int valorTipoDato) {
		this.valorTipoDato = valorTipoDato;
		if (valorTipoDato == tipoDato.STRING.getValue()) {
			this.requiereLongitud = true;
			this.nombreTipoDato = tipoDato.STRING.name();
		}
		//solo para fijar el nombre
		if (valorTipoDato == tipoDato.INT.getValue()) {
			this.nombreTipoDato = tipoDato.INT.name();
		}
		if (valorTipoDato == tipoDato.LONG.getValue()) {
			this.nombreTipoDato = tipoDato.LONG.name();
		}
		if (valorTipoDato == tipoDato.DOUBLE.getValue()) {
			this.nombreTipoDato = tipoDato.DOUBLE.name();
		}
		if (valorTipoDato == tipoDato.FLOAT.getValue()) {
			this.nombreTipoDato = tipoDato.FLOAT.name();
		}
		if (valorTipoDato == tipoDato.DATE.getValue()) {
			this.nombreTipoDato = tipoDato.DATE.name();
		}
		if (valorTipoDato == tipoDato.INT.getValue()) {
			this.nombreTipoDato = tipoDato.INT.name();
		}
		if (valorTipoDato == tipoDato.CHAR.getValue()) {
			this.nombreTipoDato = tipoDato.CHAR.name();
		}
	}

	/**
	 * @return the longitud
	 */
	public int getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud
	 *            the longitud to set
	 */
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the requiereLongitud
	 */
	public boolean isRequiereLongitud() {
		return requiereLongitud;
	}

	/**
	 * @return the nombreTipoDato
	 */
	public String getNombreTipoDato() {
		return nombreTipoDato;
	}
	
	

}
