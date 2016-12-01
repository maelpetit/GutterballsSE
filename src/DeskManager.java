public class DeskManager {
	
	private final int MAX_MEMBERS_PER_GROUP;
	private final int NB_CASH_DESK;
	private CashDesk[] cashdesks;
	private Group group;
	private int groupCount;
	private int clientsCount = 0;
	
	
	public DeskManager(CashDesk[] desks, int nb_cashdesk, int max){
		NB_CASH_DESK = nb_cashdesk;
		cashdesks = desks;
		MAX_MEMBERS_PER_GROUP = max;
		groupCount = -1;
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
				wait(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		CashDesk desk = cashdesks[deskAvailable()];
		desk.exitClient(c);
		notifyAll();
		return desk;
	}

}
