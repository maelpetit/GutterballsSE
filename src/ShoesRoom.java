
public class ShoesRoom {
	
	int nbShoes;
	int clientsShoes = 0;
	
	public ShoesRoom(){
		nbShoes = 10;
	}

	public synchronized void enterClient(Client c) {
		takeShoes(c);
		clientsShoes++;
		System.err.println("Client "+c.id()+" Shoes IN " + clientsShoes);
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
		System.out.println("Client "+ c.id() +" : SHOES ON");
		
		
		//waiting for the whole group to have their shoes
		while(!c.getGroup().allHaveShoes()){
			System.err.println("Client " + c.id() + " waiting for the whole group " +c.getGroup().id() + " to have their shoes ");
			try {
				wait(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		notifyAll();
	}
	
	public synchronized void putShoes(Client c){
		//nbShoes++;
		System.out.println("Client "+ c.id() +" : SHOES OFF");
		c.setShoes(false);
	}

	public synchronized void exitClient(Client client) {
		putShoes(client);
		clientsShoes++;
		System.err.println("Client "+client.id()+" Shoes OUT " + clientsShoes);
		notifyAll();
	}

}
