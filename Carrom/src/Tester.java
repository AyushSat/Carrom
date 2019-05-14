import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/**
 * PApplet for testing purposes
 * 
 * @author Calix
 * @version 5/6/19
 */
public class Tester extends PApplet {

	private ArrayList<GenericGamePiece> pieces;
	private ArrayList<Player> players;
	private Striker striker;
	private PImage board;
	private int turnPhase;
	private int playerTurn;

	public static final float GenericGamePiece_RADIUS = 14.5f;
	public static final float BORDER_WIDTH = 28;
	public static final float MOVEMENT_INCREMENT = 10;
	
	public Tester(int blacks, int whites) {
		playerTurn = 0;
		turnPhase = 0;
		pieces = new ArrayList<GenericGamePiece>();
		GenericGamePiece queen = new GenericGamePiece(0, 0, GenericGamePiece_RADIUS, 50);
		queen.setColor(255, 0, 0);
		pieces.add(queen);
		for (int i = 0; i < blacks; i++) {
			GenericGamePiece black = new GenericGamePiece(0, 0, GenericGamePiece_RADIUS, 10);
			black.setColor(0, 0, 0);
			pieces.add(black);
		}
		for (int i = 0; i < whites; i++) {
			GenericGamePiece white = new GenericGamePiece(0, 0, GenericGamePiece_RADIUS, 20);
			white.setColor(255, 220, 150);
			pieces.add(white);
		}
		players = new ArrayList<Player>();
		
		striker = new Striker(0, 0, GenericGamePiece_RADIUS*4/3,255,255,255);
	}

	public void settings() {
		size(1000, 1000);
	}

	public void setup() {
		frameRate(240);
		double x = width / 2;
		double y = height / 2;

		pieces.get(0).setLoc(x, y);
		pieces.get(1).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y + GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2 + GenericGamePiece_RADIUS * 2);
		pieces.get(2).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y + GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(3).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2 * 2, y);
		pieces.get(4).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y - GenericGamePiece_RADIUS * 2 - GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(5).setLoc(x, y - GenericGamePiece_RADIUS * 2);
		pieces.get(6).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y - GenericGamePiece_RADIUS * 2 - GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(7).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2 * 2, y);
		pieces.get(8).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y + GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(9).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y + GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2 + GenericGamePiece_RADIUS * 2);
		
		pieces.get(10).setLoc(x, y + GenericGamePiece_RADIUS * 4);
		pieces.get(11).setLoc(x, y + GenericGamePiece_RADIUS * 2);
		pieces.get(12).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 4, y - GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 4);
		pieces.get(13).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y - GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(14).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 4, y - GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 4);
		pieces.get(15).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 2, y - GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 2);
		pieces.get(16).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 4, y + GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 4);
		pieces.get(17).setLoc(x, y - GenericGamePiece_RADIUS * 4);
		pieces.get(18).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI/3) * 4, y + GenericGamePiece_RADIUS * Math.cos(Math.PI/3) * 4);	
		
		striker.setLoc(width/2, height/4 * 3 - 13);
		players.add(new Player(striker,new Rectangle2D.Double(3*this.width/10-striker.getRadius(),height/4 + 13 - striker.getRadius(),11*this.width/25,2*striker.getRadius())));
		players.add(new Player(striker,new Rectangle2D.Double(3*this.width/10-striker.getRadius(),height/4 * 3 - 13 + striker.getRadius(),11 *this.width/25,2*striker.getRadius())));
		board = loadImage("board.png");
	}

	public void draw() {
		Player player = players.get(playerTurn);
		background(255);	
		imageMode(CENTER);
		image(board, width/2, height/2, width * 0.75f, height * 0.75f);
		
		if(turnPhase==0) {
			player.draw(this);
			for(GenericGamePiece p : pieces) {
				p.draw(this);
			}
		}else if(turnPhase==1) {
			players.get(0).draw(this);
			for(GenericGamePiece p : pieces) {
				p.draw(this);
			}
			double velX = striker.getX()-mouseX;
			double velY = striker.getY()-mouseY;
			if(Math.pow(velX, 2)+Math.pow(velY, 2) > 9*width/10) {
				velX *= 3*width/100/Math.sqrt(Math.pow(velX, 2)+Math.pow(velY, 2));
				velY *= 3*width/100/Math.sqrt(Math.pow(velX, 2)+Math.pow(velY, 2));
			}
				
			striker.setVelX(velX);
			striker.setVelY(velY);
			pushStyle();
			strokeWeight(4);
			stroke(255);
			line((float)striker.getX(),(float)striker.getY(),(float)(striker.getX()+2*velX),(float)(striker.getY()+2*velY));
			popStyle();
		}else if(turnPhase==2) {
			for(int i = 0; i < pieces.size(); i++) {
				GenericGamePiece p = pieces.get(i);
				striker.collide(p,this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
			}
			striker.move(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
			striker.draw(this);
			for(int i = 0; i < pieces.size(); i++) {
				GenericGamePiece p = pieces.get(i);
				//p.draw(this);
				int pScore = p.score(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH,4/3*GenericGamePiece_RADIUS);
				if(pScore > 0) {
					player.addCoin(p);
					pieces.remove(p);
					i--;		
				}
			}
			int sScore = striker.score(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH,4/3*GenericGamePiece_RADIUS);
			if(sScore==-1) {
				for(GenericGamePiece p : pieces) {
					p.setVelX(0);
					p.setVelY(0);
				}
				striker.setVelX(0);
				striker.setVelY(0);
			}
			ArrayList<GenericGamePiece> stationarypieces = new ArrayList<GenericGamePiece>();
			for(int i = 0; i < pieces.size(); i++) {
				if(!pieces.get(i).isMoving()) {
					stationarypieces.add(pieces.remove(i));
					i--;
				}
			}
			//collision check
			for(int i = 0; i < pieces.size();i++) {
				for(int j = i+1; j < pieces.size(); j++) {
					pieces.get(i).collide(pieces.get(j), this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
				}
				for(int k = 0; k < stationarypieces.size();k++) {
					pieces.get(i).collide(stationarypieces.get(k), this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
				}
			}
			for(int i = 0; i < stationarypieces.size();i++) {
				pieces.add(stationarypieces.remove(i));
				i--;
			}
			for(GenericGamePiece p : pieces) {
				p.collide(striker, this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
				p.move(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
				p.draw(this);
			}
			
			boolean stop = true;
			for(GenericGamePiece p : pieces) {
				if(p.isMoving()) {
					stop = false;
				}
			}
			if(striker.isMoving()) {
				stop = false;
			}
			if(stop) {
				striker.setLoc(player.getHitarea().getX()+player.getHitarea().getWidth()/2, player.getHitarea().getY()+player.getHitarea().getHeight()/2);
				turnPhase = 0;
				playerTurn = (playerTurn+1) % players.size();
			}
		}
		
		//testGenericGamePiece.move(this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
		//testGenericGamePiece.draw(this);
		textSize(30);
		fill(0);
		textAlign(CENTER,CENTER);
		text("Player 1 score: " + players.get(0).getScore() + "                      Player 2 score: " + players.get(1).getScore(),width/2,height/10);
	}
	
	public void mouseDragged() {
		//striker.setLoc(mouseX, mouseY);
	}
	public void mousePressed() {
		if(turnPhase==1) {
			turnPhase=2;
		}
		if(turnPhase==0) {
			striker.setLoc(mouseX, mouseY,this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
		}
	}
	public void keyPressed() {
		if(turnPhase==0) {
			Player player = players.get(playerTurn);
			Rectangle2D.Double bounds = player.getHitarea();
			if(keyCode==37) {
				striker.setLoc(striker.getX()-MOVEMENT_INCREMENT, striker.getY(),bounds.getMinX(),bounds.getMinY(),bounds.getMaxX(),bounds.getMaxY());
			}
			if(keyCode==39) {
				striker.setLoc(striker.getX()+MOVEMENT_INCREMENT, striker.getY(),bounds.getMinX(),bounds.getMinY(),bounds.getMaxX(),bounds.getMaxY());
			}
			if(keyCode==38) {
				striker.setLoc(striker.getX(), striker.getY()-MOVEMENT_INCREMENT,bounds.getMinX(),bounds.getMinY(),bounds.getMaxX(),bounds.getMaxY());
			}
			if(keyCode==40) {
				striker.setLoc(striker.getX(),striker.getY()+MOVEMENT_INCREMENT,bounds.getMinX(),bounds.getMinY(),bounds.getMaxX(),bounds.getMaxY());
			}
			if(keyCode==10) {
				turnPhase = 1;
			}
		}
		if(keyCode==8 && turnPhase==1) {
			turnPhase = 0;
		}
		if(keyCode==83) {
			for(GenericGamePiece p : pieces) {
				p.setVelX(0);
				p.setVelY(0);
			}
			striker.setVelX(0);
			striker.setVelY(0);
		}
		//text(keyCode,100,100);
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
