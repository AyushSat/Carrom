import processing.core.PApplet;

public class Striker extends Piece{

	public Striker(double x, double y,  double radius) {
		super(x, y, radius, 0.99);//Akshat: mess with the 0.99 value to make it smoother than normal pieces but not too smooth
		// TODO Auto-generated constructor stub
	}
	
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
