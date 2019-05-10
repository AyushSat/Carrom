import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

/**Represents a player of Carrom
 * 
 * @author Akshat
 *
 */
public class Player {
	private Striker striker;
	private Rectangle2D.Double hitarea;
	private ArrayList<GenericGamePiece> myPieces;
	
	public Player(Striker striker, Double hitarea) {
		super();
		this.striker = striker;
		this.hitarea = hitarea;
	}
	public Striker getStriker() {
		return striker;
	}
	public void setStriker(Striker striker) {
		this.striker = striker;
	}
	public Rectangle2D.Double getHitarea() {
		return hitarea;
	}
	public void setHitarea(Rectangle2D.Double hitarea) {
		this.hitarea = hitarea;
	}
	
	public void addCoin(GenericGamePiece piece) {
		myPieces.add(piece);
	}
	
	public void removeCoin() {
		for(int i = 0;i<myPieces.size();i++) {
			GenericGamePiece piece = myPieces.get(i);
			if(piece.getValue() == 50) {
				myPieces.remove(i);
				return;
			}
			
		}
		for(int i = 0;i<myPieces.size();i++) {
			GenericGamePiece piece = myPieces.get(i);
			if(piece.getValue() == 20) {
				myPieces.remove(i);
				return;
			}
		}
		
		for(int i = 0;i<myPieces.size();i++) {
			GenericGamePiece piece = myPieces.get(i);
			if(piece.getValue() == 10) {
				myPieces.remove(i);
				return;
			}
		}
		
	}
	
}
