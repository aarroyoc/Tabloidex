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
		case 1: return new Color(0,153,0);
		case 2: return new Color(51,0,51);
		case 3: return new Color(51,255,204);
		case 4: return new Color(153,51,0);
		case 5: return new Color(204,0,0);
		case 6: return new Color(204,153,0);
		default: return new Color(0,0,0);
		}
	}
	
	public void paint(Graphics g){
		int width = this.getWidth() / matrix.length;
		
		for(byte i=0;i<matrix.length;i++){
			for(byte j=0;j<matrix[i].length;j++){
				g.setColor(colorFromNumber(matrix[i][j] % 10));
				g.fillRect(j*width, i*width, width, width);
			}
		}
	}
}
