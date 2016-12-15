
/**
 * ShoesRoom monitor class
 * The shoesroom handles the shoes stock
 * 
 * @author Petit, Sabir
 *
 */
public class ShoesRoom {
	
	/**
	 * The current stock of shoes
	 */
	int nbShoes;
	
	/**
	 * Constructor for the ShoesRoom
	 * @param nb the number of shoes initially
	 */
	public ShoesRoom(int nb){
		nbShoes = nb;
	}
	
	/**
	 * Method called by a Client Thread to take his shoes
	 * @param c the Client Thread
	 */
	public synchronized void takeShoes(Client c){
		nbShoes--;
		c.setShoes(true);
		System.out.println("Client "+ c.id() +" : SHOES ON");

	}
	
	/**
	 * Returns true if there are shoes available
	 * @return nbShoes > 0
	 */
	public boolean shoesAvailable(){
		return nbShoes > 0;
	}
	
	/**
	 * Method called by a Client Thread to put back his shoes
	 * @param c
	 */
	public synchronized void putShoes(Client c){
		nbShoes++;
		System.out.println("Client "+ c.id() +" : SHOES OFF");
		c.setShoes(false);
	}

}
