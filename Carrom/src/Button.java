import java.awt.Color;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

/**
 * Represents a PApplet Button!
 * 
 * @author Akshat
 *
 */
public class Button {

	private double x;
	private double y;
	private double width;
	private double height;
	private int round;
	private Color defaultColor;
	private Color hoverColor;
	private boolean hovered;
	private boolean pressed;
	private String text;
	private boolean st;
	private PFont font;

	/**
	 * 
	 * @param w  width of the button
	 * @param h  height of the button
	 * @param dC Color of button when button it is not being hovered over
	 * @param hC Color of button when button it is being hovered over
	 * @param t  the text to be displayed on this Button
	 * @param r  the roundness of the button
	 */
	public Button(double xVal, double yVal, double w, double h, Color dC, Color hC, String t, int r) {
		x = xVal;
		y = yVal;
		width = w;
		height = h;
		defaultColor = dC;
		hoverColor = hC;
		hovered = false;
		text = t;
		round = r;
		pressed = false;
		st = false;
		font = null;
	}

	/**
	 * 
	 * @param marker the PApplet used to draw this Button
	 */
	public void draw(PApplet marker) {
		marker.pushStyle();
		if (hovered)
			marker.fill(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue());
		else
			marker.fill(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue());
		marker.rectMode(PConstants.CENTER);
		marker.rect((float) x, (float) y, (float) width, (float) height, round);
		marker.fill(255);
		marker.textFont(font);
		marker.textSize((float) height - 10);
		marker.text(text, (float) (x - marker.textWidth(text) / 2), (float) (y) + marker.textAscent() / 2 - 5);
		if (st) {
			int offset = 13;
			marker.stroke(255, 0, 0);
			marker.line((float) x - (float) width / 2 + offset, (float) y - (float) height / 2, (float) x + (float) width / 2 - offset,
					(float) y + (float) height / 2);
			marker.line((float) x - (float) width / 2 + offset, (float) y + (float) height / 2, (float) x + (float) width / 2 - offset,
					(float) y - (float) height / 2);
		}
		marker.popStyle();
	}

	public void setHover(boolean h) {
		hovered = h;
	}

	public void setPressed(boolean p) {
		pressed = p;
	}

	public boolean getPressed() {
		return pressed;
	}

	/**
	 * 
	 * @return a Rectangle2D.Double object that bounds this Button
	 */
	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double(x - width / 2, y - height / 2, width, height);
	}
	
	public boolean getST() {
		return st;
	}

	public void toggleST() {
		st = !st;
	}
	
	public void setFont(PFont f) {
		font = f;
	}

}
