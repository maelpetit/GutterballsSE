
public class Bowling {

	private BowlingAlley[] alleys;

	public Bowling(int nbAlleys){
		alleys = new BowlingAlley[nbAlleys];
		for(int i = 0; i < alleys.length; i++){
			alleys[i] = new BowlingAlley(i);
		}
	}

	public synchronized void enterClient(Client c) {
		if(!c.getGroup().isPlaying()){
			while(availableAlley() == -1){
				try {
					wait(); //DANCEFLOOR
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(c.getGroup().hasPlayed())
				return;
			BowlingAlley alley = alleys[availableAlley()];
			alley.setGroup(c.getGroup());
			alley.setAvailable(false);
			c.getGroup().setAlley(alley);
			
			System.out.println("Group " + c.getGroup().getId() + " is PLAYING, client.id=" + c.id());
			try {
				wait(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endGame(c.getGroup());
		}
	}
	
	public synchronized void endGame(Group g){
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
		System.out.println("Group "+c.getGroup().getId()+" DONE PLAYING");
		notifyAll();
	}

	private int availableAlley() { //synchronized ?
		for(BowlingAlley a : alleys){
			if(a.isAvailable())
				return a.getId();
		}
		return -1;
	}

}
