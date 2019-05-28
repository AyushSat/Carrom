package networkedgame;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkManagementPanel;
import networking.frontend.NetworkMessenger;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;
import main.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * A game board that implements networking
 * 
 * @author Ayush and Calix
 * @version 5/6/19
 */
public class NetworkedGameBoard extends PApplet implements NetworkListener{

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
	private ArrayList<PlayerN> players;
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
	private String serverHost;
	
	private String serverIP;
	
	private int currPlayerIndex;
	
	private static final String PLAYER_MOVE = "PLAYER_MOVE";
	private static final String ADD_PLAYER = "ADD_PLAYER";
	private static final String GET_PLAYERS = "GET_PLAYERS";
	private static final String SWITCH_PLAYER_TURN = "SWITCH_PLAYER_TURN";
	private static final String UPDATE_PLAYERS = "UPDATE_PLAYERS";

	
	private NetworkMessenger nm;
	/**
	 * Initializes a networked gameboard
	 * @param blacks the number of black coins
	 * @param whites the number of white coins
	 */
	public NetworkedGameBoard(int blacks, int whites) {
		playerTurn = 0;
		chainTurn = false;
		turnStreak = 0;
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
		players = new ArrayList<PlayerN>();
		
		striker = new Striker(0, 0, GenericGamePiece_RADIUS*4/3,255,255,255);
		
		try {
			serverHost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	
	public void settings() {
		size(1000, 1000);
	}
	/**
	 * Sets the IPv4 address of the server
	 * @param ip the server's ip
	 */
	public void setServerIP(String ip) {
		this.serverIP = ip;
	}
	

	public void setup() {
		frameRate(240);
		double x = width / 2;
		double y = height / 2;

		
		final int margin = 15;
		pieces.get(0).setLoc(x, y - margin);
		pieces.get(1).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 + GenericGamePiece_RADIUS * 2 - margin);
		pieces.get(2).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 - margin);
		pieces.get(3).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2 * 2, y - margin);
		pieces.get(4).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * 2 - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 - margin);
		pieces.get(5).setLoc(x, y - GenericGamePiece_RADIUS * 2 - margin);
		pieces.get(6).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * 2 - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 - margin);
		pieces.get(7).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2 * 2, y - margin);
		pieces.get(8).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 - margin);
		pieces.get(9).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 + GenericGamePiece_RADIUS * 2 - margin);

		pieces.get(10).setLoc(x, y + GenericGamePiece_RADIUS * 4 - margin);
		pieces.get(11).setLoc(x, y + GenericGamePiece_RADIUS * 2 - margin);
		pieces.get(12).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4 - margin);
		pieces.get(13).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 - margin);
		pieces.get(14).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4 - margin);
		pieces.get(15).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 2,
				y - GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 2 - margin);
		pieces.get(16).setLoc(x + GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4 - margin);
		pieces.get(17).setLoc(x, y - GenericGamePiece_RADIUS * 4 - margin);
		pieces.get(18).setLoc(x - GenericGamePiece_RADIUS * Math.sin(Math.PI / 3) * 4,
				y + GenericGamePiece_RADIUS * Math.cos(Math.PI / 3) * 4 - margin);	
		
		for (GenericGamePiece p : pieces) {
			p.setInitLoc(x, y);
		}
		

		
		
		players.add(new PlayerN(striker,new Rectangle2D.Double(3*this.width/10-striker.getRadius(),height/4 + 13 - striker.getRadius(),11*this.width/25,2*striker.getRadius()), serverHost));
		striker.setLoc(players.get(0).getHitarea().getX() + players.get(0).getHitarea().getWidth() / 2, players.get(0).getHitarea().getY() + players.get(0).getHitarea().getHeight() / 2 - 5);
		currPlayerIndex = 0;
		board = loadImage("data" + File.separator + "board.png");
		black = loadImage("data" + File.separator + "black.png");
		white = loadImage("data" + File.separator + "white.png");
		red = loadImage("data" + File.separator + "red.png");
		s = loadImage("data" + File.separator + "striker.png");

	}
	
	public boolean myTurn(){
		if(playerTurn == getMyIndex()) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getMyIndex(){
		String myIP = "";
		try {
			myIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		for(int i = 0;i<players.size();i++) {
			if(players.get(i).getHost().equals(myIP)){
				return i;
			}
		}
		
		return -1;
		
		

	}

	public void draw() {
		//if(players.size()>1) {
		
		background(255);
		if(!myTurn()) {
			pushStyle();
			textAlign(CENTER, CENTER);
			textSize(25);
			text("Not your turn!", 500,10);
			popStyle();
		}
		PlayerN player = players.get(playerTurn);
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
			// players.get(0).draw(this);0.
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
					turnStreak = 0;
					updateSwitch();
				} else {
					turnStreak++;
				}
				player = players.get(playerTurn);
				striker.setLoc(player.getHitarea().getX() + player.getHitarea().getWidth() / 2,
						player.getHitarea().getY() + player.getHitarea().getHeight() / 2);
				if (pieces.isEmpty()) {
					turnPhase = 3;
				}
				updateBoard();
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
					(i + 1.0f) / (players.size() + 1) * width, height / 30 + 10);
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
			//text("Player 1 score: " + players.get(0).getScore() + "                      Player 2 score: " + players.get(1).getScore(),width/2,height/10);
		if(myTurn() && nm != null && players.size()>1) {
			updateBoard();
			updatePlayer();
		}
			processNetworkMessages();
		//}
		
	}
	
	public void updatePlayer() {
		if(serverHost.equals(serverIP)) {
			nm.sendMessage(NetworkDataObject.MESSAGE, GET_PLAYERS, players);
		}else {
			nm.sendMessage(NetworkDataObject.MESSAGE, UPDATE_PLAYERS, players);

		}
	}
	
	private boolean isServer() {
		// TODO Auto-generated method stub
		if(serverHost.equals(players.get(0).getHost())) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Initializes the messenger that transmits data across the LAN
	 * @param nm the NetworkMessenger to be used with this gameboard
	 */
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
	}
	
	public void processNetworkMessages() {
		// TODO Auto-generated method stub
		if (nm == null)
			return;
		
		Queue<NetworkDataObject> queue = nm.getQueuedMessages();
		
		while (!queue.isEmpty()) {
			NetworkDataObject ndo = queue.poll();

			String host = ndo.getSourceIP();

			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(PLAYER_MOVE)) {
					this.pieces = (ArrayList<GenericGamePiece>) ndo.message[1];
					this.playerTurn = (int) ndo.message[2];
					this.striker = (Striker)ndo.message[3];
				}else if(ndo.message[0].equals(ADD_PLAYER)) {
					//players.add(new Player());
					//if ur hosting the server then add all of the new players 
					if(serverHost.equals(serverIP)) {
						if(players.size()<2) {
							if(players.size() == 1) {
								players.add(new PlayerN(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
										height * .717, 11 * this.width / 25, 2 * striker.getRadius()), host));
							}else if(players.size() == 2) {
								players.add((new PlayerN(striker, new Rectangle2D.Double(3 * this.width / 10 - striker.getRadius(),
										height * .717, 11 * this.width / 25, 2 * striker.getRadius()), host)));
							}else if(players.size() == 3)  {
								players.add(new PlayerN(striker, new Rectangle2D.Double(.716 * this.width,
										height / 1000 * 245 + 2 * striker.getRadius(), 2 * striker.getRadius(), 11 * this.height / 25), host));
							}
						}else {
							JOptionPane.showMessageDialog(null, "Sorry, server is full");
						}
						nm.sendMessage(NetworkDataObject.MESSAGE, GET_PLAYERS, players);
					}
					
				}else if(ndo.message[0].equals(GET_PLAYERS)) {
					players = (ArrayList<PlayerN>) ndo.message[1];
				}else if(ndo.message[0].equals(SWITCH_PLAYER_TURN)) {
					playerTurn = (playerTurn + 1) % players.size();

				}else if(ndo.message[0].equals(UPDATE_PLAYERS)) {
					if(serverHost.equals(serverIP)){
						this.players = (ArrayList<PlayerN>)ndo.message[1];
						nm.sendMessage(NetworkDataObject.MESSAGE, GET_PLAYERS, players);
					}
				}
				
				
				
				//add change player turn variabels to make sure no conflicting data sends
			}else if(ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				nm.sendMessage(NetworkDataObject.MESSAGE, ADD_PLAYER);
			}else if(ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
				if(ndo.dataSource.equals(ndo.serverHost)) {
					System.exit(0);
				}else {
					for(int i = 0;i<players.size();i++) {
						if(players.get(i).host.equals(ndo.getSourceIP())) {
							players.remove(i);
						}
					}
				}
			}
		}
		
		
	}
	
	public void updateBoard() {
		nm.sendMessage(NetworkDataObject.MESSAGE,PLAYER_MOVE, pieces, playerTurn, striker);
		

	}
	
	public void updateSwitch() {
		nm.sendMessage(NetworkDataObject.MESSAGE, SWITCH_PLAYER_TURN);
		playerTurn = (playerTurn + 1) % players.size();
	}

	public void mouseDragged() {
		//striker.setLoc(mouseX, mouseY);
	}
	
	public void mousePressed() {
		
		
		if(myTurn()) {
			if (turnPhase == 1) {
				turnPhase = 2;
			}
		}
		
		/*
		if (turnPhase == 0) {
			striker.setLoc(mouseX, mouseY, this.width / 8 + BORDER_WIDTH, this.height / 8 + BORDER_WIDTH,
					7 * this.width / 8 - BORDER_WIDTH, 7 * this.height / 8 - BORDER_WIDTH);
		}
		*/
	}
	
	public void keyPressed() {
		if(myTurn()) {
			if (turnPhase == 0) {
				PlayerN player = players.get(playerTurn);
				Rectangle2D.Double bounds = player.getHitarea();
				if (keyCode == 37) { // left arrow
					striker.setLoc(striker.getX() - MOVEMENT_INCREMENT, striker.getY(), bounds.getMinX(), bounds.getMinY(),
							bounds.getMaxX(), bounds.getMaxY());
					for (GenericGamePiece p : pieces) {
						if (striker.isColliding(p)) {
							striker.setLoc(p.getX() - striker.getRadius() - p.getRadius(), striker.getY(), bounds.getMinX(),
									bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						}
					}
				}
				if (keyCode == 39) { // right arrow
					striker.setLoc(striker.getX() + MOVEMENT_INCREMENT, striker.getY(), bounds.getMinX(), bounds.getMinY(),
							bounds.getMaxX(), bounds.getMaxY());
					for (GenericGamePiece p : pieces) {
						if (striker.isColliding(p)) {
							striker.setLoc(p.getX() + striker.getRadius() + p.getRadius(), striker.getY(), bounds.getMinX(),
									bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						}
					}
				}
				if (keyCode == 38) { // up arrow
					striker.setLoc(striker.getX(), striker.getY() - MOVEMENT_INCREMENT, bounds.getMinX(), bounds.getMinY(),
							bounds.getMaxX(), bounds.getMaxY());
					for (GenericGamePiece p : pieces) {
						if (striker.isColliding(p)) {
							striker.setLoc(striker.getX(), p.getY() - striker.getRadius() - p.getRadius(), bounds.getMinX(),
									bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						}
					}
				}
				if (keyCode == 40) { // down arrow
					striker.setLoc(striker.getX(), striker.getY() + MOVEMENT_INCREMENT, bounds.getMinX(), bounds.getMinY(),
							bounds.getMaxX(), bounds.getMaxY());
					for (GenericGamePiece p : pieces) {
						if (striker.isColliding(p)) {
							striker.setLoc(striker.getX(), p.getY() + striker.getRadius() + p.getRadius(), bounds.getMinX(),
									bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
						}
					}
				}
				if (keyCode == 10) {
					turnPhase = 1;
				}
			}
			if (keyCode == 8 && turnPhase == 1) {
				turnPhase = 0;
			}
			if (keyCode == 83) {
				for (GenericGamePiece p : pieces) {
					p.setVelX(0);
					p.setVelY(0);
				}
				striker.setVelX(0);
				striker.setVelY(0);
			}
		}
			
		
		
	}
	
	/**
	 * disposes of the window
	 */
	public void exit() {
		this.dispose();
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		// TODO Auto-generated method stub
		
	}
}
