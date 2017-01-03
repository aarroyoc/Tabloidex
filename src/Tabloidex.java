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
		// Genera u{n número aleatorio entre el 1 y el máximo
		Random random = new Random();
		int rand = 1 + random.nextInt(max);
		return rand;
	}

	public static void modoContinuo(Scanner in) {
		int colors = 2;
		int size = 9;
		boolean exit = false;
		int pasos = 0; // LEER MINIMO DE PASOS DE ARCHIVO
		byte maxScore = readScoreFor((byte) size, (byte) 0, (byte) colors);

		System.out.println("NIVEL 1 - 9x9 / 2 colores");

		while (!exit) {
			// int[][] tablero =
			// {{1,1,2,2,1,3},{3,2,2,3,2,3},{3,3,1,2,1,2},{1,2,2,3,1,2},{3,2,3,3,2,3},{2,2,3,2,2,3}};
			// MATRICES TRICKY
			/*
			 * int[][] tablero = { {1,1,1,1,1,1,1,1,1}, {1,2,2,2,2,2,2,2,2},
			 * {1,2,2,2,2,2,2,2,2}, {1,2,2,2,1,1,1,1,1}, {1,1,1,2,2,2,2,2,1},
			 * {2,2,1,2,2,2,2,2,1}, {2,2,1,2,2,2,2,2,1}, {2,2,1,1,1,1,1,1,1},
			 * {2,2,2,2,2,2,2,2,2}};
			 */
			maxScore = readScoreFor((byte) size, (byte) 0, (byte) colors);
			int[][] tablero = genTablero(size, colors);
			setupTablero(tablero);
			printTablero(tablero);
			while (!esCompleto(tablero)) {
				byte selection;
				do {
					System.out.println("Pasos empleados: " + pasos + "\tMínimo de pasos: " + maxScore);
					System.out.print("Introduce el color: ");
					selection = in.nextByte();
					System.out.println();
				} while (selection == tablero[0][0] % 10 || selection < 1 || selection > colors);

				pasos++;

				// PARA SABER SI ALGO ESTA SELECCIONADO
				// - MATRIZ INDEPENDIENTE BOOLEANA
				// - OBJETO CELDA
				// - TERCERA DIMENSION
				// - MODULO 10 (printTablero y Tablero)
				// - Caminos con Dijsktra

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

				// printTablero
				printTablero(tablero);
			}

			// Mostrar resultados
			System.out.println("MODO CONTINUO");
			switch (size) {
			case 9:
				System.out.println("Tamaño de tablero: PEQUEÑO (9x9)");
				break;
			case 11:
				System.out.println("Tamaño de tablero: MEDIANO (11x11)");
				break;
			case 15:
				System.out.println("Tamaño de tablero: GRANDE (15x15");
				break;
			}
			System.out.println("Número de colores: " + colors);
			System.out.println("Pasos empleados: " + pasos);

			if (maxScore > pasos || maxScore == 0) {
				writeScoreFor((byte) size, (byte) 0, (byte) colors, (byte) pasos);
				System.out.println("Menor número de pasos empleados: " + pasos);
			} else {
				System.out.println("Menor número de pasos empleados: " + maxScore);
			}

			// Mostrar menú
			while (!exit && pasos != 0) {
				System.out.println();
				System.out.println("¿NUEVO TABLERO?");
				System.out.println("0. Volver a menú de MODO DE JUEGO");
				System.out.println("1. Nuevo tablero con estas características");
				System.out.println("2. Cambiar tamaño");
				System.out.println("3. Cambiar número de colores");
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
						System.out.println("Introdudce el nuevo tamaño del tablero (9, 11 o 15):");
						size = in.nextInt();
					} while (size != 9 && size != 11 && size != 15);
					break;
				case 3:
					do {
						System.out.println("Introduce el número de colores (entre 2 y 6)");
						colors = in.nextInt();
					} while (colors < 2 || colors > 6);
					break;

				}
			}

			// Cambiar variables según opción y LOOP!!
		}

	}

	public static void contagiar(int[][] tablero) {
		// ESTA BIEN PERO HAY QUE IR REPITIENDO VARIAS VECES. EL MAXIMO SERIA EL
		// ANCHO DEL TABLERO (Size)
		for (int q = 0; q < tablero.length; q++) {

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

	public static void modoProgresivo() {

	}

	public static int[][] genTablero(int size, int colors) {
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

		// parte arriba
		System.out.print("\u250f");
		for (byte i = 0; i < tablero.length * 2 - 1; i++) {
			System.out.print((i % 2 == 0) ? "\u2501" : "\u2533");
		}
		System.out.println("\u2513");

		// imprimir
		for (byte i = 0; i < tablero.length; i++) {
			// Números

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
		// | SIZE 4 BITS| MODE 1 BIT| COLOR 3 BITS| SCORE 8 BITS |
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
			// System.out.println("Excepci�n: "+e.getMessage());
		}

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
		}catch (Exception e) {
			// System.out.println(e.getMessage());
		}
		return score;
	}

	public static void main(String[] args) {
		boolean exit = false;
		while (!exit) {
			System.out.println("TABLOIDEX - 0.1.0");
			System.out.println("MODO DE JUEGO");
			System.out.println("1. Modo continuo");
			System.out.println("2. Modo progresivo");
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
				modoProgresivo();
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
