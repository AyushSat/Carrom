import processing.core.PApplet;

/**Represents a circular piece. It is an interactive, moving piece. 
 * 
 * @author calix
 *
 */
public abstract class Piece {
	protected double x;
	protected double y;
	protected double velX;
	protected double velY;
	protected double radius;
	protected int R, G, B;
	
	public Piece(double x, double y, double velX, double velY, double radius) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.radius = radius;
		this.R = 0;
		this.G = 0;
		this.B = 0;
	}
	
	/**Sets the color of the Piece.
	 * 
	 * @param R red value
	 * @param G green value
	 * @param B blue value
	 * @precondition R, G, and B are between 0 and 255, inclusive.
	 */
	public void setColor(int R, int G, int B) {
		this.R = R;
		this.G = G;
		this.B = B;
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public double getVelX() {	
		return this.velX;
	}
	public double getVelY() {
		return this.velY;
	}
	public double getRadius() {
		return this.radius;
	}
	
	
	public void moveX() {
		x+=velX;
	}
	public void moveY() {
		y+=velY;
	}
	public abstract void draw(PApplet p);
}
