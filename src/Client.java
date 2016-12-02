
/**
 * The Client Thread class
 * 
 * @author Petit, Sabir
 *
 */
public class Client extends Thread{
	
	/**
	 * The Client id
	 */
	private int id;
	
	/**
	 * The Bowling monitor
	 */
	private Bowling bowling;
	
	/**
	 * The DeskManager monitor
	 */
	private DeskManager manager;
	
	/**
	 * The Client Group monitor
	 */
	private Group group;
	
	/**
	 * The Employee monitor
	 */
	private Employee employee;
	
	/**
	 * Boolean Shoes Attribute
	 */
	private boolean hasShoes;
	
	/**
	 * The ShoesRoom monitor
	 */
	private ShoesRoom shoesroom;

	/**
	 * Constructor for a Client
	 * 
	 * @param i The Client id
	 * @param b The Bowling monitor
	 * @param man The DeskManager monitor
	 * @param sr The ShoesRoom monitor
	 * @param emp The Employee monitor
	 */
	public Client(int i, Bowling b, DeskManager man, ShoesRoom sr, Employee emp){
		id = i;
		bowling = b;
		manager = man;
		shoesroom = sr;
		hasShoes = false;
		employee = emp;
	}
	
	/**
	 * Getter for the client id ( != Thread.getId())
	 * @return The Client id
	 */
	public int id(){
		return id;
	}
	
	/**
	 * Getter for the Shoes attribute
	 * @return True if the Client has shoes on, False else
	 */
	public boolean hasShoes() {
		return hasShoes;
	}

	/**
	 * Setter for the shoes attribute
	 * @param hasShoes 
	 */
	public void setShoes(boolean hasShoes) {
		this.hasShoes = hasShoes;
	}
	
	/**
	 * Setter for the Group monitor
	 * @param g the group monitor
	 */
	public void setGroup(Group g){
		group = g;
	}
	
	/**
	 * Getter for the Group monitor
	 * @return the Group monitor
	 */
	public Group getGroup() {
		return group;
	}
	
	public void run(){
		
		int deskTime = 300, shoesTime = 200;
		CashDesk desk = manager.enterClient(this);
		try {
			Thread.sleep(deskTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		desk.doneRegistering(this);
		
		employee.askTakeShoes(this);
		
		shoesroom.takeShoes(this);
		try {
			Thread.sleep(shoesTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		group.waitShoes(this);
		
		bowling.enterClient(this);
		
		int gameTime = (int) (Math.random()*1000 + 3000);
		try {
			Thread.sleep(gameTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		bowling.endGame(group, gameTime);
		
		desk = manager.exitClient(this);
		try {
			Thread.sleep(deskTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		manager.donePaying(this, desk);
		
		employee.askPutShoes(this);
		try {
			Thread.sleep(shoesTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//System.out.println("Client "+ id + " IS GONE (group " + group.id() + ")");
	}
	 
	
}