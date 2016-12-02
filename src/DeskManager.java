import java.util.ArrayList;
import java.util.List;

public class DeskManager {
	
	private final int NB_CASH_DESK;
	private final int MAX_MEMBERS_PER_GROUP;
	private CashDesk[] cashdesks;
	private int groupCount;
	private List<Group> groups;
	//private int clientsCount = 0;
	
	
	public DeskManager(int nb_cashdesk, int max){
		NB_CASH_DESK = nb_cashdesk;
		MAX_MEMBERS_PER_GROUP = max;
		cashdesks = new CashDesk[NB_CASH_DESK];
		for(int i = 0; i < NB_CASH_DESK; i++){
			cashdesks[i] = new CashDesk(i, MAX_MEMBERS_PER_GROUP, this);
		}
		groups = new ArrayList<Group>();
		groupCount = -1;
	}
	
	public Group getNotFullGroup(){
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
	
	public synchronized CashDesk enterClient(Client c){
		while(deskAvailable() == -1){
			System.err.println("Client " + c.id() + " waiting to enter");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		CashDesk desk = cashdesks[deskAvailable()];
//		while(!cashdesks[clientsCount%NB_CASH_DESK].isAvailable()){
//			System.err.println("Client " + c.id() + " waiting to enter");
//			
//			try {
//				wait(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		CashDesk desk = cashdesks[clientsCount%NB_CASH_DESK];
//		clientsCount++;
		desk.enterClient(c);
		notifyAll();
		return desk;
	}
	
	public synchronized int getNewGroupId(){
		groupCount++;
		return groupCount;
	}
	
	private int deskAvailable(){
		for(CashDesk c : cashdesks){
			if(c.isAvailable())
				return c.getId();
		}
		return -1;
	}
	
	public synchronized CashDesk exitClient(Client c){
		while(deskAvailable() == -1){
			System.err.println("Client " + c.id() + " waiting to exit");
			try {
				wait(2000); //soucis avec le dernier client qui veut partir
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		CashDesk desk = cashdesks[deskAvailable()];
		desk.exitClient(c);
		notifyAll();
		return desk;
	}

	public synchronized void donePaying(Client client, CashDesk desk) {
		desk.donePaying(client);
		notifyAll();
	}

}
