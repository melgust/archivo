package gt.edu.archivo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alumno {
	
	//private final String formatoFecha = "EEE MMM d HH:mm:ss Z yyyy";
	private final DateFormat formatoFecha = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy"); 
	private int carne;
	private String nombre;
	private Date fechaNacimiento;
	private byte[] bytesNombre;
	private byte[] bytesFechaNacimiento;
	/**
	 * @return the carne
	 */
	public int getCarne() {
		return carne;
	}
	/**
	 * @param carne the carne to set
	 */
	public void setCarne(int carne) {
		this.carne = carne;
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
		bytesNombre = new byte[50]; //arreglo de bytes de longitud 50
		//convertir caracter por caracter a byte y agregarlo al arreglo
		for (int i = 0; i < nombre.length(); i++) {
			bytesNombre[i] = (byte)nombre.charAt(i);
		}
	}
	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		String strFecha = formatoFecha.format(fechaNacimiento);
		bytesFechaNacimiento = strFecha.getBytes();
	}
	
	public byte[] getBytesNombre() {
		return bytesNombre;
	}
	
	public void setBytesNombre(byte[] bytesNombre) {
		this.bytesNombre = bytesNombre;
		nombre = new String(bytesNombre);
	}
	
	public void setBytesFechaNacimiento(byte[] bytesFechaNacimiento) throws ParseException {
		this.bytesFechaNacimiento = bytesFechaNacimiento;
		String strFecha = new String(bytesFechaNacimiento); //convertir bytes a String
		this.fechaNacimiento = formatoFecha.parse(strFecha); //convertir a tipo de dato Date
	}
	
	public byte[] getBytesFechaNacimiento() {
		return bytesFechaNacimiento;
	}
	
}
