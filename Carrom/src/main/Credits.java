package main;
import java.io.File;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Akshat Jain
 *
 */
public class Credits extends PApplet{

	private float width;
	private float height;
	private PImage img;
	
	/**Makes a tutorial with the given width and height
	 * 
	 * @param width width of screen
	 * @param height height of screen
	 */
	public Credits(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void settings() {
		size((int)width, (int)height);
	}
	public void setup() {
		img = loadImage("data" + File.separator + "credits.png");
	}
	public void draw() {
		image(img, 0, 0, 1000, 980);
	}
	public void keyPressed() {

	}
	public void mousePressed() {

		
	}
	public void exit() {
		dispose();
	}
}
