import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Bowling monitor class
 * The Bowling monitor handles Alley distribution
 * 
 * @author Petit, Sabir
 *
 */
public class Bowling {

	/**
	 * The BowlingAlley container
	 */
	private BowlingAlley[] alleys;
	
	/**
	 * The wait list to keep the order of the groups
	 */
	private Queue<Group> waitList;

	/**
	 * Constructor for the Bowling monitor
	 * @param nbAlleys the number of alleys
	 */
	public Bowling(int nbAlleys){
		alleys = new BowlingAlley[nbAlleys];
		for(int i = 0; i < alleys.length; i++){
			alleys[i] = new BowlingAlley(i, this);
		}
		waitList = new ArrayDeque<Group>();
	}

	/**
	 * Method called by a Client Thread to reserve or join an alley
	 * @param c the Client Thread
	 */
	public synchronized void enterClient(Client c) {
		if(!c.getGroup().isPlaying()){
			addGroupToWaitList(c);
			while(!c.getGroup().isPlaying() && 
					(availableAlley() == -1 || 
					(!waitList.isEmpty()?c.getGroup().id() != waitList.peek().id():true)))
			{
				//System.err.println("The group " + c.getGroup().id() + " is waiting on the DANCEFLOOR/ARCADE ROOM");
				try {
					wait(); // The client is waiting on the DANCEFLOOR/ARCADE ROOM
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!c.getGroup().isPlaying() && !c.getGroup().hasPlayed()){
				waitList.remove();
				BowlingAlley alley = alleys[availableAlley()];
				alley.enterClient(c);
			}
		}
	}


	/**
	 * Return the id of the first available alley, -1 if none are available
	 * @return the id of an available alley or -1 if none are available
	 */
	private synchronized int availableAlley() {
		for(BowlingAlley a : alleys){
			if(a.isAvailable())
				return a.getId();
		}
		return -1;
	}
	
	private void addGroupToWaitList(Client c) {
		for(Group g : waitList){
			if(c.getGroup().id() == g.id())
				return;
		}
		waitList.add(c.getGroup());
		System.out.println("Group " + c.getGroup().id() + " added to WAITLIST");
	}

	/**
	 * Method called by a Client Thread to end the game for his group
	 * @param group the group ending its game
	 * @param gameTime the time the game took (for display only)
	 */
	public synchronized void endGame(Group group, int gameTime) {
		group.getAlley().endGame(group,gameTime);
		notifyAll();
	}

}
