import processing.core.PApplet;

public class Striker extends Piece{

	public Striker(double x, double y,  double radius) {
		super(x, y, radius, 0.99);//Akshat: mess with the 0.99 value to make it smoother than normal pieces but not too smooth
		// TODO Auto-generated constructor stub
	}
	
	public boolean contains(double x2, double y2) {
		return Math.sqrt((x-x2) * (x-x2) + (y - y2) * (y - y2)) <= radius;
	}

}
