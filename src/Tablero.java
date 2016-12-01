import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;

public class Tablero extends JComponent {
	private int[][] matrix = new int[9][9];
	
	public Tablero(){
		
	}
	public void setMatrix(int[][] m){
		matrix = m;
		this.repaint();
	}
	
	public Color colorFromNumber(int n){
		switch(n){
		case 1: return new Color(255,0,0);
		case 2: return new Color(0,255,0);
		case 3: return new Color(0,0,255);
		default: return new Color(0,0,0);
		}
	}
	
	public void paint(Graphics g){
		int width = this.getWidth() / matrix.length;
		
		for(byte i=0;i<matrix.length;i++){
			for(byte j=0;j<matrix[i].length;j++){
				g.setColor(colorFromNumber(matrix[i][j]));
				g.fillRect(i*width, j*width, width, width);
			}
		}
	}
}
