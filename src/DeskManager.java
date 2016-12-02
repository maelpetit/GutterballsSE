import java.util.ArrayList;
import java.util.List;

/**
 * DeskManager monitor class
 * The desk manager redirects clients to an available desk
 * 
 * @author Petit, Sabir
 *
 */
public class DeskManager {
	
	/**
	 * The number of CaskDesk
	 */
	private final int NB_CASH_DESK;
	
	/**
	 * The maximum number of members per group
	 */
	private final int MAX_MEMBERS_PER_GROUP;
	
	/**
	 * The CashDesk container
	 */
	private CashDesk[] cashdesks;
	
	/**
	 * The number of groups already created
	 */
	private int groupCount;
	
	/**
	 * The Groups container
	 */
	private List<Group> groups;
	
	/**
	 * Number of clients who entered the bowling to display the progression
	 */
	private int clientsEntered = 0;
	
	/**
	 * Number of clients who exited the bowling to display the progression
	 */
	private int clientsExited = 0;
	
	/**
	 * Constructor for the DeskManager
	 * @param nb_cashdesk
	 * @param max
	 */
	public DeskManager(int nb_cashdesk, int max){
		NB_CASH_DESK = nb_cashdesk;
		MAX_MEMBERS_PER_GROUP = max;
		cashdesks = new CashDesk[NB_CASH_DESK];
		for(int i = 0; i < NB_CASH_DESK; i++){
			cashdesks[i] = new CashDesk(i, this);
		}
		groups = new ArrayList<Group>();
		groupCount = -1;
	}
	
	/**
	 * Returns the not full group
	 * if all groups are full, creates a new one
	 * @return the next not full group
	 */
	public synchronized Group getNotFullGroup(){
		for(Group g : groups){
			if(!g.isFull()){
				return g;
			}
		}
		groupCount++;
		Group g = new Group(groupCount,MAX_MEMBERS_PER_GROUP);
		groups.add(g);
		return g;
	}
	
	/**
	 * Method called by a Client Thread to enter a cash desk
	 * @param c the Client Thread
	 * @return The CashDesk entered by the Client Thread
	 */
	public synchronized CashDesk enterClient(Client c){
		/*
		 * If there are no desk available, the client waits
		 */
		while(deskAvailable() == -1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		CashDesk desk = cashdesks[deskAvailable()];
		desk.enterClient(c);
		clientsEntered++;
		notifyAll();
		return desk;
	}
	
	/**
	 * Returns the next Group id
	 * @return the next Group id
	 */
	public synchronized int getNewGroupId(){
		groupCount++;
		return groupCount;
	}
	
	/**
	 * Checks if a desk is available
	 * @return the first available CashDesk id. If no desk is available, returns -1
	 */
	private int deskAvailable(){
		for(CashDesk c : cashdesks){
			if(c.isAvailable())
				return c.getId();
		}
		return -1;
	}
	
	/**
	 * Method called by a Client Thread to pay for his game
	 * @param c The Client Thread
	 * @return the CashDesk where the client will pay
	 */
	public synchronized CashDesk exitClient(Client c){
		while(deskAvailable() == -1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		CashDesk desk = cashdesks[deskAvailable()];
		desk.exitClient(c);
		notifyAll();
		return desk;
	}

	/**
	 * Method called by a Client Thread when he is done paying to exit the CashDesk
	 * @param client The Client Thread
	 * @param desk the CashDesk monitor
	 */
	public synchronized void donePaying(Client client, CashDesk desk) {
		desk.donePaying(client);
		clientsExited++;
		System.err.println("Clients Exited : " + clientsExited + " / " + clientsEntered);
		notifyAll();
	}

}
