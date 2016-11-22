
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
		setAvailable(false);
		c.getGroup().setPlaying(true);
		c.getGroup().setAlley(this);
		int gameLength = (int) (Math.random()*500) + 500;
		System.out.println("Group " + c.getGroup().id() + " is PLAYING on alley "+ getId()+" ("+gameLength+"ms), Client " + c.id() + " reserved it");
		
		try {
			wait(gameLength);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		endGame(c.getGroup());
	}

	public void endGame(Group g) {
		if(g.isPlaying()){
			int captain = (int) (Math.random()*g.getMax());
			endGame(g.getMember(captain));
		}		
	}

	private void endGame(Client c) {
		Group g = c.getGroup();
		g.getAlley().setAvailable(true);
		g.setPlaying(false);
		g.donePlaying();
		System.out.println("Group "+c.getGroup().id()+" DONE PLAYING, Client " + c.id() + " ended the game");
	}

}
