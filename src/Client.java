
public class Client extends Thread{
	
	private int id;
	private Bowling bowling;
	private DeskManager manager;
	private ShoesRoom shoesroom;
	private Group group;
	private boolean hasShoes;

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
		
		int deskTime = 300, shoesTime = 200;
		//System.out.println("Client " + id + " ARRIVED");
		CashDesk desk = manager.enterClient(this);
		
		try {
			Thread.sleep(deskTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//System.out.println("Client " + id + " ENTERED CASHDESK1");
		while(!desk.doneRegistering(this)){
			desk = manager.enterClient(this);
			try {
				Thread.sleep(deskTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
//		desk.doneRegistering(this);
		//System.out.println("Client " + id + " EXITED CASHDESK1");
		
		shoesroom.enterClient(this);
		try {
			Thread.sleep(shoesTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//System.out.println("Client " + id + " SHOES ON");
		
		bowling.enterClient(this);
		//System.out.println("Client " + id + " ENTERED BOWLING");
		int gameTime = (int) (Math.random()*1500 + 500);
		try {
			Thread.sleep(gameTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		bowling.endGame(group, gameTime);
		//System.out.println("Client " + id + " EXITED BOWLING");
		desk = manager.exitClient(this);
		//System.out.println("Client " + id + " ENTERED CASHDESK2");
		try {
			Thread.sleep(deskTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		desk.donePaying(this);
		//System.out.println("Client " + id + " EXITED CASHDESK2");
		shoesroom.exitClient(this);
		//System.out.println("Client " + id + " SHOES OFF");
		try {
			Thread.sleep(shoesTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//System.out.println("Client " + id + " DONE");
		System.out.println("Client "+ id + " IS GONE (group " + group.id() + ")");
	}
	 
	
}