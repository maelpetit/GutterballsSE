
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
			while(availableAlley() == -1 && !c.getGroup().isPlaying()){
				try {
					wait(); //DANCEFLOOR
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
//			if(c.getGroup().hasPlayed())
//				return;
			if(!c.getGroup().isPlaying()){
				BowlingAlley alley = alleys[availableAlley()];
				//alley.setGroup(c.getGroup());
				alley.setAvailable(false);
				c.getGroup().setAlley(alley);
				
				int gameLength = (int) (Math.random()*500) + 500;
				System.out.println("Group " + c.getGroup().id() + " is PLAYING on alley "+alley.getId()+" ("+gameLength+"ms), Client " + c.id() + " reserved it");
				try {
					wait(gameLength);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				endGame(c.getGroup());
			}
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
		System.out.println("Group "+c.getGroup().id()+" DONE PLAYING, Client " + c.id() + " ended the game");
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
