import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

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

	public Menu(double width, double height) {
		start = new Button(width/2 - 50, height/2 - 10, 100, 20, Color.CYAN, Color.BLUE, "Start");
		w = width;
		h = height;
	}

	public void settings() {
		size((int)w, (int)h);
	}

	public void setup() {
		
	}

	public void draw() {
		background(255);
		
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
