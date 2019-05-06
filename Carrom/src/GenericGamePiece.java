import processing.core.PApplet;

/**Superclass for all types of game pieces.
 * 
 * @author calix
 *
 */
public class GenericGamePiece extends Piece{
	private int value;
	
	public GenericGamePiece(double x, double y, double velX, double velY, double radius, int value) {
		super(x, y, velX, velY, 50);
		this.value = value;
		super.setColor(255,0,0);
		// TODO Auto-generated constructor stub
	}
	public void draw(PApplet p) {
		p.pushStyle();
		p.fill(R, G, B);
		p.ellipse((float)x, (float)y, (float)radius, (float)radius);
		p.popStyle();
	}
}
