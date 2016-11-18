
public class Client extends Thread{
	
	private int id;
	private Bowling bowling;
	private CashDesk cashdesk;
	private ShoesRoom shoesroom;
	private Group group;
	private boolean hasShoes;

	public Client(int i, Bowling b, CashDesk cd, ShoesRoom sr){
		id = i;
		bowling = b;
		cashdesk = cd;
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
		cashdesk.enterClient(this);
		shoesroom.enterClient(this);
		bowling.enterClient(this);
	}
	 
	
}