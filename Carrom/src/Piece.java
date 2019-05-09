import java.util.ArrayList;

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
	/**An epsilon used for double comparisons.
	 * 
	 */
	public static final double EPSILON = 1E-5;
	
	
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
		return (Math.abs(velX)>0 || Math.abs(velY)>0);
	}
	
	/**Enacts motion in the X dimension.
	 * 
	 * @param minX The minimum x boundary
	 * @param maxX The maximum x boundary
	 */
	public void moveX(double minX, double maxX) {
		x+=velX;
		if(Math.abs(velX)<NEGLIGIBLE_VEL) {
			velX = 0;
		}else {
			velX *= friction;
		}
		if(this.x-this.radius < minX) {
			x = this.radius + minX;
			velX*=-friction;
		}else if(this.x+this.radius > maxX) {
			x = maxX - this.radius;
			velX*=-friction;
		}
	}
	
	/**Enacts motion in the Y dimension.
	 * 
	 * @param minY The minimum y boundary
	 * @param maxY The maximum y boundary
	 */
	public void moveY(double minY, double maxY) {
		y+=velY;
		if(Math.abs(velY)<NEGLIGIBLE_VEL) {
			velY = 0;
		}else {
			velY *= friction;
		}
		if(this.y-this.radius < minY) {
			y = minY + this.radius;
			velY*=-friction;
		}else if(this.y+this.radius > maxY) {
			y = maxY - this.radius;
			velY*=-friction;
		}
		
	}
	
	/**Enacts motion in general
	 * 
	 * @param minX The minimum x boundary
	 * @param minY The maximum x boundary
	 * @param maxX The minimum y boundary
	 * @param maxY The maximum y boundary
	 */
	public void move(double minX, double minY, double maxX, double maxY) {
		moveX(minX,maxX);
		moveY(minY,maxY);
	}
	
	/**Draws the piece on the PApplet
	 * 
	 * @param p the PApplet window to draw on
	 */
	public void draw(PApplet p) {
		p.pushStyle();
		p.fill(R, G, B);
		p.ellipse((float)x, (float)y, (float)radius * 2, (float)radius * 2);
		p.popStyle();
	}
	
	/**This method will determine if two pieces are in contact with one another.
	 * 
	 * @param that The piece to check collision with
	 * @return a boolean representing whether the two pieces are colliding or not.
	 */
	public boolean isColliding(Piece that) {
		return Math.sqrt(Math.pow(this.x-that.x, 2)+Math.pow(this.y-that.y, 2)) < this.radius + that.radius;
	}
	
	/**This method will make one piece collide with many others
	 * 
	 * @param others the ArrayList of Pieces that this piece should collide with
	 */
	public void collide(ArrayList<Piece> others,double minX, double minY, double maxX, double maxY) {
		for(Piece that : others) {
			this.collide(that, minX, minY, maxX, maxY);
		}
	}
	
	/**this method will make one piece collide with one other.
	 * 
	 * @param that the piece that this piece should collide with
	 */
	public void collide(Piece that, double minX, double minY, double maxX, double maxY) {
		if(this==that) {//literally equality checking
			return;
		}
		if(this.isColliding(that) && (this.isMoving() || that.isMoving())) {
			double thisMass = Math.pow(this.radius,2);
			double thatMass = Math.pow(that.radius,2);
			
			double dX = this.x - that.x;
			double dY = this.y - that.y;
			double dXYsq = Math.pow(dX, 2)+Math.pow(dY, 2);
		
			double dvX = this.velX - that.velX;
			double dvY = this.velY - that.velY;
			//double dV = Math.pow(dvX, 2)+Math.pow(dvY, 2);
			
			
			that.velX = (dX*dvX+dY*dvY)/dXYsq*dX*thisMass/thatMass;
			that.velY = (dX*dvX+dY*dvY)/dXYsq*dY*thisMass/thatMass;
			this.velX = (dvX-((dX*dvX+dY*dvY)/dXYsq*dX))*thatMass/thisMass;
			this.velY = (dvY-((dX*dvX+dY*dvY)/dXYsq*dY))*thatMass/thisMass;
			
			that.move(minX, minY, maxX, maxY);
			this.move(minX, minY, maxX, maxY);
		}
	}
	
	//This is just to unclip two pieces
	//Precondition: that != this and they are actually in collision, but are not right on top of each other.
	private void unCollide(Piece that) {
		if(this==that) {
			return;
		}
		double dX = that.x - this.x;
		double dY = that.y - this.y;
		double dXY = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
		double realD = this.radius + that.radius;
		double realDX = dX * realD / dXY;
		double realDY = dY * realD / dXY;
		this.x -= (1.01)*(realDX - dX);
		this.y -= (1.01)*(realDY - dY);
	}
	
	/**Gives the score of a piece at where it is, given the bounds of the board and radius of corner holes.
	 * 
	 * @param minX left boundary
	 * @param minY top boundary
	 * @param maxX right boundary
	 * @param maxY top boundary
	 * @param radius radius of the hole in the corner
	 * @precondition: all parameters must be valid and accurate as to the actual board which the piece is located on
	 * @return The score that the piece should net at its current XY location in those bounds
	 */
	public abstract int score(double minX, double minY, double maxX, double maxY, double radius);
}
