
public class Gutterballs {
	
	final static int NB_CLIENTS = 100; //Attention le nombre de clients doit etre divisible par le nombre de clients par groupe
	final static int MAX_MEMBERS_PER_GROUP = 4;
	final static int NB_ALLEYS = 1;
	private static final int NB_CASH_DESK = 3;
	
	public static void main(String[] args) {
		
		Bowling bowling = new Bowling(NB_ALLEYS, NB_CLIENTS/MAX_MEMBERS_PER_GROUP);
		ShoesRoom shoesroom = new ShoesRoom();
		Client[] clients = new Client[NB_CLIENTS];
		
		DeskManager manager = new DeskManager(NB_CASH_DESK, MAX_MEMBERS_PER_GROUP);
		
		for(int i = 0; i < clients.length; i++){
			clients[i] = new Client(i, bowling, manager, shoesroom);
			clients[i].start();
			int interval = (int) (Math.random()*1000 + 100);
			try {
				
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
				
	}
}
