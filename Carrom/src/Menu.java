import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * PApplet for testing purposes
 * 
 * @author Akshat but later Calix
 * @version 5/6/19
 */
public class Menu extends PApplet {
	private Tutorial tutorial;
	private Button start;
	private Button credits;
	private Button tut;
	private Button twoPlayer;
	private Button threePlayer;
	private Button fourPlayer;
	private Button back;
	private boolean startPressed;
	private double w;
	private double h;
	private PImage background;

	public Menu(double width, double height) {
		startPressed = false;
		tutorial = null;
		start = new Button(width*.5, height*.49, width*.2, height*.04, Color.CYAN, Color.BLUE, "Start", 50);
		tut = new Button(width*.5, height*.55, width*.2, height*.04, Color.CYAN, Color.BLUE, "Tutorial",
				50);
		credits = new Button(width*.5, height*.61, width*.2, height*.04, Color.CYAN, Color.BLUE, "Credits",
				50);
		twoPlayer = new Button(width*.5, height*.49, width*.2, height*.04, Color.CYAN, Color.BLUE, "2 players", 50);
		threePlayer = new Button(width*.5, height*.55, width*.2, height*.04, Color.CYAN, Color.BLUE, "3 players",
				50);
		fourPlayer = new Button(width*.5, height*.61, width*.2, height*.04, Color.CYAN, Color.BLUE, "4 players",
				50);
		back = new Button(width*.5,height*.67,width*.2,height*.04, Color.CYAN, Color.BLUE, "Back", 50);
		w = width;
		h = height;
	}

	public void settings() {
		size((int) w, (int) h);
	}

	public void setup() {
		background = loadImage("data" + File.separator + "backgroundMenu.png");
	}

	public void draw() {
		image(background, 0, 0, 1000, 980);
		if(startPressed) {
			twoPlayer.draw(this);
			threePlayer.draw(this);
			fourPlayer.draw(this);
			back.draw(this);
		}else {
			start.draw(this);
			tut.draw(this);
			credits.draw(this);
		}	
	}

	public void mouseDragged() {

	}

	public void mousePressed() {
		if(startPressed) {
			if(twoPlayer.getBoundingRectangle().contains(mouseX,mouseY)) {
				startGame(2);
			}
			if(threePlayer.getBoundingRectangle().contains(mouseX,mouseY)) {
				startGame(3);
			}
			if(fourPlayer.getBoundingRectangle().contains(mouseX,mouseY)) {
				startGame(4);
			}
			if(back.getBoundingRectangle().contains(mouseX,mouseY)) {
				startPressed = false;
			}
		}else {
			if (start.getBoundingRectangle().contains(mouseX, mouseY))
				startPressed = true;
			if (tut.getBoundingRectangle().contains(mouseX, mouseY))
				tutorial();
			if (credits.getBoundingRectangle().contains(mouseX, mouseY)) {
				//tutorial();
			}
			
		}
		
	}

	public void mouseMoved() {
		start.setHover(start.getBoundingRectangle().contains(mouseX, mouseY));
		tut.setHover(tut.getBoundingRectangle().contains(mouseX, mouseY));
		credits.setHover(credits.getBoundingRectangle().contains(mouseX, mouseY));
		twoPlayer.setHover(twoPlayer.getBoundingRectangle().contains(mouseX,mouseY));
		threePlayer.setHover(threePlayer.getBoundingRectangle().contains(mouseX,mouseY));
		fourPlayer.setHover(fourPlayer.getBoundingRectangle().contains(mouseX,mouseY));
		back.setHover(back.getBoundingRectangle().contains(mouseX,mouseY));
	}

	public void keyPressed() {

	}

	private void startGame(int players) {
		GameBoard board = new GameBoard(9, 9, players);
		PApplet.runSketch(new String[] { "Carrom" }, board);

		PSurfaceAWT surf = (PSurfaceAWT) board.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		// window is 1000x1000 permanently
		window.setSize(1000, 1000);
		window.setMinimumSize(new Dimension(1000, 1000));
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setResizable(false);// a stretch is to change this

		// make window visible
		window.setVisible(true);
		canvas.requestFocus();
	}

	public void tutorial() {
		tutorial = new Tutorial(width, height);
		PApplet.runSketch(new String[] { "Tutorial" }, tutorial);

		PSurfaceAWT surf = (PSurfaceAWT) tutorial.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		// window is 1000x1000 permanently
		window.setSize(1000, 1000);
		window.setMinimumSize(new Dimension(1000, 1000));
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setResizable(false);// a stretch is to change this

		// make window visible
		window.setVisible(true);
		canvas.requestFocus();
	}

}