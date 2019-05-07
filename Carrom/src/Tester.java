import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/**
 * PApplet that represents the GameBoard
 * 
 * @author Akshat
 * @version 5/6/19
 */
public class Tester extends PApplet {

	private ArrayList<GenericGamePiece> pieces;
	private Striker striker;
	private PImage board;

	private static final float PIECE_RADIUS = 14.5f;
	private static final float BORDER_WIDTH = 28;
	public Tester(int blacks, int whites) {
		
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
		
		striker.move(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
		striker.draw(this);
		
		for(int i = 0; i < pieces.size(); i++) {
			GenericGamePiece p = pieces.get(i);
			striker.collide(p);
			for(int j = i+1; j < pieces.size(); j++) {
				p.move(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
				p.draw(this);
				GenericGamePiece q = pieces.get(j);
				p.collide(q);
				q.move(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
				q.draw(this);
			}		
		}
		
		
	}
	
	public void mouseDragged() {
		//striker.setLoc(mouseX, mouseY);
	}
	public void keyPressed() {
		if(keyCode==37) {
			striker.setVelX(striker.getVelX()-10);
		}
		if(keyCode==38) {
			striker.setVelY(striker.getVelY()-10);
		}
		if(keyCode==39) {
			striker.setVelX(striker.getVelX()+10);
		}
		if(keyCode==40) {
			striker.setVelY(striker.getVelY()+10);
		}
	}
	
	public static void main(String[] args) {
		Tester board = new Tester(9, 9);
		PApplet.runSketch(new String[]{"Carrom"}, board);
		
		PSurfaceAWT surf = (PSurfaceAWT) board.getSurface();
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
