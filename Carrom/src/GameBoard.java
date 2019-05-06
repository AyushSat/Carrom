import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/** PApplet that represents the GameBoard
 * 
 * @author Akshat
 * @version 5/6/19
 */
public class GameBoard extends PApplet{
	
	private ArrayList<GenericGamePiece> pieces;
	private Striker striker;
	private PImage board;
	
	
	public GameBoard(int blacks, int whites) {
		pieces = new ArrayList<GenericGamePiece>();
		for(int i = 0; i < blacks; i++) {
			GenericGamePiece black = new GenericGamePiece(0, 0, 10, 10);
			black.setColor(0, 0, 0);
			pieces.add(black);
			
		}
		for(int i = 0; i < whites; i++) {
			GenericGamePiece white = new GenericGamePiece(0, 0, 10, 20);
			white.setColor(255, 255, 255);
			pieces.add(white);
		}
		GenericGamePiece queen = new GenericGamePiece(0, 0, 10, 50);
		queen.setColor(255, 0, 0);
		pieces.add(queen);
		
		striker = new Striker(0, 0, 10);
		striker.setColor(0, 255, 0);
	}
	
	public void setup() {
		
	}
	
	public void draw() {
		
	}
	
	public void mousePressed() {

	}

	public void mouseDragged() {

	}

	public void mouseWheel(MouseEvent event) {
	}

	public void keyPressed() {
	}
}
