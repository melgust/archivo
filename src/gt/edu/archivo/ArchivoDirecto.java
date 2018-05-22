package gt.edu.archivo;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ArchivoDirecto {

	// global class variables
	Scanner sc = new Scanner(System.in);
	RandomAccessFile fichero = null, definicion = null;
	private final String ruta = "/home/melgust/eclipse-workspace/archivo/src/bd.dat";
	private final String rutaDefinicion = "/home/melgust/eclipse-workspace/archivo/src/deficion.dat";
	private final int totalBytes = 83;
	private final static String formatoFecha = "dd/MM/yyyy";
	static DateFormat format = new SimpleDateFormat(formatoFecha);

	public static void main(String[] args) {
		ArchivoDirecto ad = new ArchivoDirecto();
		ad.iniciar();
		System.exit(0); // finalize application
	}

	private void iniciar() {
		int opcion = 0;
		try {
			fichero = new RandomAccessFile(ruta, "rw");
			System.out.println("Bienvenido (a)");
			int carne;
			do {
				try {
					System.out.println("Seleccione su opcion");
					System.out.println("1.\t\tAgregar");
					System.out.println("2.\t\tListar");
					System.out.println("3.\t\tBuscar");
					System.out.println("4.\t\tModificar");
					System.out.println("0.\t\tSalir");
					opcion = sc.nextInt();
					switch (opcion) {
					case 0:
						System.out.println("Gracias por usar nuestro sistema");
						break;
					case 1:
						grabarRegistro();
						break;
					case 2:
						listarRegistros();
						break;
					case 3:
						System.out.println("Ingrese el carne a buscar: ");
						carne = sc.nextInt();
						sc.nextLine();
						encontrarRegistro(carne);
						break;
					case 4:
						System.out.println("Ingrese el carne a modificar: ");
						carne = sc.nextInt();
						sc.nextLine();
						modificarRegistro(carne);
						break;
					default:
						System.out.println("Opcion no valida");
						break;
					}
				} catch (Exception e) { // capturar cualquier excepcion que ocurra
					System.out.println("Error: " + e.getMessage());
				}
			} while (opcion != 0);
			fichero.close();
		} catch (Exception e) { // capturar cualquier excepcion que ocurra
			System.out.println("Error: " + e.getMessage());
		}
	}

	private boolean grabarRegistro() {
		boolean resultado = false;
		try {
			// objeto alumno ocupa 82 bytes + uno por el cambio de linea. Total = 83
			Alumno a = new Alumno();
			System.out.println("Ingrese el carne");
			a.setCarne(sc.nextInt());
			sc.nextLine();
			System.out.println("Ingrese el nombre");
			String strNombre = "";
			int longitud = 0;
			do {
				strNombre = sc.nextLine();
				longitud = strNombre.length();
				if (longitud <= 0 || longitud > 50) {
					System.out.println("La longitud del nombre no es valida [1 - 50]");
				}
			} while (longitud <= 0 || longitud > 50);
			a.setNombre(strNombre);
			System.out.println("Ingrese la fecha (" + formatoFecha + ")");
			Date date = null;
			while (date == null) {
				date = strintToDate(sc.nextLine());
			}
			a.setFechaNacimiento(date);
			// posicionarse al final
			fichero.seek(fichero.length());// calcular la longitud el archivo
			fichero.writeInt(a.getCarne());
			fichero.write(a.getBytesNombre());
			fichero.write(a.getBytesFechaNacimiento());
			fichero.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
			resultado = true;
		} catch (Exception e) {
			resultado = false;
			System.out.println("Error al agregar el registro " + e.getMessage());
		}
		return resultado;
	}

	public void listarRegistros() {
		try {
			long longitud = fichero.length();
			if (longitud <= 0) {
				System.out.println("No hay registros");
				return; //finalizar el procedimiento
			}
			// posicionarse al principio del archivo
			fichero.seek(0);			
			Alumno a;
			while (longitud >= totalBytes) {
				a = new Alumno();
				a.setCarne(fichero.readInt());
				byte[] bNombre = new byte[50]; // leer 50 bytes para el nombre
				fichero.read(bNombre);
				a.setBytesNombre(bNombre);
				byte[] bFecha = new byte[28]; // 28 bytes para la fecha
				fichero.read(bFecha);
				fichero.readByte();// leer el cambio de linea
				a.setBytesFechaNacimiento(bFecha);
				// imprimir los campos del registro
				System.out.println("Carne: " + a.getCarne());
				System.out.println("Nombre: " + a.getNombre());
				System.out.println("Fecha de nacimiento: " + dateToString(a.getFechaNacimiento()));
				// restar los bytes del registro leido
				longitud -= totalBytes;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void encontrarRegistro(int carne) {
		try {
			long longitud = fichero.length();
			if (longitud <= 0) {
				System.out.println("No hay registros");
				return; //finalizar el procedimiento
			}
			// bandera para verificar que el registro fue encontrado
			boolean bndEncontrado = false;
			// posicionarse al principio del archivo
			fichero.seek(0);
			// solo se instancia una vez y se sobreescriben los datos debido a que se
			// mostrara un unico registro
			Alumno a = new Alumno();
			while (longitud >= totalBytes) {
				a.setCarne(fichero.readInt());
				byte[] bNombre = new byte[50]; // leer 50 bytes para el nombre
				fichero.read(bNombre);
				a.setBytesNombre(bNombre);
				byte[] bFecha = new byte[28]; // 28 bytes para la fecha
				fichero.read(bFecha);
				fichero.readByte();// leer el cambio de linea
				a.setBytesFechaNacimiento(bFecha);
				if (a.getCarne() == carne) {
					// imprimir los campos del registro
					System.out.println("Carne: " + a.getCarne());
					System.out.println("Nombre: " + a.getNombre());
					System.out.println("Fecha de nacimiento: " + dateToString(a.getFechaNacimiento()));
					bndEncontrado = true;
					// si el registro se ha encontrado entonces salir del ciclo
					break;
				}
				// restar los bytes del registro leido
				longitud -= totalBytes;
			}
			// solo si el registro no se encontro imprimir un mensaje
			if (!bndEncontrado) { // esto es equivalente a (bndEncontrado == false)
				System.out.println("No se encontro el carne indicado, por favor verifique");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void modificarRegistro(int carne) {
		try {
			// bandera para verificar que el registro fue encontrado
			boolean bndEncontrado = false, bndModificado = false;
			// posicionarse al principio del archivo
			fichero.seek(0);
			long longitud = fichero.length();
			int registros = 0;
			// solo se instancia una vez y se sobreescriben los datos debido a que se
			// mostrara un unico registro
			Alumno a = new Alumno();
			while (longitud > totalBytes) {
				a.setCarne(fichero.readInt());
				byte[] bNombre = new byte[50]; // leer 50 bytes para el nombre
				fichero.read(bNombre);
				a.setBytesNombre(bNombre);
				byte[] bFecha = new byte[28]; // 28 bytes para la fecha
				fichero.read(bFecha);
				fichero.readByte();// leer el cambio de linea
				a.setBytesFechaNacimiento(bFecha);
				if (a.getCarne() == carne) {
					System.out.println("Si no desea modificar el campo presione enter");
					System.out.println("Ingrese el nombre");
					String tmpStr = "";
					int len = 0;
					long posicion;
					do {
						tmpStr = sc.nextLine();
						len = tmpStr.length();
						if (len > 50) {
							System.out.println("La longitud del nombre no es valida [1 - 50]");
						}
					} while (len > 50);
					if (len > 0) {
						a.setNombre(tmpStr);
						// encontrar la posicion especifica del campo a modificar
						// primero encontrar la posicion del registro
						posicion = registros * totalBytes;
						fichero.seek(posicion);
						// sumar el tamanio del campo llave
						fichero.skipBytes(4); //moverse despues del carne (int = 4 bytes)
						// grabar el cambio
						fichero.write(a.getBytesNombre());
						bndModificado = true;
					}
					System.out.println("Ingrese la fecha (" + formatoFecha + ")");
					tmpStr = sc.nextLine();
					if (tmpStr.length() > 0) {
						Date date = null;
						while (date == null) {
							date = strintToDate(tmpStr);
						}
						a.setFechaNacimiento(date);
						posicion = registros * totalBytes;
						fichero.seek(posicion);
						fichero.skipBytes(4 + 50); //moverse despues del carne + el nombre (int = 4 bytes, nombre = 50 bytes)
						fichero.write(a.getBytesFechaNacimiento());
						bndModificado = true;
					}
					// imprimir los campos del registro
					if (bndModificado) { // equivalente a (bndModificado == true)
						System.out.println("El registro fue modificado correctamente, los nuevos datos son:");
					}
					System.out.println("Carne: " + a.getCarne());
					System.out.println("Nombre: " + a.getNombre());
					System.out.println("Fecha de nacimiento: " + dateToString(a.getFechaNacimiento()));
					bndEncontrado = true;
					// si el registro se ha encontrado entonces salir del ciclo
					break;
				}
				registros++;
				// restar los bytes del registro leido
				longitud -= totalBytes;
			}
			// solo si el registro no se encontro imprimir un mensaje
			if (!bndEncontrado) { // esto es equivalente a (bndEncontrado == false)
				System.out.println("No se encontro el carne indicado, por favor verifique");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public Date strintToDate(String strFecha) {
		Date date = null;
		try {
			date = format.parse(strFecha);
		} catch (Exception e) {
			date = null;
			System.out.println("Error en fecha: " + e.getMessage());
		}
		return date;
	}

	public String dateToString(Date date) {
		String strFecha;
		strFecha = format.format(date);
		return strFecha;
	}

}
