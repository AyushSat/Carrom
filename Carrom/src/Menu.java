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
	private Tutorial tutorial;
	private Button start;
	private Button credits;
	private Button tut;
	private double w;
	private double h;
	private PImage background;

	public Menu(double width, double height) {
		tutorial = null;
		start = new Button(500, 490, 200, 40, Color.CYAN, Color.BLUE, "Start", 50);
		tut = new Button(500, 550, 200, 40, Color.CYAN, Color.BLUE, "Tutorial",
				50);
		credits = new Button(500, 610, 200, 40, Color.CYAN, Color.BLUE, "Credits",
				50);
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
		start.draw(this);
		tut.draw(this);
		credits.draw(this);

	}

	public void mouseDragged() {

	}

	public void mousePressed() {
		if (start.getBoundingRectangle().contains(mouseX, mouseY))
			startGame();
		if (tut.getBoundingRectangle().contains(mouseX, mouseY))
			tutorial();
		if (credits.getBoundingRectangle().contains(mouseX, mouseY))
			tutorial();
	}

	public void mouseMoved() {
		start.setHover(start.getBoundingRectangle().contains(mouseX, mouseY));
		tut.setHover(tut.getBoundingRectangle().contains(mouseX, mouseY));
		credits.setHover(credits.getBoundingRectangle().contains(mouseX, mouseY));

	}

	public void keyPressed() {

	}

	private void startGame() {
		GameBoard board = new GameBoard(9, 9);
		PApplet.runSketch(new String[] { "Carrom" }, board);

		PSurfaceAWT surf = (PSurfaceAWT) board.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		// window is 1000x1000 permanently
		window.setSize(1000, 1000);
		window.setMinimumSize(new Dimension(1000, 1000));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);// a stretch is to change this

		// make window visible
		window.setVisible(true);
		canvas.requestFocus();
	}

}
