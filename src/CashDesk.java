
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
		if(group == null){
			group = new Group(manager.getNewGroupId(), MAX_MEMBERS_PER_GROUP);
		}
		available = false;
		group.addMember(c);
		c.setGroup(group);
				
		if(group.isFull()){
			System.out.println("Group "+ group.id() +" filled");
			group = null;
		}
	}
	
	public synchronized boolean doneRegistering(Client c) {
		available = true;
		System.out.println("Client " + c.id() + " REGISTERED at CashDesk " + id);
		int nbWait = 0;
		while(!c.getGroup().isFull()){
			System.err.println("Client " + c.id() + " waiting for the rest of the group " + c.getGroup().id() + " at CashDesk " + id);
			try {
				wait(1000); //probleme si on augmente l'intervalle d'arrivée des clients et la durée de l'inscription, mais il faudrait beaucoup l'augmenter
				nbWait++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(nbWait > MAX_MEMBERS_PER_GROUP && id != 0){
				System.out.println("Client " + c.id() + " REGISTRATION CANCELED at CashDesk " + id);
				c.getGroup().removeMember(c);
				return false;
			}
		}
		notifyAll();
		return true;
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
