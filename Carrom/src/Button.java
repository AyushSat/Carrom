import java.awt.Color;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PConstants;

/**Represents a PApplet Button!
 * 
 * @author Akshat
 *
 */
public class Button {
	
	private double x;
	private double y;
	private double width;
	private double height;
	private Color defaultColor;
	private Color hoverColor;
	private boolean hovered;
	private String text;
	
	/**
	 * 
	 * @param w width of the button
	 * @param h height of the button
	 * @param dC Color of button when button it is not being hovered over
	 * @param hC Color of button when button it is being hovered over
	 * @param t the text to be displayed on this Button
	 */
	public Button(double xVal, double yVal, double w, double h, Color dC, Color hC, String t) {
		x = xVal;
		y = yVal;
		width = w;
		height = h;
		defaultColor = dC;
		hoverColor = hC;
		hovered = false;
		text = t;
	}
	
	/**
	 * 
	 * @param marker the PApplet used to draw this Button
	 */
	public void draw(PApplet marker) {
		marker.pushStyle();
		if(hovered)
			marker.fill(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue());
		else
			marker.fill(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue());
		marker.rect((float)x, (float)y, (float)width, (float)height);
		marker.fill(255);
		marker.text(text, (float)(x + width/2 - marker.textWidth(text)/2), (float)(y + height/2) + marker.textAscent()/2);
		marker.popStyle();
	}
	
	public void setHover(boolean h) {
		hovered = h;
	}
	
	/**
	 * 
	 * @return a Rectangle2D.Double object that bounds this Button
	 */
	public Rectangle2D.Double getBoundingRectangle(){
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	
}
