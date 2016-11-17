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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//nbShoes--;
		c.setShoes(true);
		System.out.println("Client "+ c.id() +" : shoes ON");
		//waiting for the whole group to have their shoes
		while(!c.getGroup().allHaveShoes()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(!c.getGroup().dispShoes){
			System.out.println("Group "+ c.getGroup().getId() +" : shoes ALL ON");
			c.getGroup().dispShoes = true;
		}
		notifyAll();
	}
	
	public synchronized void putShoes(){
		//nbShoes++;
		notifyAll();
	}

}
