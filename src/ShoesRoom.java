
public class ShoesRoom {
	
	int nbShoes;
	int clientsShoes = 0;
	
	public ShoesRoom(){
		nbShoes = 12;
	}

	public synchronized void enterClient(Client c) {
		takeShoes(c);
		clientsShoes++;
		notifyAll();
	}
	
	public synchronized void takeShoes(Client c){
		while(nbShoes < 1){
			System.err.println("Client "+c.id()+" waiting for shoes");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nbShoes--;
		c.setShoes(true);
		System.out.println("Client "+ c.id() +" : SHOES ON");
		//c.getGroup().waitShoes(c);
//		//waiting for the whole group to have their shoes
//		while(!c.getGroup().allHaveShoes()){
//			System.err.println("Client " + c.id() + " waiting for the whole group " +c.getGroup().id() + " to have their shoes ");
//			try {
//				wait(10000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		notifyAll();
	}
	
	public synchronized void putShoes(Client c){
		nbShoes++;
		System.out.println("Client "+ c.id() +" : SHOES OFF");
		c.setShoes(false);
		notifyAll();
	}

	public synchronized void exitClient(Client client) {
		putShoes(client);
		clientsShoes++;
	}

}
