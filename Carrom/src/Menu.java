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
 * @author Akshat
 * @version 5/6/19
 */
public class Menu extends PApplet {
	
	private Button start;
	private double w;
	private double h;
	private PImage background;
	private PImage title;

	public Menu(double width, double height) {
		start = new Button(width/2 - 50, height/2 - 10, 100, 20, Color.CYAN, Color.BLUE, "Start");
		w = width;
		h = height;
	}

	public void settings() {
		size((int)w, (int)h);
	}

	public void setup() {
		background = loadImage("data" + File.separator + "backgroundMenu.jpg");
		title = loadImage("data" + File.separator + "title.png");
	}

	public void draw() {
		image(background, 0, 0);
		imageMode(CENTER);
		image(title, width/2, 200);
		
		if(start.getBoundingRectangle().contains(mouseX, mouseY))
			start.setHover(true);
		else
			start.setHover(false);
			
		start.draw(this);
	}
	
	public void mouseDragged() {
		
	} 
	
	public void mousePressed() {
		if(start.getBoundingRectangle().contains(mouseX, mouseY))
			startGame();
	}
	
	public void keyPressed() {
	
	}
	
	private void startGame() {
		GameBoard board = new GameBoard(9, 9);
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
