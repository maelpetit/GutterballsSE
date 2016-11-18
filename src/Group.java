import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Group {
	
	private final int MAX_MEMBERS_PER_GROUP; 
	private List<Client> members;
	private int id;
	private BowlingAlley alley;
	private boolean playing = false;
	private boolean played = false;
	
	boolean dispShoes = false; //only for display
	
	public Group(int i, int max){
		id = i;
		MAX_MEMBERS_PER_GROUP = max;
		members = new ArrayList<Client>();
	}
	
	public Client getMember(int i){
		return members.get(i);
	}
	
	public int getMax(){
		return MAX_MEMBERS_PER_GROUP;
	}
	
	public int getId(){
		return id;
	}

	public synchronized void addMember(Client c) {
		members.add(c);
		//System.out.println("Client "+ c.id() +" -> Group " + id);
		if(isFull()){
			notifyAll();
		}
	}

	public boolean isFull() {
		return members.size() == MAX_MEMBERS_PER_GROUP;
	}
	
	public boolean isPlaying(){
		return playing;
	}
	
	public boolean allHaveShoes(){
		Iterator<Client> it = members.iterator();
		while(it.hasNext()){
			if(!it.next().hasShoes()){
				return false;
			}
		}
		return true;
	}

	public void setPlaying(boolean p) {
		playing = p;
	}

	public BowlingAlley getAlley() {
		return alley;
	}

	public void setAlley(BowlingAlley alley) {
		this.alley = alley;
		setPlaying(true);
	}

	public boolean hasPlayed() {
		return played;
	}
	
	public void donePlaying(){
		played = true;
	}

}
