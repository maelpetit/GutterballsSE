public class DeskManager {
	
	private final int MAX_MEMBERS_PER_GROUP;
	private final int NB_CASH_DESK;
	private CashDesk[] cashdesks;
	private Group group;
	private int groupCount;
	private int clientCount;
	
	
	public DeskManager(CashDesk[] desks, int nb_cashdesk, int max){
		NB_CASH_DESK = nb_cashdesk;
		cashdesks = desks;
		MAX_MEMBERS_PER_GROUP = max;
		group = new Group(0, MAX_MEMBERS_PER_GROUP);
		groupCount = 1;
		clientCount = 0;
	}
	
	public synchronized void enterClient(Client c){
		
		group.addMember(c);
		c.setGroup(group);
		
		while(!c.getGroup().isFull()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		notifyAll();
		
		cashdesks[clientCount%NB_CASH_DESK].enterClient(c);
		
		if(group.isFull()){
			System.out.println("Group "+ group.id() +" filled");
			group = new Group(groupCount, MAX_MEMBERS_PER_GROUP);
			groupCount++;
		}
		
	}
	
	public synchronized void exitClient(Client c){
		
		
		
	}
}
