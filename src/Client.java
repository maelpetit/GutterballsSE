
public class Client extends Thread{
	
	private int id;
	private Bowling bowling;
	private DeskManager manager;
	private ShoesRoom shoesroom;
	private Group group;
	private boolean hasShoes;
	private boolean registered;

	public Client(int i, Bowling b, DeskManager man, ShoesRoom sr){
		id = i;
		bowling = b;
		manager = man;
		shoesroom = sr;
		hasShoes = false;
	}
	
	public int id(){
		return id;
	}
	
	public boolean hasRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public void mySleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean hasShoes() {
		return hasShoes;
	}

	public void setShoes(boolean hasShoes) {
		this.hasShoes = hasShoes;
	}
	
	public void setGroup(Group g){
		group = g;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void run(){
		manager.enterClient(this);
	
		
		shoesroom.enterClient(this);
		bowling.enterClient(this);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		bowling.endGame(group);
		manager.exitClient(this);
		shoesroom.exitClient(this);
		System.out.println("Client "+ id + " IS GONE");
	}
	 
	
}