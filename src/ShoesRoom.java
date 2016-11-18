import java.util.ArrayList;
import java.util.List;


public class ShoesRoom {
	
	int nbShoes;
	List<Client> clients;
	
	public ShoesRoom(){
		clients = new ArrayList<Client>();
		nbShoes = 10;
	}

	public synchronized void enterClient(Client c) {
		clients.add(c);
		takeShoes(c);
	}
	
	public synchronized void takeShoes(Client c){
		while(nbShoes < 1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//nbShoes--;
		c.setShoes(true);
		//System.out.println("Client "+ c.id() +" : shoes ON");
		
		//waiting for the whole group to have their shoes
		while(!c.getGroup().allHaveShoes()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//only used to display when all members of a group have shoes
		
//		if(!c.getGroup().dispShoes){
//			System.out.println("Group "+ c.getGroup().getId() +" : shoes ALL ON");
//			c.getGroup().dispShoes = true;
//		}
		
		notifyAll();
	}
	
	public synchronized void putShoes(Client c){
		//nbShoes++;
		c.setShoes(false);
		notifyAll();
	}

}
