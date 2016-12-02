
public class Gutterballs {
	
	private final static int NB_GROUPS = 50;
	private final static int MAX_MEMBERS_PER_GROUP = 3;
	/**
	 * This ensures that the number of clients is a multiple of the number of members per group
	 */
	private final static int NB_CLIENTS = NB_GROUPS * MAX_MEMBERS_PER_GROUP;
	private final static int NB_ALLEYS = 6;
	private static final int NB_CASH_DESK = 3;
	/**
	 * The number of shoes is greater or equals the number of clients that can simultaneously play in the bowling
	 * ie the number of clients per group multiplied by the number of alleys
	 */
	private final static int NB_SHOES = MAX_MEMBERS_PER_GROUP*NB_ALLEYS *10;//+ ((int)Math.random()*NB_GROUPS);
	
	public static void main(String[] args) {
		
		Bowling bowling = new Bowling(NB_ALLEYS);
		ShoesRoom shoesroom = new ShoesRoom(NB_SHOES);
		Client[] clients = new Client[NB_CLIENTS];
		
		DeskManager manager = new DeskManager(NB_CASH_DESK, MAX_MEMBERS_PER_GROUP);
		Employee employee = new Employee(MAX_MEMBERS_PER_GROUP, shoesroom);
		
		for(int i = 0; i < clients.length; i++){
			clients[i] = new Client(i, bowling, manager, shoesroom, employee);
			clients[i].start();
			int interval = (int) (Math.random()*50 + 50);
			try {
				
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
				
	}
}
