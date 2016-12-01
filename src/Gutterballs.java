
public class Gutterballs {
	
	final static int NB_CLIENTS = 30; //Attention le nombre de clients doit etre divisible par le nombre de clients par groupe
	final static int MAX_MEMBERS_PER_GROUP = 5;
	final static int NB_ALLEYS = 3;
	private static final int NB_CASH_DESK = 3;
	
	public static void main(String[] args) {
		
		Bowling bowling = new Bowling(NB_ALLEYS, NB_CLIENTS/MAX_MEMBERS_PER_GROUP);
		CashDesk[] cashdesks = new CashDesk[NB_CASH_DESK];
		ShoesRoom shoesroom = new ShoesRoom();
		Client[] clients = new Client[NB_CLIENTS];
		
		for(int i = 0; i < NB_CASH_DESK; i++){
			cashdesks[i] = new CashDesk(i, MAX_MEMBERS_PER_GROUP);
		}
		DeskManager manager = new DeskManager(cashdesks, NB_CASH_DESK, MAX_MEMBERS_PER_GROUP);
		
		for(int i = 0; i < clients.length; i++){
			clients[i] = new Client(i, bowling, manager, shoesroom);
			clients[i].start();
			int interval = (int) (Math.random()*50 + 50);
			try {
				
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
}
