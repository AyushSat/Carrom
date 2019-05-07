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
		striker = new Striker(200,200,30);
		
	}
	
	public void settings() {
		size(1000,1000);
	}
	public void setup() {
	}
	public void draw() {
		background(255);

		striker.collide(testPiece);
		testPiece.move(this);
		striker.move(this);
		striker.draw(this);
		testPiece.draw(this);
		
	}
	public void keyPressed() {
		if(keyCode==37) { //left
			striker.setVelX(striker.getVelX()-10);
		}
		if(keyCode==38) { //up
			striker.setVelY(striker.getVelY()-10);
		}
		if(keyCode==39) { //right
			striker.setVelX(striker.getVelX()+10);
		}
		if(keyCode==40) { //down
			striker.setVelY(striker.getVelY()+10);
		}
		if(keyCode==65) { //a
			testPiece.setVelX(testPiece.getVelX()-10);
		}
		if (keyCode == 87) { //w
			testPiece.setVelY(testPiece.getVelY()-10);
		}
		if (keyCode == 83) { //s
			testPiece.setVelY(testPiece.getVelY()+10);
		}
		if (keyCode == 68) { //d
			testPiece.setVelX(testPiece.getVelX()+10);
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
