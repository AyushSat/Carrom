import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/**
 * PApplet for testing purposes
 * 
 * @author Akshat, Calix
 * @version 5/6/19
 */
public class GameBoard extends PApplet {

	
	/**The radius to be used by a normal piece if the with and height are 1000.
	 * 
	 */
	public static final float GenericGamePiece_RADIUS = 14.5f;
	/**The border width of the board if the with and height are 1000.
	 * 
	 */
	public static final float BORDER_WIDTH = 28;
	/**The movement increment for the striker if the with and height are 1000.
	 * 
	 */
	public static final float MOVEMENT_INCREMENT = 10;
	/**Point value of white pieces
	 * 
	 */
	public static final int WHITE_VALUE = 20;
	/**Point value of black pieces
	 * 
	 */
	public static final int BLACK_VALUE = 10;
	/**Point value of the queen piece.
	 * 
	 */
	public static final int QUEEN_VALUE = 50;
	
	private ArrayList<GenericGamePiece> pieces;
	private ArrayList<Player> players;
	private int amtPlayers;
	private Striker striker;
	private PImage board;
	private PImage black;
	private PImage red;
	private PImage white;
	private PImage s;
	private int turnStreak;
	private boolean chainTurn;
	private int turnPhase;
	private int playerTurn;

	public GameBoard(int blacks, int whites) {
		turnStreak = 0;
		chainTurn = false;
		amtPlayers = 0;
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

		striker = new Striker(0, 0, GenericGamePiece_RADIUS * 4 / 3, 255, 255, 255);
	}

	public GameBoard(int blacks, int whites, int amtPlayers) {
		turnStreak = 0;
		chainTurn = false;
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
		this.amtPlayers = amtPlayers;
		striker = new Striker(0, 0, GenericGamePiece_RADIUS * 4 / 3, 255, 255, 255);
	}

	public void settings() {
		size(1000, 1000);
	}

	public void setup() {
		frameRate(120);
		double x = width / 2;
		double y = height / 2;

		pieces.get(0).setLoc(x, y);
		pieces.get(1).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 + GenericGamePiece_RADIUS * 2);
		pieces.get(2).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2);
		pieces.get(3).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2 * 2, y);
		pieces.get(4).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * 2 - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2);
		pieces.get(5).setLoc(x, y - GenericGamePiece_RADIUS * 2);
		pieces.get(6).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * 2 - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2);
		pieces.get(7).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2 * 2, y);
		pieces.get(8).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2);
		pieces.get(9).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 + GenericGamePiece_RADIUS * 2);

		pieces.get(10).setLoc(x, y + GenericGamePiece_RADIUS * 4);
		pieces.get(11).setLoc(x, y + GenericGamePiece_RADIUS * 2);
		pieces.get(12).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4);
		pieces.get(13).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2);
		pieces.get(14).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4);
		pieces.get(15).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2);
		pieces.get(16).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4);
		pieces.get(17).setLoc(x, y - GenericGamePiece_RADIUS * 4);
		pieces.get(18).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4);
		for (GenericGamePiece p : pieces) {
			p.setInitLoc(x, y);
		}
		if (amtPlayers <= 2) {
			players.add(new Player(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
					height * .245, 11 * this.width / 25, 2 * striker.getRadius())));
			players.add(new Player(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
					height * .717, 11 * this.width / 25, 2 * striker.getRadius())));
		} else if (amtPlayers == 3) {
			players.add(new Player(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
					height * .245, 11 * this.width / 25, 2 * striker.getRadius())));
			players.add(new Player(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
					height * .717, 11 * this.width / 25, 2 * striker.getRadius())));
			players.add(new Player(striker, new Rectangle2D.Double(.245 * this.width,
					height / 1000 * 245 + 2 * striker.getRadius(), 2 * striker.getRadius(), 11 * this.height / 25)));
		} else if (amtPlayers >= 4) {
			players.add(new Player(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
					height * .245, 11 * this.width / 25, 2 * striker.getRadius())));
			players.add(new Player(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
					height * .717, 11 * this.width / 25, 2 * striker.getRadius())));
			players.add(new Player(striker, new Rectangle2D.Double(.245 * this.width,
					height / 1000 * 245 + 2 * striker.getRadius(), 2 * striker.getRadius(), 11 * this.height / 25)));
			players.add(new Player(striker, new Rectangle2D.Double(.716 * this.width,
					height / 1000 * 245 + 2 * striker.getRadius(), 2 * striker.getRadius(), 11 * this.height / 25)));
		}

		striker.setLoc(players.get(0).getHitarea().getX() + players.get(0).getHitarea().getWidth() / 2,
				players.get(0).getHitarea().getY() + players.get(0).getHitarea().getHeight() / 2);
		board = loadImage("data" + File.separator + "board.png");
		black = loadImage("data" + File.separator + "black.png");
		white = loadImage("data" + File.separator + "white.png");
		red = loadImage("data" + File.separator + "red.png");
		s = loadImage("data" + File.separator + "striker.png");
	}

	public void draw() {
		background(255);
		Player player = players.get(playerTurn);
		if (turnPhase != 3) {
			imageMode(CENTER);
			image(board, width / 2, height / 2, width * 0.75f, height * 0.75f);
			textSize(width * .02f);
			fill(0, 0, 255);
			for (int i = 0; i < players.size(); i++) {
				text("Player " + (i + 1),
						width / 2 + 1.8f * ((float) players.get(i).getHitarea().getCenterX() - width / 2),
						height / 2 + 1.8f * ((float) players.get(i).getHitarea().getCenterY() - height / 2));
			}
			fill(0);
		}

		if (turnPhase == 0) {
			chainTurn = false;
			// player.draw(this,s);
			striker.draw(this, s);
			for (GenericGamePiece p : pieces) {
				for (GenericGamePiece q : pieces) {
					p.unCollide(q);
				}
				if (p.getValue() == 10)
					p.draw(this, black);
				else if (p.getValue() == 20)
					p.draw(this, white);
				else
					p.draw(this, red);
			}
		} else if (turnPhase == 1) {
			// players.get(0).draw(this);
			striker.draw(this, s);
			for (GenericGamePiece p : pieces) {
				if (p.getValue() == 10)
					p.draw(this, black);
				else if (p.getValue() == 20)
					p.draw(this, white);
				else
					p.draw(this, red);
			}
			double velX = striker.getX() - mouseX;
			double velY = striker.getY() - mouseY;
			if (Math.pow(velX, 2) + Math.pow(velY, 2) > 9 * width / 10) {
				velX *= 3 * width / 100 / Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
				velY *= 3 * width / 100 / Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
			}
			
			striker.setVelX(velX);
			striker.setVelY(velY);
			pushStyle();
			strokeWeight(4);
			stroke(255);
			line((float) striker.getX(), (float) striker.getY(), (float) (striker.getX() + 2 * velX),
					(float) (striker.getY() + 2 * velY));
			popStyle();
		} else if (turnPhase == 2) {
			for (int i = 0; i < pieces.size(); i++) {
				GenericGamePiece p = pieces.get(i);
				striker.collide(p, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
						7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
			}
			striker.move(this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
					7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
			striker.draw(this, s);
			int totalScoreForTurn = 0;
			for (int i = 0; i < pieces.size(); i++) {
				GenericGamePiece p = pieces.get(i);
				// p.draw(this);
				int pScore = p.score(this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
						7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH,
						4 / 3 * GenericGamePiece_RADIUS);
				if (pScore > 0) {
					totalScoreForTurn += pScore;
					player.addCoin(p);
					pieces.remove(p);
					i--;
				}
			}
			if (totalScoreForTurn > 0) {
				chainTurn = true;
			}
			int sScore = striker.score(this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
					7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH,
					4 / 3 * GenericGamePiece_RADIUS);
			if (sScore == -1) {
				GenericGamePiece pi = player.removeCoin();
				if (pi != null) {
					pieces.add(pi);
					pi.setLoc(pi.getInitialX(), pi.getInitialY());
				}
				for (GenericGamePiece p : pieces) {
					p.setVelX(0);
					p.setVelY(0);
				}
				striker.setVelX(0);
				striker.setVelY(0);

				if (pi != null) {
					striker.collide(pi, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
							7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
					for (GenericGamePiece p : pieces) {
						pi.collide(p, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
								7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
					}
				}
			}

			for (GenericGamePiece p : pieces) {
				for (GenericGamePiece q : pieces) {
					p.collide(q, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
							7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
				}

			}
			for (GenericGamePiece p : pieces) {
				p.collide(striker, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
						7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
				p.move(this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH, 7 * this.width / 8 - BORDER_WIDTH,
						7 * this.height / 8 - BORDER_WIDTH);
				if (p.getValue() == 10)
					p.draw(this, black);
				else if (p.getValue() == 20)
					p.draw(this, white);
				else
					p.draw(this, red);
			}

			boolean stop = true;
			for (GenericGamePiece p : pieces) {
				if (p.isMoving()) {
					stop = false;
				}
			}
			if (striker.isMoving()) {
				stop = false;
			}
			if (stop) {
				turnPhase = 0;
				if (player.getLastPiece() != null && player.getLastPiece().getValue() ==  QUEEN_VALUE
						&& turnStreak > 0) {// this means player has not sunk anything and the queen was sunk last turn.
					GenericGamePiece pi = player.removeCoin(); // must be the queen :)
					if (pi != null) {
						pieces.add(pi);
						pi.setLoc(pi.getInitialX(), pi.getInitialY());
						// striker.collide(pi,this.width/8+BORDER_WIDTH,this.height/8+BORDER_WIDTH,7*this.width/8-BORDER_WIDTH,7*this.height/8-BORDER_WIDTH);
						for (GenericGamePiece p : pieces) {
							pi.collide(p, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
									7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
						}
					}
				}
				if (!chainTurn) {
					playerTurn = (playerTurn + 1) % players.size();
					turnStreak = 0;
				} else {
					turnStreak++;
				}
				player = players.get(playerTurn);
				striker.setLoc(player.getHitarea().getX() + player.getHitarea().getWidth() / 2,
						player.getHitarea().getY() + player.getHitarea().getHeight() / 2);
				if (pieces.isEmpty()) {
					turnPhase = 3;
				}
			}
		} else if (turnPhase == 3) {
			int winner = 0;
			int maxScore = 0;
			boolean draw = false;
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getScore() > maxScore) {
					winner = i;
					maxScore = players.get(i).getScore();
				}
			}
			for (int i = 0; i < players.size(); i++) {
				if (i != winner && players.get(i).getScore() == maxScore) {
					draw = true;
				}
			}
			textSize(50);
			if (draw) {
				text("Draw!", width / 2, height * 3 / 4);
			} else {
				text("Player " + winner + 1 + " wins!", width / 2, height * 3 / 4);
			}
			// text("Player 1 score: " + players.get(0).getScore(),width/4,height*3/4);
			// text("Player 2 score: " + players.get(1).getScore(),width*3/4,height*3/4);
		}

		textSize(width * .03f - 5 * (players.size() - 2));
		fill(0);
		textAlign(CENTER, CENTER);
		for (int i = 0; i < players.size(); i++) {
			text("Player " + (i + 1) + " score: " + players.get(i).getScore(),
					(i + 1.0f) / (players.size() + 1) * width, height / 30);
			for (int j = 0; j < players.get(i).getPieces().size(); j++) {
				GenericGamePiece p = players.get(i).getPieces().get(j);
				if (Math.abs(players.get(i).getHitarea().getCenterX() - width / 2) <= width / 10) {
					p.setLoc(
							width / 2 - GenericGamePiece_RADIUS * 4
									* ((players.get(i).getPieces().size() + 1) / 2.0f - (j + 1)),
							height / 2 + 1.7f * (players.get(i).getHitarea().getCenterY() - height / 2));
				} else {
					p.setLoc(width / 2 + 1.7f * (players.get(i).getHitarea().getCenterX() - width / 2), height / 2
							- GenericGamePiece_RADIUS * 4 * ((players.get(i).getPieces().size() + 1) / 2.0f - (j + 1)));
				}
				if (p.getValue() == 10)
					p.draw(this, black);
				else if (p.getValue() == 20)
					p.draw(this, white);
				else
					p.draw(this, red);

			}
		}
	}

	public void mouseDragged() {
		// striker.setLoc(mouseX, mouseY);
	}

	public void mousePressed() {
		if (turnPhase == 1) {
			turnPhase = 2;
		}
		/*
		if (turnPhase == 0) {
			striker.setLoc(mouseX, mouseY, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
					7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
		}
		*/
	}

	public void keyPressed() {
		if (turnPhase == 0) {
			boolean colliding = false;
			Player player = players.get(playerTurn);
			Rectangle2D.Double bounds = player.getHitarea();
			if (keyCode == 37) { // left arrow

				for (GenericGamePiece p : pieces) {
					if (striker.isColliding(p)) {
						striker.setLoc(p.getX() - striker.getRadius() - p.getRadius(), striker.getY(), bounds.getMinX(),
								bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						colliding = true;
					}
				}
				if (!colliding) {
					striker.setLoc(striker.getX() - MOVEMENT_INCREMENT, striker.getY(), bounds.getMinX(),
							bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
				}
			}
			if (keyCode == 39) { // right arrow
				for (GenericGamePiece p : pieces) {
					if (striker.isColliding(p)) {
						striker.setLoc(p.getX() + striker.getRadius() + p.getRadius(), striker.getY(), bounds.getMinX(),
								bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						colliding = true;
					}
				}
				if (!colliding) {
					striker.setLoc(striker.getX() + MOVEMENT_INCREMENT, striker.getY(), bounds.getMinX(),
							bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
				}
			}
			if (keyCode == 38) { // up arrow
				for (GenericGamePiece p : pieces) {
					if (striker.isColliding(p)) {
						striker.setLoc(striker.getX(), p.getY() - striker.getRadius() - p.getRadius(), bounds.getMinX(),
								bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						colliding = true;
					}
				}
				if (!colliding) {
					striker.setLoc(striker.getX(), striker.getY() - MOVEMENT_INCREMENT, bounds.getMinX(),
							bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
				}
			}
			if (keyCode == 40) { // down arrow
				for (GenericGamePiece p : pieces) {
					if (striker.isColliding(p)) {
						striker.setLoc(striker.getX(), p.getY() + striker.getRadius() + p.getRadius(), bounds.getMinX(),
								bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						colliding = true;
					}
				}
				if (!colliding) {
					striker.setLoc(striker.getX(), striker.getY() + MOVEMENT_INCREMENT, bounds.getMinX(),
							bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
				}
			}
			if (keyCode == 10) {
				turnPhase = 1;
			}
		}
		if (keyCode == 8 && turnPhase == 1) {
			turnPhase = 0;
		}
	}

	public void exit() {
		this.dispose();
	}

}
