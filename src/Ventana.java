import javax.swing.JFrame;
import javax.swing.UIManager;

public class Ventana extends JFrame {
	
	private Tablero tablero;
	
	public Ventana(){
		this.tablero = new Tablero();
		this.setUndecorated(false);
		this.setTitle("TABLOIDEX");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100,100,400 + 100,400 + 100);
		this.getContentPane().add(tablero);
		this.setVisible(true);
	}
	
	public void setMatrix(int[][] m){
		tablero.setMatrix(m);
	}
}
