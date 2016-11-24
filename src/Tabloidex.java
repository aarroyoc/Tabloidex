import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Tabloidex {
	
	public static void pruebaGrafica(){
		Ventana ventana = new Ventana();
//buenos dias
		
	}
	
	public static int genNumero(int max){
		// Genera u{n número aleatorio entre el 1 y el máximo
		Random random = new Random();
		int rand = 1 + random.nextInt(max);
		return rand;
	}
	
	public static void sumaInfinita(){
		Random random = new Random();
		IntStream infinito = random.ints();
		System.out.println("Suma de infinitos aleatorios: "+infinito.sum());
	}
	
	public static void modoContinuo(Scanner in){
		System.out.print("NIVEL 1 - Rango pequeño");
		
		int max = 10;
		int numeroPensado = genNumero(max);
		int x = -1;
		int intentos = 0;
		do{
			System.out.printf("\nAdivina entre 1 y %d (llevas %d intentos):",max,intentos);
			intentos++;
			x = in.nextInt(); // TO,DO COMPROBAR SI EL NUMERO ESTA EN EL RANGO
		}while(x != numeroPensado);
		System.out.println("MODO CONTINUO");
		System.out.println("Rango: PEQUEÑO (1 a 10)");
		System.out.println("Pasos empleados: "+intentos);
		
	}
	
	public static void modoProgresivo(){
		
	}
	
	public static int[][] genTablero(int n){
		Random random = new Random();
		int[][] tablero = new int[n][];
		for(byte i=0;i<n;i++){
			tablero[i] = new int[n];
			for(byte j=0;j<n;j++){
				tablero[i][j] = 1 + random.nextInt(3);
			}
		}
		return tablero;
	}
	
	public static void printTablero(int[][] tablero){
		for(byte i=0;i<tablero.length;i++){
			for(byte j=0;j<tablero[i].length;j++){
				System.out.print(tablero[i][j]);
				// añadir caja System.out.print("\u2502");
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		System.out.println("TABLOIDEX - 0.1.0");
		System.out.println("MODO DE JUEGO");
		System.out.println("1. Modo continuo");
		System.out.println("2. Modo progresivo");
		System.out.println("0. Salir de TABLOIDEX");
		Scanner in = new Scanner(System.in);
		byte opcion = in.nextByte();
		switch(opcion){
		case 1: modoContinuo(in);break;
		case 2: modoProgresivo();break;
		case 42: pruebaGrafica(); break;
		case 77: System.out.println("El genio de la suerte dice: "+genNumero(10));break;
		case 12: sumaInfinita(); break;
		case 15: printTablero(genTablero(9));break;
		}
		
	}

}
