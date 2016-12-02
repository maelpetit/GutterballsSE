/**
 * BowlingAlley monitor class
 * 
 * @author Petit, Sabir
 *
 */
public class BowlingAlley {
	
	/**
	 * The Alley id
	 */
	private int id;
	
	/**
	 * Boolean available attribute (lock)
	 */
	private boolean available;

	/**
	 * Constructor for a BowlingAlley
	 * @param i the Alley id
	 * @param b the Bowling monitor
	 */
	public BowlingAlley(int i, Bowling b) {
		id = i;
		available = true;
	}

	/**
	 * Returns true if the Alley is available
	 * @return True if the Alley is available, False else
	 */
	public boolean isAvailable(){
		return available;
	}

	/**
	 * Setter for the available attribute
	 * @param a 
	 */
	public void setAvailable(boolean a){
		available = a;
	}

	/**
	 * Getter for the alley id
	 * @return the Alley id
	 */
	public int getId(){
		return id;
	}

	/**
	 * Method called by a Client Thread to start a game and lock the Alley
	 * @param c the Client Thread
	 */
	public synchronized void enterClient(Client c) {
		if(!c.getGroup().isPlaying()){
			setAvailable(false);
			c.getGroup().setPlaying(true);
			c.getGroup().setAlley(this);
			System.out.println("Group " + c.getGroup().id() + " is PLAYING on alley " + id);
		}
	}

	/**
	 * Method called by a Client Thread to end the game
	 * @param g the group of the Client Thread
	 * @param gameTime the lenght of the game for display only
	 */
	public synchronized void endGame(Group g, int gameTime) {
		if(g.isPlaying()){
			/*
			 * a random member from the group is selected to end the game
			 */
			int captain = (int) (Math.random()*g.getMax());
			endGame(g.getMember(captain),gameTime);
		}		
	}

	/**
	 * Method called by a Client Thread to end the game
	 * @param c the Client Thread
	 * @param gameTime the lenght of the game for display only
	 */
	private synchronized void endGame(Client c, int gameTime) {
		Group g = c.getGroup();
		g.getAlley().setAvailable(true);
		g.setPlaying(false);
		g.donePlaying();
		System.out.println("Group "+c.getGroup().id()+" DONE PLAYING , Game time : "+gameTime +" ms");
	}

}
