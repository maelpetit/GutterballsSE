
public class ShoesRoom {
	
	int nbShoes;
	
	public ShoesRoom(){
		nbShoes = 10;
	}

	public synchronized void enterClient(Client c) {
		takeShoes(c);
		notifyAll();
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
		System.out.println("Client "+ c.id() +" : shoes ON");
		
		
		//waiting for the whole group to have their shoes
		while(!c.getGroup().allHaveShoes()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		notifyAll();
		//only used to display when all members of a group have shoes
		
//		if(!c.getGroup().dispShoes){
//			System.out.println("Group "+ c.getGroup().id() +" : shoes ALL ON");
//			c.getGroup().dispShoes = true;
//		}
	}
	
	public synchronized void putShoes(Client c){
		//nbShoes++;
		System.out.println("Client "+ c.id() +" SHOES OFF");
		c.setShoes(false);
	}

	public synchronized void exitClient(Client client) {
		putShoes(client);
		notifyAll();
	}

}
