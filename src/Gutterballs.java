
public class Gutterballs {
	
	final static int NB_CLIENTS = 13; //Attention le nombre de clients doit etre divisible par le nombre de clients par groupe
	final static int MAX_MEMBERS_PER_GROUP = 3;
	final static int NB_ALLEYS = 3;
	
	public static void main(String[] args) {
		
		Bowling bowling = new Bowling(NB_ALLEYS);
		CashDesk cashdesk = new CashDesk(MAX_MEMBERS_PER_GROUP);
		ShoesRoom shoesroom = new ShoesRoom();
		
		Client[] clients = new Client[NB_CLIENTS];
		for(int i = 0; i < clients.length; i++){
			clients[i] = new Client(i, bowling, cashdesk, shoesroom);
			clients[i].start();
		}
				
	}
}
