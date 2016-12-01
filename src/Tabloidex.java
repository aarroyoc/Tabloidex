import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

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

	public static void sumaInfinita() {
		Random random = new Random();
		IntStream infinito = random.ints();
		System.out.println("Suma de infinitos aleatorios: " + infinito.sum());
	}

	public static void modoContinuo(Scanner in) {		
		int colors = 2;
		int size = 9;
		boolean exit = false;
		int pasos = 0; // LEER MINIMO DE PASOS DE ARCHIVO
		// TODO GUARDAR MINIMO DE PASOS EN ARCHIVO
		
		System.out.println("NIVEL 1 - 9x9 / 2 colores");
		
		
		while(!exit){
			int[][] tablero = genTablero(size,colors);
			printTablero(tablero);
			while(!esCompleto(tablero)){
				byte selection;
				do{
					System.out.println("Pasos empleados: "+pasos+"\tMínimo de pasos: ");
					System.out.print("Introduce el color: ");
					selection = in.nextByte();
					System.out.println();
				}while(selection == tablero[0][0] || selection < 1 || selection > colors);
				
				pasos++;
				
				// TODO el algoritmo tiene fallos
				int previo = tablero[0][0];
				for(int i =0;i<tablero.length;i++){
					for(int j=0;j<tablero.length && tablero[i][j] == previo;j++){
						tablero[i][j] = selection;
					}
				}
				
				for(int j =0;j<tablero.length;j++){
					for(int i=0;i<tablero.length && tablero[i][j] == previo;i++){
						tablero[i][j] = selection;
					}
				}
				
				printTablero(tablero);
			}
			
			// Mostrar resultados
			System.out.println("MODO CONTINUO");
			switch(size){
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
			System.out.println("Número de colores: "+colors);
			System.out.println("Pasos empleados: "+pasos);
			System.out.println("Menor número de pasos empleados: ");
			
			// Mostrar menú
			while(!exit && pasos != 0){
				System.out.println();
				System.out.println("¿NUEVO TABLERO?");
				System.out.println("0. Volver a menú de MODO DE JUEGO");
				System.out.println("1. Nuevo tablero con estas características");
				System.out.println("2. Cambiar tamaño");
				System.out.println("3. Cambiar número de colores");
				int option;
				do{
					option = in.nextInt();
				}while(option < 0 || option > 3);
				switch(option){
				case 0: 
					exit=true;
					break;
				case 1:
					pasos = 0;
					break;
				case 2:
					do{
						System.out.println("Introdudce el nuevo tamaño del tablero (9, 11 o 15):");
						size = in.nextInt();
					}while(size != 9 && size != 11 && size != 15);
					break; // TODO Cambiar tamaño y salir a menú
				case 3:
					do{
						System.out.println("Introduce el número de colores (entre 2 y 6)");
						colors = in.nextInt();
					}while(colors < 2 || colors > 6);
					break; // TODO Cambiar colores y salir a menú
				
				}
			}
			
			// Cambiar variables según opción y LOOP!!
		}

	}
	
	public static boolean esCompleto(int[][] tablero){
		boolean completo = true;
		int inicial = tablero[0][0];
		for(int i =0;i<tablero.length && completo;i++){
			for(int j = 0;j<tablero.length && completo;j++){
				if(inicial != tablero[i][j]){
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
		for(byte i=0;i<tablero.length * 2 -1;i++){
			System.out.print((i % 2 == 0) ? "\u2501" : "\u2533");
		}
		System.out.println("\u2513");
		
		// imprimir 
		for (byte i = 0; i < tablero.length; i++) {
			// Números
			
			System.out.print("\u2503");
			for (byte j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]);
				System.out.print("\u2503");
			}
			System.out.println("");
			
			// Intercaja
			if(i != tablero.length -1){
				System.out.print("\u2523");
				for(byte z = 0; z < tablero.length * 2 -1;z++){
					System.out.print((z % 2 == 0) ? "\u2501" : "\u254b");
				}
				System.out.println("\u252b");
			}
		}
		
		// parte abajo
		System.out.print("\u2517");
		for(byte i=0; i<tablero.length * 2 -1;i++){
			System.out.print((i%2 == 0) ? "\u2501" : "\u253b");
		}
		System.out.println("\u251b");
		
		if(ventana!=null){
			ventana.setMatrix(tablero);
		}
		
	}

	public static void main(String[] args) {
		boolean exit = false;
		while(!exit){
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
			case 12:
				sumaInfinita();
				break;
			case 15:
				printTablero(genTablero(9,3));
				break;
			}
		}

	}

}
