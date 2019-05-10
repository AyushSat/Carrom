import processing.core.PApplet;

/**
 * 
 * @author Calix
 *
 */
public class Striker extends Piece{

	/**Instantiates a striker.
	 * 
	 * @param x initial x position
	 * @param y intial y position
	 * @param radius radius of the striker
	 */
	public Striker(double x, double y,  double radius) {
		super(x, y, radius, 0.99);//Akshat: mess with the 0.99 value to make it smoother than normal pieces but not too smooth
		// TODO Auto-generated constructor stub
	}
	
	/**Instantiates a striker with color.
	 * 
	 * @param x initial x position
	 * @param y initial y position
	 * @param radius radius of the striker
	 * @param R R value of RGB. 0 to 255 inclusive
	 * @param G G value of RGB. 0 to 255 inclusive
	 * @param B B value of RGB. 0 to 255 inclusive
	 */
	public Striker(double x, double y, double radius, int R, int G, int B) {
		super(x,y,radius,0.99,R,G,B);
	}
	
	/**Tells if the striker contains a point
	 * 
	 * @param x2 the x coordinate of that point
	 * @param y2 the y coordinate of that point
	 * @return whether the point is in the circle defined by the striker.
	 */
	public boolean contains(double x2, double y2) {
		return Math.sqrt((x-x2) * (x-x2) + (y - y2) * (y - y2)) <= radius;
	}

	@Override
	/**Same implementation as GenericGamePiece, but since Strikers should not be scored, it will return -1.
	 * 
	 */
	public int score(double minX, double minY, double maxX, double maxY, double radius) {
		if((x-this.radius-radius <= minX || x+this.radius+radius >= maxX) && (y-this.radius-radius <= minY || y+this.radius+radius >= maxY)) {
			return -1;
		}
		return 0;
	}

}
