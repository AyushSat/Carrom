import java.io.File;

import processing.core.PApplet;
import processing.core.PImage;

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
		//img = loadImage("data" + File.separator + "");
	}
	public void draw() {
		//image(img, 0, 0);
		background(255);
		fill(0);
		textSize(50);
		textAlign(CENTER,CENTER);
		text("Code and backend by Calix",width/2,height/3);
		text("Images by Akshat",width/2,height/2);
		text("Networking by Ayush",width/2,height*2/3);
		textSize(10);
		text("hppeng",width*15/16,height*15/16);
	}
	public void keyPressed() {

	}
	public void mousePressed() {

		
	}
	public void exit() {
		dispose();
	}
}
