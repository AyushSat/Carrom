import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**Testing class
 * 
 * @author Calix Tang
 *
 */
public class Tester extends PApplet{
	private Piece testPiece;
	public Tester() {
		testPiece = new GenericGamePiece(500,500,30,10);
		
	}
	
	public void settings() {
		size(1000,1000);
	}
	public void setup() {
		testPiece.setVelX(15);
		testPiece.setVelY(15);
	}
	public void draw() {
		background(255);
		testPiece.move();
		testPiece.draw(this);
		fill(0);
		text("" + testPiece.getVelX() + ", " + testPiece.getVelY(),500,500);
	}
	public void keyPressed() {
		
	}
	public void mousePressed() {
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tester tester = new Tester();
		PApplet.runSketch(new String[] {"CarromTester"}, tester);
		PSurfaceAWT surf = (PSurfaceAWT) tester.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();
		
		//window is 1000x1000 permanently
		window.setSize(1000,1000);
		window.setMinimumSize(new Dimension(1000,1000));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);//a stretch is to change this
		
		//make window visible
		window.setVisible(true);
		canvas.requestFocus();
	}

}
