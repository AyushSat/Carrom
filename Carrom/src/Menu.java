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

	public Menu() {
		
	}

	public void settings() {
		size(1000, 1000);
	}

	public void setup() {
		
	}

	public void draw() {
	
	}
	
	public void mouseDragged() {
		
	} 
	
	public void mousePressed() {
		
	}
	
	public void keyPressed() {
		//initialize and run sketch
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
