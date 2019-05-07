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
	protected double friction;
	
	/**An epsilon representing the threshold for motion to be considered not motion.
	 * 
	 */
	public static final double NEGLIGIBLE_VEL = .1;
	
	public Piece(double x, double y, double radius,double friction) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.velX = 0;
		this.velY = 0;
		this.R = 0;
		this.G = 0;
		this.B = 0;
		this.friction = friction;
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
	public void setLoc(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**Tells if the piece is in motion or not
	 * 
	 * @return a boolean representing whether the piece is in motion or not
	 */
	public boolean isMoving() {
		return (velX>0 && velY>0);
	}
	
	/**Enacts motion and friction in the X dimension.
	 * @param p the PApplet it checks for border collision
	 */
	public void moveX(PApplet p) {
		x+=velX;
		if(Math.abs(velX)<NEGLIGIBLE_VEL) {
			velX = 0;
		}else {
			velX *= friction;
		}
		if(this.x-this.radius < 0) {
			x = this.radius;
			velX*=-1;
		}else if(this.x+this.radius > p.width) {
			x = p.width - this.radius;
			velX*=-1;
		}
	}
	/**Enacts motion and friction in the Y dimension.
	 * @param p the PApplet it checks for border collision
	 */
	public void moveY(PApplet p) {
		y+=velY;
		if(Math.abs(velY)<NEGLIGIBLE_VEL) {
			velY = 0;
		}else {
			velY *= friction;
		}
		if(this.y-this.radius < 0) {
			y = this.radius;
			velY*=-1;
		}else if(this.y+this.radius > p.height) {
			y = p.height - this.radius;
			velY*=-1;
		}
		
	}
	
	/**Enacts motion in general.
	 * 
	 */
	public void move(PApplet p) {
		moveX(p);
		moveY(p);
	}
	public void draw(PApplet p) {
		p.pushStyle();
		p.fill(R, G, B);
		p.ellipse((float)x, (float)y, (float)radius * 2, (float)radius * 2);
		p.popStyle();
	}
}
