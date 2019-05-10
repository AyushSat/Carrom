
/**Represents a player of Carrom
 * 
 * @author Akshat
 *
 */
public class Player {
	private Striker striker;
	
	/**Instantiates a player with a striker
	 * 
	 * @param st the striker
	 */
	public Player(Striker st) {
		striker = st;
	}

	public Striker getStriker() {
		return striker;
	}

	public void setStriker(Striker striker) {
		this.striker = striker;
	}
	
	
}
