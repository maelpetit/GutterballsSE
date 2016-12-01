
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
//		try {
//			sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		shoesroom.enterClient(this);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		bowling.enterClient(this);
		int gameTime = (int) (Math.random()*1500 + 500);
		try {
			Thread.sleep(gameTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		bowling.endGame(group, gameTime);
		manager.exitClient(this);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		shoesroom.exitClient(this);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Client "+ id + " IS GONE");
	}
	 
	
}