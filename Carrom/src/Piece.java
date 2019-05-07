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
			velX*=-1;
		}else if(this.x+this.radius > maxX) {
			x = maxX - this.radius;
			velX*=-1;
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
			velY*=-1;
		}else if(this.y+this.radius > maxY) {
			y = maxY - this.radius;
			velY*=-1;
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
		return Math.sqrt(Math.pow(this.x-that.x, 2)+Math.pow(this.y-that.y, 2)) <= this.radius + that.radius;
	}
	
	/**This method will make two pieces collide with each other.
	 * 
	 * @param that the piece that you are trying to collide with
	 */
	public void collide(Piece that) {
		if(this.isColliding(that) && (this.isMoving() || that.isMoving())) {
			double thisMass = Math.pow(this.radius,2);
			double thatMass = Math.pow(that.radius,2);
			double dX = that.x - this.x;
			double dY = that.y - this.y;
			double d = Math.sqrt(Math.pow(dX,2) + Math.pow(dY, 2));
			
			
			double temp = this.velX;
			this.velX = 0.98*that.velX;
			that.velX = 0.98*temp;
			temp = this.velY;
			this.velY = 0.98*that.velY;
			that.velY = 0.98*temp;
		}
	}
}
