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
		if(!c.getGroup().isPlaying()){
			addGroupToWaitList(c);
			while(availableAlley() == -1){
				//System.out.println("Client "+c.id()+" DANCEFLOOR !" );
				try {
					wait(); //DANCEFLOOR
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!c.getGroup().isPlaying() && !c.getGroup().hasPlayed()){
				BowlingAlley alley = alleys[availableAlley()];
				alley.enterClient(c);
				notifyAll();
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

	public synchronized void endGame(Group group) {
		group.getAlley().endGame(group);
		notifyAll();
	}

}
