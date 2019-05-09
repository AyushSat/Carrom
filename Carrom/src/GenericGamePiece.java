import processing.core.PApplet;

/**Superclass for all types of game pieces.
 * 
 * @author calix
 *
 */
public class GenericGamePiece extends Piece{
	
	private int value;
	
	public GenericGamePiece(double x, double y, double radius, int value) {
		super(x,y,radius, 0.98);
		this.value = value;
		super.setColor(255,0,0);
	}

	@Override
	public int score(double minX, double minY, double maxX, double maxY, double radius) {
		if((x-this.radius-radius <= minX || x+this.radius+radius >= maxX) && (y-this.radius-radius <= minY || y+this.radius+radius >= maxY)) {
			return this.value;
		}
		return 0;
	}
}
