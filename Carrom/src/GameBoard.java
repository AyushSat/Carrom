import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/**
 * PApplet that represents the GameBoard
 * 
 * @author Akshat
 * @version 5/6/19
 */
public class GameBoard extends PApplet {

	private ArrayList<GenericGamePiece> pieces;
	private Striker striker;
	private PImage board;

	private static final float PIECE_RADIUS = 14.5f;

	public GameBoard(int blacks, int whites) {
		
		pieces = new ArrayList<GenericGamePiece>();
		GenericGamePiece queen = new GenericGamePiece(0, 0, PIECE_RADIUS, 50);
		queen.setColor(255, 0, 0);
		pieces.add(queen);
		for (int i = 0; i < blacks; i++) {
			GenericGamePiece black = new GenericGamePiece(0, 0, PIECE_RADIUS, 10);
			black.setColor(0, 0, 0);
			pieces.add(black);

		}
		for (int i = 0; i < whites; i++) {
			GenericGamePiece white = new GenericGamePiece(0, 0, PIECE_RADIUS, 20);
			white.setColor(255, 220, 150);
			pieces.add(white);
		}

		striker = new Striker(0, 0, PIECE_RADIUS*5/3);
		striker.setColor(0, 255, 0);

	}

	public void settings() {
		size(1000, 1000);
	}

	public void setup() {
		double x = width / 2;
		double y = height / 2;

		pieces.get(0).setLoc(x, y);

		pieces.get(1).setLoc(x + PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y + PIECE_RADIUS * Math.cos(Math.PI/3) * 2 + PIECE_RADIUS * 2);
		pieces.get(2).setLoc(x + PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y + PIECE_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(3).setLoc(x + PIECE_RADIUS * Math.sin(Math.PI/3) * 2 * 2, y);
		pieces.get(4).setLoc(x + PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y - PIECE_RADIUS * 2 - PIECE_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(5).setLoc(x, y - PIECE_RADIUS * 2);
		pieces.get(6).setLoc(x - PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y - PIECE_RADIUS * 2 - PIECE_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(7).setLoc(x - PIECE_RADIUS * Math.sin(Math.PI/3) * 2 * 2, y);
		pieces.get(8).setLoc(x - PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y + PIECE_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(9).setLoc(x - PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y + PIECE_RADIUS * Math.cos(Math.PI/3) * 2 + PIECE_RADIUS * 2);
		
		pieces.get(10).setLoc(x, y + PIECE_RADIUS * 4);
		pieces.get(11).setLoc(x, y + PIECE_RADIUS * 2);
		pieces.get(12).setLoc(x + PIECE_RADIUS * Math.sin(Math.PI/3) * 4, y - PIECE_RADIUS * Math.cos(Math.PI/3) * 4);
		pieces.get(13).setLoc(x + PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y - PIECE_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(14).setLoc(x - PIECE_RADIUS * Math.sin(Math.PI/3) * 4, y - PIECE_RADIUS * Math.cos(Math.PI/3) * 4);
		pieces.get(15).setLoc(x - PIECE_RADIUS * Math.sin(Math.PI/3) * 2, y - PIECE_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(16).setLoc(x + PIECE_RADIUS * Math.sin(Math.PI/3) * 4, y + PIECE_RADIUS * Math.cos(Math.PI/3) * 4);
		pieces.get(17).setLoc(x, y - PIECE_RADIUS * 4);
		pieces.get(18).setLoc(x - PIECE_RADIUS * Math.sin(Math.PI/3) * 4, y + PIECE_RADIUS * Math.cos(Math.PI/3) * 4);	
		
		striker.setLoc(width/2, height/4 * 3 - 13);
		
		board = loadImage("boardOther.png");
	}

	public void draw() {
		background(255);
		
		imageMode(CENTER);
		image(board, width/2, height/2, width * 0.75f, height * 0.75f);
		
		for (GenericGamePiece p : pieces)
			p.draw(this);
		
		striker.draw(this);
	}
	
	public void mouseDragged() {
		striker.setLoc(mouseX, mouseY);
	}
}
