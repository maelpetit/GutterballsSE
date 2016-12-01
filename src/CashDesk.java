
public class CashDesk {
	
	//private Client client;
	private Group group;
	private int groupCount;
	private final int MAX_MEMBERS_PER_GROUP;
	private int id;
	
	public CashDesk(int i, int max){
		id = i;
		MAX_MEMBERS_PER_GROUP = max;
		group = new Group(0, MAX_MEMBERS_PER_GROUP);
		groupCount = 1;
	}
	
	public synchronized void enterClient(Client c){
		
//		while(!c.getGroup().isFull()){
//			System.out.println("PROUT");
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		notifyAll();
		
		//System.out.println("Client " + c.id() + " REGISTERED");
		
		//attendre que toute le groupe soit enregistre apres le cashdesk avec un attribut hasRegistered
	}

	public synchronized void exitClient(Client c) {
		
		//System.out.println("Client " + c.id() + " HAS PAID");
	}
}
