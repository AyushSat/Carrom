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
	
	/**An epsilon representing the threshold for motion to be considered not motion.
	 * 
	 */
	public static final double NEGLIGIBLE_VEL = .1;
	
	public Piece(double x, double y, double radius) {
		this.x = x;
		this.y = y;
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
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	/**Tells if the piece is in motion or not
	 * 
	 * @return a boolean representing whether the piece is in motion or not
	 */
	public boolean isMoving() {
		return (velX>0 && velY>0);
	}
	
	/**Enacts motion and friction in the X dimension.
	 * 
	 */
	public void moveX() {
		x+=velX;
		if(velX<NEGLIGIBLE_VEL) {
			velX = 0;
		}else {
			velX *= 0.94;
		}
	}
	/**Enacts motion and friction in the Y dimension.
	 * 
	 */
	public void moveY() {
		y+=velY;
		if(velY<NEGLIGIBLE_VEL) {
			velY = 0;
		}else {
			velY *= 0.94;
		}
	}
	
	/**Enacts motion in general.
	 * 
	 */
	public void move() {
		moveX();
		moveY();
	}
	public abstract void draw(PApplet p);
}
