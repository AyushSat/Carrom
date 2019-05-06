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
	private Striker striker;
	public Tester() {
		testPiece = new GenericGamePiece(500,500,30,10);
		striker = new Striker(200,200,10);
		
	}
	
	public void settings() {
		size(1000,1000);
	}
	public void setup() {
	}
	public void draw() {
		background(255);
		testPiece.move(this);
		testPiece.draw(this);
		fill(0);
		text("" + testPiece.getVelX() + ", " + testPiece.getVelY(),500,500);
	}
	public void keyPressed() {
		if(keyCode==37) { //left
			testPiece.setVelX(testPiece.getVelX()-10);
		}
		if(keyCode==38) { //up
			testPiece.setVelY(testPiece.getVelY()-10);
		}
		if(keyCode==39) { //right
			testPiece.setVelX(testPiece.getVelX()+10);
		}
		if(keyCode==40) { //down
			testPiece.setVelY(testPiece.getVelY()+10);
		}
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
