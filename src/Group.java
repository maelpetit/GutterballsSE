import java.util.ArrayList;
import java.util.List;

/**
 * The Group monitor class
 * 
 * @author Petit, Sabir
 *
 */
public class Group {
	
	private final int MAX_MEMBERS_PER_GROUP; 
	
	/**
	 * The Members list
	 */
	private List<Client> members;
	/**
	 * The Group id
	 */
	private int id;
	/**
	 * The BowlingAlly monitor this group is going to play on
	 */
	private BowlingAlley alley;
	/**
	 * Boolean playing attribute
	 * is True when the group is playing in the BowlingAlley alley
	 */
	private boolean playing = false;
	/**
	 * Boolean played attribute
	 * is True when the group has played
	 */
	private boolean played = false;
	/**
	 * Counter for the number of group members with shoes
	 */
	private int membersWithShoes = 0;
	
	/**
	 * Constructor for a Group monitor
	 * @param i The Group id
	 * @param max The maximum number of members per group
	 */
	public Group(int i, int max){
		id = i;
		MAX_MEMBERS_PER_GROUP = max;
		members = new ArrayList<Client>();
	}
	/**
	 * Getter for the member at index i 
	 * @param i index for member to get
	 * @return The Client at index i
	 */
	public Client getMember(int i){
		return members.get(i);
	}
	
	/**
	 * Getter for the maximum number of members in the group
	 * @return The maximum number of members in the group
	 */
	public int getMax(){
		return MAX_MEMBERS_PER_GROUP;
	}
	
	/**
	 * Getter for the Group id
	 * @return The Group id
	 */
	public int id(){
		return id;
	}

	/**
	 * Adds the specified Client to the members list
	 * @param c The Client to add
	 */
	public synchronized void addMember(Client c) {
		members.add(c);
		//System.out.println("Client "+ c.id() +" -> Group " + id);
	}
	
	/**
	 * Returns true if the group is full
	 * @return True if the group is full, False else
	 */
	public boolean isFull() {
		return members.size() == MAX_MEMBERS_PER_GROUP;
	}
	
	/**
	 * Returns true if the group is playing on the alley
	 * @return True if the group is playing
	 */
	public boolean isPlaying(){
		return playing;
	}

	/**
	 * Sets the Group playing attribute
	 * @param p 
	 */
	public void setPlaying(boolean p) {
		playing = p;
	}

	/**
	 * Getter for the alley
	 * @return the BowlingAlley
	 */
	public BowlingAlley getAlley() {
		return alley;
	}

	/**
	 * Setter for the alley
	 * @param alley the BowlingAlley
	 */
	public void setAlley(BowlingAlley alley) {
		this.alley = alley;
	}

	/**
	 * Return true if the group has already played
	 * @return true is the groups has played, False else
	 */
	public boolean hasPlayed() {
		return played;
	}
	
	/**
	 * Sets the played attribute to true
	 */
	public void donePlaying(){
		played = true;
	}

	/**
	 * Method called to wait for the other members of the group to have their shoes
	 * @param c The Client Thread
	 */
	public synchronized void waitShoes(Client c) {
		membersWithShoes++;
		while(membersWithShoes < MAX_MEMBERS_PER_GROUP){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
				
	}

}
