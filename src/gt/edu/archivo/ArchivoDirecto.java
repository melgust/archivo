package gt.edu.archivo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ArchivoDirecto {

	// global class variables
	Scanner sc = new Scanner(System.in);
	RandomAccessFile fichero = null, entidades = null, atributos = null;
	private final String rutaBase = "/home/melgust/eclipse-workspace/archivo/src/";
	//contiene Indice, Nombre de la entidad (30 caracteres maximo), cantidad de atributos, posicion donde inician los atributos => total bytes = 47 (incluye cambio de linea) 
	private final String rutaEntidades = "/home/melgust/eclipse-workspace/archivo/src/entidades.dat";
	//contiene indice de la entidad, nombre del atributo, tipo de dato, longitud => total de bytes = 43
	private final String rutaAtributos = "/home/melgust/eclipse-workspace/archivo/src/atributos.dat";
	private final int totalBytes = 83, bytesEntidad = 47, bytesAtributo = 43;	
	private final static String formatoFecha = "dd/MM/yyyy";
	static DateFormat format = new SimpleDateFormat(formatoFecha);
	
	private List<Entidad> listaEntidades = new ArrayList<>();
	
	public static void main(String[] args) {
		ArchivoDirecto ad = new ArchivoDirecto();
		if (ad.validarDefinicion()) {
			ad.menuDefinicion(true);
		} else {
			ad.menuDefinicion(false);
		}
		System.exit(0); // finalize application
	}
	
	//metodos para definicion
	private boolean validarDefinicion() {
		boolean res = false;
		try {
			entidades = new RandomAccessFile(rutaEntidades, "rw");
			atributos = new RandomAccessFile(rutaAtributos, "rw");
			long longitud = entidades.length();
			if (longitud <= 0) {
				System.out.println("No hay registros");
				res = false; //finalizar el procedimiento
			}
			if (longitud >= bytesEntidad) {
				// posicionarse al principio del archivo
				entidades.seek(0);
				Entidad e;
				while (longitud >= bytesEntidad) {
					e = new Entidad();
					e.setIndice(entidades.readInt());
					byte[] bNombre = new byte[30]; // leer 30 bytes para el nombre
					entidades.read(bNombre);
					e.setBytesNombre(bNombre);
					e.setCantidad(entidades.readInt());
					e.setPosicion(entidades.readLong());
					entidades.readByte();// leer el cambio de linea				
					longitud -= bytesEntidad;
					//leer atributos
					long longitudAtributos = atributos.length();
					if (longitudAtributos <= 0) {
						System.out.println("No hay registros");
						res = false; //finalizar el procedimiento
						break;
					}					
					atributos.seek(e.getPosicion());
					Atributo a;
					longitudAtributos = e.getCantidad() * bytesAtributo;
					while (longitudAtributos >= bytesAtributo) {
						a = new Atributo();
						a.setIndice(atributos.readInt());
						byte[] bNombreAtributo = new byte[30]; // leer 30 bytes para el nombre
						atributos.read(bNombreAtributo);
						a.setBytesNombre(bNombreAtributo);
						a.setValorTipoDato(atributos.readInt());
						a.setLongitud(atributos.readInt());
						atributos.readByte();// leer el cambio de linea
						e.setAtributo(a);
						longitudAtributos -= bytesAtributo;
					}
					listaEntidades.add(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void mostrarEntidad(Entidad entidad) {
		System.out.println("Indice: " + entidad.getIndice());
		System.out.println("Nombre: " + entidad.getNombre());
		System.out.println("Cantidad de atributos: " + entidad.getCantidad());
		System.out.println("Atributos:");
		int i = 1;
		for (Atributo atributo : entidad.getAtributos()) {
			System.out.println("\tNo. " +  i);
			System.out.println("\tNombre: " + atributo.getNombre());
			System.out.println("\tTipo de dato: " + atributo.getNombreTipoDato());
			if (atributo.isRequiereLongitud()) {
				System.out.println("\tLongitud: " + atributo.getLongitud());
			}
			i++;
		}
	}
	
	private boolean agregarEntidad() {
		boolean resultado = false;
		try {
			Entidad entidad = new Entidad();
			entidad.setIndice(listaEntidades.size() + 1);			
			System.out.println("Ingrese el nombre de la entidad");
			String strNombre = "";
			int longitud = 0;
			do {
				strNombre = sc.nextLine();
				longitud = strNombre.length();
				if (longitud < 3 || longitud > 30) {
					System.out.println("La longitud del nombre no es valida [3 - 30]");
				}
			} while (longitud < 3 || longitud > 30);
			entidad.setNombre(strNombre);
			System.out.println("Atributos de la entidad");
			int bndDetener = 0;
			do {
				Atributo atributo = new Atributo();
				atributo.setIndice(entidad.getIndice());
				longitud = 0;
				System.out.println("Escriba el nombre del atributo no. " + (entidad.getCantidad() + 1));
				do {
					strNombre = sc.nextLine();
					longitud = strNombre.length();
					if (longitud < 3 || longitud > 30) {
						System.out.println("La longitud del nombre no es valida [3 - 30]");
					}
				} while (longitud < 3 || longitud > 30);
				atributo.setNombre(strNombre);
				System.out.println("Seleccione el tipo de dato");
				System.out.println(tipoDato.INT.getValue() + " .......... " + tipoDato.INT.name());
				System.out.println(tipoDato.LONG.getValue() + " .......... " + tipoDato.LONG.name());
				System.out.println(tipoDato.STRING.getValue() + " .......... " + tipoDato.STRING.name());
				System.out.println(tipoDato.DOUBLE.getValue() + " .......... " + tipoDato.DOUBLE.name());
				System.out.println(tipoDato.FLOAT.getValue() + " .......... " + tipoDato.FLOAT.name());
				System.out.println(tipoDato.DATE.getValue() + " .......... " + tipoDato.DATE.name());
				System.out.println(tipoDato.CHAR.getValue() + " .......... " + tipoDato.CHAR.name());
				atributo.setValorTipoDato(sc.nextInt());
				if (atributo.isRequiereLongitud()) {
					System.out.println("Ingrese la longitud");
					atributo.setLongitud(sc.nextInt());
				} else {
					atributo.setLongitud(0);
				}
				entidad.setAtributo(atributo);
				System.out.println("Desea agregar otro atributo presione cualquier numero, de lo contrario 0");
				bndDetener = sc.nextInt();
			} while(bndDetener != 0);
			System.out.println("Los datos a registrar son: ");
			mostrarEntidad(entidad);
			System.out.println("Presione 1 para guardar 0 para cancelar");
			longitud = sc.nextInt();
			if (longitud == 1) {
				// primero guardar atributos
				//establecer la posicion inicial donde se va a guardar
				entidad.setPosicion(atributos.length());
				atributos.seek(atributos.length());// calcular la longitud el archivo
				for (Atributo atributo : entidad.getAtributos()) {
					atributos.writeInt(atributo.getIndice());
					atributos.write(atributo.getBytesNombre());
					atributos.writeInt(atributo.getValorTipoDato());
					atributos.writeInt(atributo.getLongitud());
					atributos.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
				}
				//guardar la entidad
				entidades.writeInt(entidad.getIndice());
				entidades.write(entidad.getBytesNombre());
				entidades.writeInt(entidad.getCantidad());
				entidades.writeLong(entidad.getPosicion());
				entidades.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
				listaEntidades.add(entidad);
				resultado = true;
			} else {
				System.out.println("No se guardo la entidad debido a que el usuario decidio cancelarlo");
				resultado = false;
			}
			//https://www.experts-exchange.com/questions/22988755/Some-system-pause-equivalent-in-java.html
			System.out.println("Presione una tecla para continuar...");
			System.in.read();
		} catch (Exception e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;
	}
	
	private void menuDefinicion(boolean mostrar) {
		int opcion = 1;
		while (opcion != 0) {
			System.out.println("Elija su opcion");
			System.out.println("1 ........ Agregar entidad");
			System.out.println("2 ........ Modificar entidad");
			System.out.println("3 ........ Listar entidades");			
			if (mostrar) {
				System.out.println("4 ........ Agregar registros");
			}
			System.out.println("0 ........ Salir");
			opcion = sc.nextInt();
			switch(opcion) {
			case 0:
				System.out.println("Gracias por usar nuestra aplicacion");
				break;
			case 1:
				if (agregarEntidad()) {
					System.out.println("Entidad agregada con exito");
				}
				break;
			case 2:
				break;
			case 3:
				if (listaEntidades.size() > 0) {
					int tmpInt = 0;
					System.out.println("Desea imprimir los detalles. Si, presione 1. No, presione 0?");
					tmpInt = sc.nextInt();
					if (tmpInt == 1) {
						for (Entidad entidad : listaEntidades) {
							mostrarEntidad(entidad);
						}
					} else {
						for (Entidad entidad : listaEntidades) {
							System.out.println("Indice: " + entidad.getIndice());
							System.out.println("Nombre: " + entidad.getNombre());
							System.out.println("Cantidad de atributos: " + entidad.getCantidad());
						}
					}
				} else {
					System.out.println("No hay entidades registradas");
				}
				break;
			case 4:
				iniciar();
			default:
				System.out.println("Opcion no valida");
			}
		}
		if (atributos != null) {
			try {
				atributos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (entidades != null) {
			try {
				entidades.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//metodos para guardar registros segun la definicion
	private void iniciar() {
		int opcion = 0;
		try {
			fichero = new RandomAccessFile(rutaBase, "rw");
			System.out.println("Bienvenido (a)");
			int carne;
			do {
				try {
					System.out.println("Seleccione su opcion");
					System.out.println("1.\t\tAgregar");
					System.out.println("2.\t\tListar");
					System.out.println("3.\t\tBuscar");
					System.out.println("4.\t\tModificar");
					System.out.println("0.\t\tRegresar al menu anterior");
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
