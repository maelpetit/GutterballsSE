
/**
 * CashDesk monitor class
 * A CashDesk registers a client in a group one at a time
 * 
 * @author Petit, Sabir
 *
 */
public class CashDesk {
	
	/**
	 * The CashDesk id
	 */
	private int id;
	
	/**
	 * Boolean available attribute (lock)
	 */
	private boolean available;
	
	/**
	 * DeskManager monitor
	 */
	private DeskManager manager;
	
	/**
	 * Constructor for a CashDesk
	 * 
	 * @param i the CashDesk id
	 * @param deskmanager
	 */
	public CashDesk(int i, DeskManager deskmanager){
		id = i;
		manager = deskmanager;
		available = true;
	}
	
	/**
	 * Method called by a Client Thread to register in a group
	 * @param c The Client Thread
	 */
	public synchronized void enterClient(Client c){
		available = false;
		Group group = manager.getNotFullGroup();
		group.addMember(c);
		c.setGroup(group);
				
		if(group.isFull()){
			System.out.println("Group "+ group.id() +" filled");
		}
	}
	
	/**
	 * Method called by the Client Thread registering to free the CashDesk and wait for his partners
	 * @param c the Client Thread
	 */
	public synchronized void doneRegistering(Client c) {
		available = true;
		System.out.println("Client " + c.id() + " REGISTERED at CashDesk " + id);
		while(!c.getGroup().isFull()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}

	/**
	 * Method called by a Client Thread to pay for his game and lock the desk
	 * @param c
	 */
	public synchronized void exitClient(Client c) {
		available = false;
		//System.out.println("Client " + c.id() + " IS PAYING");
	}
	
	/**
	 * Method called by a Client Thread to free the monitor
	 * @param c the Client Thread
	 */
	public synchronized void donePaying(Client c){
		available = true;
		System.out.println("Client " + c.id() + " HAS PAID at CashDesk " + id);
		notifyAll();
	}

	/**
	 * Getter for the CashDesk id
	 * @return the CashDesk id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns true if the desk is available
	 * @return true 
	 */
	public synchronized boolean isAvailable() {
		return available;
	}
}
