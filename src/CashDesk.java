
public class CashDesk {
	
	//private Client client;
	private Group group;
	private int groupCount;
	private final int MAX_MEMBERS_PER_GROUP;
	
	public CashDesk(int max){
		MAX_MEMBERS_PER_GROUP = max;
		group = new Group(0, MAX_MEMBERS_PER_GROUP);
		groupCount = 1;
	}
	
	public synchronized void enterClient(Client c){
		//subscription
		group.addMember(c);
		c.setGroup(group);
		Group g = group;
		if(group.isFull()){
			System.out.println("Group "+ group.getId() +" filled");
			group = new Group(groupCount, MAX_MEMBERS_PER_GROUP);
			groupCount++;
		}
		notifyAll();
		//The client is waiting for his group to fill up
		while(!g.isFull()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
