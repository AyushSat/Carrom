import java.awt.geom.Rectangle2D;
import java.io.File;
import processing.core.PImage;
import java.util.ArrayList;
import processing.core.PApplet;
/**This class is a PApplet window that represents a tutorial screen. 
 * It might be impolemented to teach players the mechanics and controls of Carrom.
 * 
 * @author calix
 *
 */
public class Tutorial extends PApplet{
	private ArrayList<GenericGamePiece> pieces;
	private float width;
	private float height;
	private Striker striker;
	private PImage s;
	private PImage white;
	private PImage black;
	private PImage queen;
	private PImage background;
	private int turnPhase;
	private int progress;
	
	/**Makes a tutorial with the given width and height
	 * 
	 * @param width width of screen
	 * @param height height of screen
	 */
	public Tutorial(float width, float height) {
		progress = 0;
		this.width = width;
		this.height = height;
		turnPhase = 0;
		pieces = new ArrayList<GenericGamePiece>();
		pieces.add(new GenericGamePiece(0,0,Tester.GenericGamePiece_RADIUS,10));
		pieces.add(new GenericGamePiece(0,0,Tester.GenericGamePiece_RADIUS,20));
		pieces.add(new GenericGamePiece(0,0,Tester.GenericGamePiece_RADIUS,50));
		striker = new Striker(0,0,Tester.GenericGamePiece_RADIUS*4/3);
		//player = new Player(striker,new Rectangle2D.Double(0,0,0,0));
	}
	
	public void settings() {
		size((int)width,(int)height);
	}
	public void setup() {
		background = loadImage("data" + File.separator + "board.png");
		imageMode(CENTER);
		black = loadImage("data" + File.separator + "black.png");
		white = loadImage("data" + File.separator + "white.png");
		queen = loadImage("data" + File.separator + "red.png");
		s = loadImage("data" + File.separator + "striker.png");
		
		
		pieces.get(0).setLoc(width/4,height/2);
		pieces.get(1).setLoc(width/2,height/2);
		pieces.get(2).setLoc(3*width/4,height/2);
		striker.setLoc(width/2, height/5);
	}
	public void draw() {
		background(255);
		image(background,width/2,height/2,width*.75f,height*.75f);
		striker.draw(this,s);
		if(turnPhase == 0) {
			for(GenericGamePiece p : pieces) {
				if (p.getValue() == 10)
					p.draw(this, black);
				else if (p.getValue() == 20)
					p.draw(this, white);
				else
					p.draw(this, queen);
			}
		}else if(turnPhase == 1) {
			striker.draw(this,s);
			for(GenericGamePiece p : pieces) {
				if(p.getValue() == 10)
					p.draw(this, black);
				else if(p.getValue() == 20)
					p.draw(this, white);
				else
					p.draw(this, queen);
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
		}else {
			striker.move(this.width/8+Tester.BORDER_WIDTH,this.height/8+Tester.BORDER_WIDTH,7*this.width/8-Tester.BORDER_WIDTH,7*this.height/8-Tester.BORDER_WIDTH);
			for(GenericGamePiece p : pieces) {
				for(GenericGamePiece q: pieces) {
					p.collide(q, this.width/8+Tester.BORDER_WIDTH,this.height/8+Tester.BORDER_WIDTH,7*this.width/8-Tester.BORDER_WIDTH,7*this.height/8-Tester.BORDER_WIDTH);		
				}
			}
			for(GenericGamePiece p : pieces) {
				p.collide(striker, this.width/8+Tester.BORDER_WIDTH,this.height/8+Tester.BORDER_WIDTH,7*this.width/8-Tester.BORDER_WIDTH,7*this.height/8-Tester.BORDER_WIDTH);
				p.move(this.width/8+Tester.BORDER_WIDTH,this.height/8+Tester.BORDER_WIDTH,7*this.width/8-Tester.BORDER_WIDTH,7*this.height/8-Tester.BORDER_WIDTH);
				if(p.getValue() == 10)
					p.draw(this, black);
				else if(p.getValue() == 20)
					p.draw(this, white);
				else
					p.draw(this, queen);
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
				turnPhase = 0;
				if(progress==2) {
					progress=3;
				}
			}
			
		}
		
		textAlign(CENTER,CENTER);
		fill(255);
		textSize(height*.03f);
		if(progress==0) {
			text("Arrow keys to move striker around. Press enter\nwhen you are comfortable with your position!",width/2,height*2/3);
		}else if(progress==1) {
			text("Use the mouse to aim the striker.\nClick the mouse to shoot.\nPress Backspace if you want to move around.",width/2,height*2/3);
		}else if(progress==2) {
			text("Wait for the pieces to stop moving.\nThen just repeat the process!",width/2,height*2/3);
		}else {
			text("You must sink another piece right after\nsinking the red one or you lose\nthe red piece.", width/2,height*3/4);
			text("You lose your piece of highest value\nif you sink the striker.",width/2,height/4);
			textSize(height*.024f);
			text("White pieces are\nworth 20 points",width/2,height*5/8);
			text("Black pieces are\nworth 10 points",width/4,height*5/8);
			text("The red piece is\nworth 50 points",width*3/4,height*5/8);
			
		}
		
		/*
		
		fill(255);
		text("White pieces are\nworth 20 points",width/2,height*5/8);
		text("Black pieces are\nworth 10 points",width/4,height*5/8);
		text("The red piece is\nworth 50 points",width*3/4,height*5/8);
			
		*/
	}
	public void keyPressed() {
		if(turnPhase==0) {
			if(keyCode==37) { //left arrow
				striker.setLoc(striker.getX()-Tester.MOVEMENT_INCREMENT,striker.getY());
				for(GenericGamePiece p : pieces) {
					if(striker.isColliding(p)) {
						striker.setLoc(p.getX()-striker.getRadius()-p.getRadius(), striker.getY());			
					}
				}				
			}
			if(keyCode==39) { //right arrow
				striker.setLoc(striker.getX()+Tester.MOVEMENT_INCREMENT,striker.getY());
				for(GenericGamePiece p : pieces) {
					if(striker.isColliding(p)) {
						striker.setLoc(p.getX()+striker.getRadius()+p.getRadius(), striker.getY());			
					}
				}
			}
			if(keyCode==38) { //up arrow
				striker.setLoc(striker.getX(),striker.getY()-Tester.MOVEMENT_INCREMENT);
				for(GenericGamePiece p : pieces) {
					if(striker.isColliding(p)) {
						striker.setLoc(striker.getX(), p.getY()-striker.getRadius()-p.getRadius());;			
					}
				}	
			}
			if(keyCode==40) { //down arrow
				striker.setLoc(striker.getX(),striker.getY()+Tester.MOVEMENT_INCREMENT);
				for(GenericGamePiece p : pieces) {
					if(striker.isColliding(p)) {
						striker.setLoc(striker.getX(), p.getY()+striker.getRadius()+p.getRadius());			
					}
				}
			}
			if(keyCode==10) {
				turnPhase = 1;
				if(progress==0)
					progress = 1;
			}
			
		}
		if(turnPhase==1) {
			if(keyCode==8 && progress>0) {
				turnPhase = 0;
			}
		}
	}
	public void mousePressed() {
		if(turnPhase==1) {
			turnPhase = 2;
		}
		if(progress==1) {
			progress = 2;
		}
		
	}
}
