package gt.edu.archivo;

public class Atributo {

	private int indice;
	private String nombre;
	private int valorTipoDato;
	private String nombreTipoDato;
	private int longitud;
	private int bytes;
	private boolean requiereLongitud;
	private byte[] bytesNombre;
	private TipoDato tipoDato;

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
		if (valorTipoDato == TipoDato.STRING.getValue()) {
			this.requiereLongitud = true;
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
	
	public void setNombreTipoDato() {
		if (this.valorTipoDato == TipoDato.STRING.getValue()) {			
			this.nombreTipoDato = TipoDato.STRING.name();
			this.bytes = this.longitud;
			tipoDato = TipoDato.STRING;
		}
		if (this.valorTipoDato == TipoDato.INT.getValue()) {
			this.nombreTipoDato = TipoDato.INT.name();
			this.bytes = 4;
			tipoDato = TipoDato.INT;
		}
		if (this.valorTipoDato == TipoDato.LONG.getValue()) {
			this.nombreTipoDato = TipoDato.LONG.name();
			this.bytes = 8;
			tipoDato = TipoDato.LONG;
		}
		if (this.valorTipoDato == TipoDato.DOUBLE.getValue()) {
			this.nombreTipoDato = TipoDato.DOUBLE.name();
			this.bytes = 8;
			tipoDato = TipoDato.DOUBLE;
		}
		if (this.valorTipoDato == TipoDato.FLOAT.getValue()) {
			this.nombreTipoDato = TipoDato.FLOAT.name();
			this.bytes = 4;
			tipoDato = TipoDato.FLOAT;
		}
		if (this.valorTipoDato == TipoDato.DATE.getValue()) {
			this.nombreTipoDato = TipoDato.DATE.name();
			this.bytes = 28;			
			tipoDato = TipoDato.DATE;
		}
		if (this.valorTipoDato == TipoDato.CHAR.getValue()) {
			this.nombreTipoDato = TipoDato.CHAR.name();
			/*
			 * la documentacion de Java indica que un tipo CHAR ocupa dos bytes
			 * sin embargo RamdomAccessFile no tiene una escritura de tipo char
			 * por lo que se usara tipo byte, es la razon del por que char sera de un byte 
			 */
			this.bytes = 1;
			tipoDato = TipoDato.CHAR;
		}
	}
	
	/**
	 * @return the bytes
	 */
	public int getBytes() {
		return bytes;
	}

	/**
	 * @return the tipoDato
	 */
	public TipoDato getTipoDato() {
		return tipoDato;
	}

}
