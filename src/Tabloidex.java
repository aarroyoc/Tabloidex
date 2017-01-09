import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Tabloidex {
	public static Ventana ventana;

	public static void pruebaGrafica() {
		ventana = new Ventana();

	}

	public static int genNumero(int max) {
		// Genera un numero aleatorio entre el 1 y el maximo
		Random random = new Random();
		int rand = 1 + random.nextInt(max);
		return rand;
	}

	public static void modoContinuo(Scanner in) {
		int colors = 2;
		int size = 9;
		boolean exit = false; // salir del modo continuo?
		int pasos = 0;
		byte maxScore = readScoreFor((byte) size, (byte) 0, (byte) colors); // leer puntuacion max para 

		// iniciamos el primer nivel
		System.out.println("NIVEL 1 - 9x9 / 2 colores");

		while (!exit) {
			maxScore = readScoreFor((byte) size, (byte) 0, (byte) colors);
			int[][] tablero = genTablero(size, colors);
			setupTablero(tablero);
			printTablero(tablero);
			while (!esCompleto(tablero)) {
				byte selection;
				do {
					System.out.println("Pasos empleados: " + pasos + "\tMinimo de pasos: " + maxScore);
					System.out.print("Introduce el color: ");
					selection = in.nextByte();
					System.out.println();
				} while (selection == tablero[0][0] % 10 || selection < 1 || selection > colors); // validar entrada

				pasos++;

				// Cambiar el valor de la seleccion al nuevo valor
				for (int i = 0; i < tablero.length; i++) {
					for (int j = 0; j < tablero.length; j++) {
						if (tablero[i][j] / 10 == 1) {
							tablero[i][j] = 10 + selection;
						}
					}
				}

				// Contagiar
				contagiar(tablero);
				printTablero(tablero);
			}

			// Mostrar resultados
			System.out.println("MODO CONTINUO");
			switch (size) {
			case 9:
				System.out.println("Tamanno de tablero: PEQUENNO (9x9)");
				break;
			case 11:
				System.out.println("Tamanno de tablero: MEDIANO (11x11)");
				break;
			case 15:
				System.out.println("Tamanno de tablero: GRANDE (15x15");
				break;
			}
			System.out.println("Numero de colores: " + colors);
			System.out.println("Pasos empleados: " + pasos);

			if (maxScore > pasos || maxScore == 0) {
				writeScoreFor((byte) size, (byte) 0, (byte) colors, (byte) pasos);
				System.out.println("Menor numero de pasos empleados: " + pasos);
			} else {
				System.out.println("Menor numero de pasos empleados: " + maxScore);
			}

			// Mostrar menu
			while (!exit && pasos != 0) {
				System.out.println();
				System.out.println("NUEVO TABLERO?");
				System.out.println("0. Volver a menu de MODO DE JUEGO");
				System.out.println("1. Nuevo tablero con estas caracteristicas");
				System.out.println("2. Cambiar tamanno");
				System.out.println("3. Cambiar numero de colores");
				int option;
				do {
					option = in.nextInt();
				} while (option < 0 || option > 3);
				switch (option) {
				case 0:
					exit = true;
					break;
				case 1:
					pasos = 0;
					break;
				case 2:
					do {
						System.out.println("Introduce el nuevo tamano del tablero (9, 11 o 15):");
						size = in.nextInt();
					} while (size != 9 && size != 11 && size != 15);
					break;
				case 3:
					do {
						System.out.println("Introduce el numero de colores (entre 2 y 6)");
						colors = in.nextInt();
					} while (colors < 2 || colors > 6);
					break;

				}
			}
		}

	}

	public static void contagiar(int[][] tablero) {
		// se repite el proceso para permitir que el contagio pueda propagarse de hacia arriba y hacia la izquierda
		// con un número de veces igual al ancho del tablero por 2 se asegura para todos los casos
		// pues se asegura que recorra hacia arriba y hacia la izquierda en casos extremos (espirales)
		for (int q = 0; q < tablero.length*2; q++) {

			// pasada simple
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero.length; j++) {
					if (tablero[i][j] / 10 == 1) {
						int color = tablero[i][j] % 10;
						if (i + 1 < tablero.length && tablero[i + 1][j] == color)
							tablero[i + 1][j] += 10;
						if (i - 1 >= 0 && tablero[i - 1][j] == color)
							tablero[i - 1][j] += 10;
						if (j - 1 >= 0 && tablero[i][j - 1] == color)
							tablero[i][j - 1] += 10;
						if (j + 1 < tablero.length && tablero[i][j + 1] == color)
							tablero[i][j + 1] += 10;
					}
				}
			}
		}
	}

	public static void setupTablero(int[][] tablero) {
		// modifica los tableros para hacer la preseleccion
		tablero[0][0] += 10;
		contagiar(tablero);

	}

	public static boolean esCompleto(int[][] tablero) {
		// se comprueban si todas las casillas son iguales a la casilla [0,0]
		boolean completo = true;
		int inicial = tablero[0][0];
		for (int i = 0; i < tablero.length && completo; i++) {
			for (int j = 0; j < tablero.length && completo; j++) {
				if (inicial != tablero[i][j]) {
					completo = false;
				}
			}
		}
		return completo;
	}

	public static void modoProgresivo(Scanner in) {
		boolean exit = false;
		while (!exit) {
			System.out.println("MODO PROGRESIVO");
			System.out.println("0. Volver a menu de modo de juego");

			byte score = readScoreFor((byte) 9, (byte) 1, (byte) 2);
			if (score == 0)
				System.out.println("1. Sin resolver todavia");
			else
				System.out.println("1. Resuelto en " + score + " pasos");

			score = readScoreFor((byte) 9, (byte) 1, (byte) 3);
			if (score == 0)
				System.out.println("2. Sin resolver todavia");
			else
				System.out.println("2. Resuelto en " + score + " pasos");

			score = readScoreFor((byte) 9, (byte) 1, (byte) 4);
			if (score == 0)
				System.out.println("3. Sin resolver todavia");
			else
				System.out.println("3. Resuelto en " + score + " pasos");

			score = readScoreFor((byte) 9, (byte) 1, (byte) 5);
			if (score == 0)
				System.out.println("4. Sin resolver todavia");
			else
				System.out.println("4. Resuelto en " + score + " pasos");

			byte seleccion = -1;
			do {
				seleccion = in.nextByte();
			} while (seleccion < 0 || seleccion > 4);

			if (seleccion == 0)
				return;

			while (seleccion > 0) {
				int pasos = 0;
				int[][] tablero = tableroProgresivo(seleccion);
				setupTablero(tablero);
				printTablero(tablero);
				byte maxScore = readScoreFor((byte) 9, (byte) 1, (byte) (seleccion + 1));
				while (!esCompleto(tablero)) {
					byte selection;
					do {
						System.out.println("Pasos empleados: " + pasos + "\tMinimo de pasos: " + maxScore);
						System.out.print("Introduce el color: ");
						selection = in.nextByte();
						System.out.println();
					} while (selection == tablero[0][0] % 10 || selection < 1 || selection > 6);

					pasos++;

					for (int i = 0; i < tablero.length; i++) {
						for (int j = 0; j < tablero.length; j++) {
							if (tablero[i][j] / 10 == 1) {
								tablero[i][j] = 10 + selection;
							}
						}
					}

					contagiar(tablero);
					printTablero(tablero);
				}
				// guardar si pasos es mejor
				if(maxScore > pasos || maxScore == 0){
					writeScoreFor((byte)9,(byte)1,(byte)(seleccion + 1),(byte)pasos);
				}

				System.out.println("MODO PROGRESIVO");
				System.out.println("0. Salir a la lista de niveles");
				System.out.println("1. Ir al siguiente nivel");

				byte menu = -1;
				do {
					menu = in.nextByte();
				} while (menu < 0 || menu > 1);

				switch (menu) {
				case 0:
					seleccion = 0;
					break;
				case 1:
					seleccion++;
					if (seleccion == 5)
						seleccion = 1;
					break;
				}
			}
		}
	}

	public static int[][] tableroProgresivo(int s) {
		// almacen de tableros para el modo progresivo
		switch (s) {
		case 1:
			return new int[][] { { 2, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
		case 2:
			return new int[][] { { 3, 2, 1, 1, 1, 1, 1, 1, 1 }, { 2, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
		case 3:
			return new int[][] { { 1, 1, 1, 1, 1, 2, 3, 1, 1 }, { 1, 1, 1, 1, 2, 3, 1, 1, 1 },
					{ 1, 1, 1, 2, 3, 1, 1, 1, 1 }, { 1, 1, 2, 3, 1, 1, 1, 1, 1 }, { 1, 2, 3, 1, 2, 2, 2, 2, 2 },
					{ 2, 3, 1, 1, 2, 3, 3, 3, 2 }, { 3, 1, 1, 1, 2, 3, 1, 3, 2 }, { 1, 1, 1, 1, 2, 3, 3, 3, 2 },
					{ 1, 1, 1, 1, 2, 2, 2, 2, 2 } };
		default:
			return new int[][] { { 1, 2, 2, 2, 2, 2, 2, 2, 2 }, { 1, 2, 2, 2, 2, 2, 2, 2, 2 },
					{ 3, 1, 1, 1, 1, 1, 1, 1, 1 }, { 3, 2, 2, 2, 2, 2, 2, 2, 2 }, { 3, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 3, 2, 2, 2, 2, 2, 2, 2, 2 }, { 3, 1, 1, 1, 1, 1, 1, 1, 1 }, { 3, 2, 2, 2, 2, 2, 2, 2, 2 },
					{ 3, 1, 1, 1, 1, 1, 1, 1, 1 } };
		}
	}

	public static int[][] genTablero(int size, int colors) {
		// generacion de tablero aleatorio
		Random random = new Random();
		int[][] tablero = new int[size][size];
		for (byte i = 0; i < size; i++) {
			for (byte j = 0; j < size; j++) {
				tablero[i][j] = 1 + random.nextInt(colors);
			}
		}
		return tablero;
	}

	public static void printTablero(int[][] tablero) {
		// imprimir tablero
		// parte arriba
		System.out.print("\u250f");
		for (byte i = 0; i < tablero.length * 2 - 1; i++) {
			System.out.print((i % 2 == 0) ? "\u2501" : "\u2533");
		}
		System.out.println("\u2513");

		// imprimir
		for (byte i = 0; i < tablero.length; i++) {
			// Numeros

			System.out.print("\u2503");
			for (byte j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] % 10);
				System.out.print("\u2503");
			}
			System.out.println("");

			// Intercaja
			if (i != tablero.length - 1) {
				System.out.print("\u2523");
				for (byte z = 0; z < tablero.length * 2 - 1; z++) {
					System.out.print((z % 2 == 0) ? "\u2501" : "\u254b");
				}
				System.out.println("\u252b");
			}
		}

		// parte abajo
		System.out.print("\u2517");
		for (byte i = 0; i < tablero.length * 2 - 1; i++) {
			System.out.print((i % 2 == 0) ? "\u2501" : "\u253b");
		}
		System.out.println("\u251b");

		if (ventana != null) {
			ventana.setMatrix(tablero);
		}

	}

	public static void writeScoreFor(byte size, byte mode, byte color, byte score) {
		// MODO BINARIO
		// EDITAR ARCHIVO CON "hexedit Tabloidex.dat"
		// F2 - Guardar
		// Ctrl+C - Salir
		// EDITAR ARCHIVO CON "vim -b Tabloidex.dat"
		// Pasar a HEXADECIMAL ":%!xxd"
		// Retornar para guardar ":%!xxd -r"

		// FILE
		// | SIZE 4 BITS| MODE 1 BIT| COLOR/TABLE 3 BITS| SCORE 8 BITS |
		
		// se lee completamente el archivo
		List<Byte> ids = new ArrayList<Byte>();
		List<Byte> scores = new ArrayList<Byte>();
		try {
			FileInputStream fsIn = new FileInputStream(new File("Tabloidex.dat"));
			DataInputStream in = new DataInputStream(fsIn);
			if (!(in.readByte() == 'T' && in.readByte() == 'X')) {
				in.close();
				throw new Exception("No es un fichero Tabloidex");
			}
			while (in.available() >= 2) {
				ids.add(in.readByte());
				scores.add(in.readByte());
			}
			in.close();
			fsIn.close();
		} catch (Exception e) {
			// System.out.println("Excepcion: "+e.getMessage());
		}

		// se reescribe el archivo menos la puntuación correspondiente al byteID que vamos a modificar
		// esa se escribe aparte
		try {
			FileOutputStream fsOut = new FileOutputStream(new File("Tabloidex.dat"));
			DataOutputStream out = new DataOutputStream(fsOut);
			out.write(0x54); // T
			out.write(0x58); // X
			byte id = generarByteID(size, mode, color);
			out.write(id);
			out.write(score);
			for (byte i = 0; i < ids.size(); i++) {
				if ((byte) ids.get(i) != id) {
					out.write((byte) ids.get(i));
					out.write((byte) scores.get(i));
				}
			}

			out.close();
			fsOut.close();
		} catch (Exception e) {
			// System.out.println("EXCEPCION: "+e.getMessage());
		}

	}

	public static byte generarByteID(byte size, byte mode, byte color) {
		byte id = (byte) (size << 4);
		id |= (byte) (mode << 3);
		id |= color;
		return id;
	}

	public static byte readScoreFor(byte size, byte mode, byte color) {
		// se busca la puntuacion y se devuelve si existe
		byte score = 0x00;
		try {
			FileInputStream fs = new FileInputStream(new File("Tabloidex.dat"));
			DataInputStream in = new DataInputStream(fs);
			// comprobar si es fichero Tabloidex
			if (in.readByte() == 'T' && in.readByte() == 'X') {
				byte searchByte = generarByteID(size, mode, color);
				// mientras haya bytes disponibles
				while (in.available() >= 2) {
					// Buscar byte que coincida con la puntuacion que buscamos
					if (searchByte == in.readByte()) {
						score = in.readByte();
					} else {
						in.readByte();
					}
				}
				in.close();
				fs.close();
				throw new Exception("Puntuacion no encontrada");
			} else {
				in.close();
				fs.close();
				throw new Exception("No es un fichero Tabloidex");
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
		return score;
	}

	public static void resetScore() {
		// se elimina el fichero para borrar todas las puntuaciones
		File tabloidex = new File("Tabloidex.dat");
		if (tabloidex.delete())
			System.out.print("El fichero ha sido eliminado correctamente.");
		else
			System.out.print("El fichero no se ha podido eliminar.");

	}

	public static void main(String[] args) {
		boolean exit = false;
		while (!exit) {
			System.out.println("TABLOIDEX - 0.1.0");
			System.out.println("MODO DE JUEGO");
			System.out.println("1. Modo continuo");
			System.out.println("2. Modo progresivo");
			System.out.println("3. Iniciar todas las estadisticas de juego");
			System.out.println("0. Salir de TABLOIDEX");
			Scanner in = new Scanner(System.in);
			byte opcion = in.nextByte();
			switch (opcion) {
			case 0:
				exit = true;
				break;
			case 1:
				modoContinuo(in);
				break;
			case 2:
				modoProgresivo(in);
				break;
			case 3:
				resetScore();
				break;
			case 42:
				pruebaGrafica();
				break;
			case 77:
				System.out.println("El genio de la suerte dice: " + genNumero(10));
				break;
			case 15:
				printTablero(genTablero(9, 3));
				break;
			case 24:
				writeScoreFor((byte) 9, (byte) 0, (byte) 4, (byte) 10);
				break;
			case 25:
				System.out.println("SCORE: " + readScoreFor((byte) 9, (byte) 0, (byte) 4));
				break;
			}
		}

	}

}
