
/**
 * The Employee monitor class
 * The employee is in charge of giving permission to take shoes and taking back shoes
 * 
 * @author Petit, Sabir
 *
 */
public class Employee {
	
	/**
	 * The group that has the priority on taking shoes
	 */
	private Group group;
	
	/**
	 * The number of authorized members of the group group
	 */
	private int nbAuth;
	
	/**
	 * The maximum number of members per group
	 */
	private final int MAX_MEMBERS_PER_GROUP;
	
	/**
	 * The ShoesRoom monitor
	 */
	private ShoesRoom shoesroom;
	
	/**
	 * Constructor for the employee
	 * 
	 * @param max The maximum number of members per group
	 * @param room The ShoesRoom monitor
	 */
	public Employee(int max, ShoesRoom room){
		group = null;
		nbAuth = 0;
		MAX_MEMBERS_PER_GROUP = max;
		shoesroom = room;
	}
	
	/**
	 * Method called in the Client c Thread to ask for shoes
	 * @param c The Client Thread
	 */
	public synchronized void askTakeShoes(Client c) {
		if(group == null || nbAuth == MAX_MEMBERS_PER_GROUP){
			group = c.getGroup();
			nbAuth = 0;
		}
		/*
		 * The Client thread waits if there are no shoes available or if the current group is not his
		 */
		while(!shoesroom.shoesAvailable() || group.id() != c.getGroup().id()){
			//System.err.println("The Group " + c.getGroup().id() + " is waiting for AUTHORIZATION");
			try {
				wait(); // The client is waiting on the DANCEFLOOR/ARCADE ROOM
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/*
			 * if the Client is the first to wake up after a group is done
			 * his group is set as the priority group
			 */
			if(nbAuth == MAX_MEMBERS_PER_GROUP){
				group = c.getGroup() ;
				nbAuth = 0;
			}
		}

		nbAuth++;
		//System.out.println("Client " + c.id() + " IS AUTHORIZED to get shoes");
		notifyAll();
	}

	/**
	 * Method called in the Client c Thread to put back shoes
	 * @param c The Client Thread
	 */
	public synchronized void askPutShoes(Client client) {
		shoesroom.putShoes(client);
		notifyAll();
	}

	
}
