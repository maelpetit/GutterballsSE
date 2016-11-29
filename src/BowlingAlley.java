
public class BowlingAlley {

	private int id;
	//private Group group;
	private boolean available;
	private Bowling bowling;

	public BowlingAlley(int i, Bowling b) {
		id = i;
		available = true;
		bowling = b;
	}

	public boolean isAvailable(){
		return available;
	}

	public void setAvailable(boolean a){
		available = a;
	}

	//	public void setGroup(Group g){
	//		group = g;
	//		//System.out.println("Group "+ group.id() +" : Alley " + id);
	//	}

	public int getId(){
		return id;
	}

	public synchronized void enterClient(Client c) {
		if(!c.getGroup().isPlaying()){
			setAvailable(false);
			c.getGroup().setPlaying(true);
			c.getGroup().setAlley(this);
			System.out.println("Group " + c.getGroup().id() + " is PLAYING on alley "+ getId()+", Client " + c.id() + " reserved it");
		}
	}

	public synchronized void endGame(Group g) {
		if(g.isPlaying()){
			int captain = (int) (Math.random()*g.getMax());
			endGame(g.getMember(captain));
		}		
	}

	private synchronized void endGame(Client c) {
		Group g = c.getGroup();
		g.getAlley().setAvailable(true);
		g.setPlaying(false);
		g.donePlaying();
		System.out.println("Group "+c.getGroup().id()+" DONE PLAYING, Client " + c.id() + " ended the game");
	}

}
