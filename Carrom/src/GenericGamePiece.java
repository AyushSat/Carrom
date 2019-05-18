import processing.core.PApplet;

/**Superclass for all types of game pieces.
 * 
 * @author calix
 *
 */
public class GenericGamePiece extends Piece{
	
	private int value;
	/**Instantiates a GenericGamePiece with default color (black)
	 * 
	 * @param x the initial x position
	 * @param y the initial y position
	 * @param radius the radius of the GenericGamePiece
	 * @param value the value of the GenericGamePiece (points)
	 */
	public GenericGamePiece(double x, double y, double radius, int value) {
		super(x,y,radius, 0.965);
		this.value = value;
		//super.setColor(255,0,0);
	}

	/** Instantiates a GenericGamePiece with color values passed in.
	 * 
	 * @param x the initial x position
	 * @param y the initial y position
	 * @param radius the radius of the GenericGamePiece
	 * @param value the value of the GenericGamePiece (points)
	 * @param R the R value of RGB, 0-255 inclusive.
	 * @param G the G value of RGB, 0-255 inclusive.
	 * @param B the B value of RGB, 0-255 inclusive.
	 */
	public GenericGamePiece(double x, double y, double radius, int value, int R, int G, int B) {
		super(x,y,radius, 0.98, R, G, B);
		this.value = value;
	}
	
	/**This method checks if the GenericGamePiece should be scored, and if so, returns the value. Else, return 0.
	 * 
	 * @param minX left boundary of the board
	 * @param minY top boundary of the board
	 * @param maxX right boundary of the board
	 * @param maxY bottom boundary of the board
	 * @param radius the radius of the holes in the corner to score in
	 * @return the score that the piece should receive at its current position in the board.
	 */
	public int score(double minX, double minY, double maxX, double maxY, double radius) {
		if((x-this.radius-radius <= minX || x+this.radius+radius >= maxX) && (y-this.radius-radius <= minY || y+this.radius+radius >= maxY)) {
			return this.value;
		}
		return 0;
	}
	
	/**
	 * 
	 * @return the point value of this piece
	 */
	public int getValue() {
		return value;
	}
}
