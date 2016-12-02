
public class CashDesk {
	
	private Group group;
	private final int MAX_MEMBERS_PER_GROUP;
	private int id;
	private boolean available;
	private DeskManager manager;
	
	public CashDesk(int i, int max, DeskManager deskmanager){
		id = i;
		MAX_MEMBERS_PER_GROUP = max;
		manager = deskmanager;
		group = null;
		available = true;
	}
	
	public synchronized void enterClient(Client c){
		available = false;
		group = manager.getNotFullGroup();
		group.addMember(c);
		c.setGroup(group);
				
		if(group.isFull()){
			System.out.println("Group "+ group.id() +" filled");
		}
		notifyAll();
	}
	
	public synchronized void doneRegistering(Client c) {
		available = true;
		System.out.println("Client " + c.id() + " REGISTERED at CashDesk " + id);
		while(!c.getGroup().isFull()){
			System.err.println("Client " + c.id() + " waiting for the rest of the group " + c.getGroup().id() + " at CashDesk " + id);
			try {
				wait(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}

	public synchronized void exitClient(Client c) {
		available = false;
		System.out.println("Client " + c.id() + " IS PAYING");
	}
	
	public synchronized void donePaying(Client c){
		available = true;
		System.out.println("Client " + c.id() + " HAS PAID at CashDesk " + id);
	}

	public int getId() {
		return id;
	}

	public synchronized boolean isAvailable() {
		return available;
	}
}
