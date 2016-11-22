import java.util.ArrayDeque;
import java.util.Queue;

public class Bowling {

	private BowlingAlley[] alleys;
	private Queue<Group> orderedGroups;

	public Bowling(int nbAlleys, int nbGroups){
		alleys = new BowlingAlley[nbAlleys];
		for(int i = 0; i < alleys.length; i++){
			alleys[i] = new BowlingAlley(i, this);
		}
		orderedGroups = new ArrayDeque<Group>();
	}

	public synchronized void enterClient(Client c) {
		addGroupToWaitList(c);
		if(!c.getGroup().isPlaying()){
			while(availableAlley() == -1){
				System.out.println("DANCEFLOOR");
				try {
					wait(); //DANCEFLOOR
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
//			if(c.getGroup().hasPlayed())
//				return;
			if(!c.getGroup().isPlaying() && !c.getGroup().hasPlayed()){
				BowlingAlley alley = alleys[availableAlley()];
				//alley.enterClient(c);
				alley.setAvailable(false);
				c.getGroup().setPlaying(true);
				c.getGroup().setAlley(alley);

				int gameLength = (int) (Math.random()*500) + 500;
				System.out.println("Group " + c.getGroup().id() + " is PLAYING on alley "+alley.getId()+" ("+gameLength+"ms), Client " + c.id() + " reserved it");
				
				try {
					wait(gameLength);//ce wait() se fait reveiller mais on veut pas
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				//c.mySleep(gameLength);
				alley.endGame(c.getGroup());
				notifyAll();
			}
		}
		while(c.getGroup().isPlaying()){
			try {
				wait(); //GAME IN PROGESS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	private void addGroupToWaitList(Client c) {
		for(Group g : orderedGroups){
			if(c.getGroup().id() == g.id())
				return;
		}
		orderedGroups.add(c.getGroup());
	}

	private synchronized int availableAlley() {
		for(BowlingAlley a : alleys){
			if(a.isAvailable())
				return a.getId();
		}
		return -1;
	}

}
