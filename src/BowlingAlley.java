
public class BowlingAlley {

	private int id;
	private Group group;
	private boolean available;
	
	public BowlingAlley(int i) {
		id = i;
		available = true;
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public void setAvailable(boolean a){
		available = a;
	}
	
	public void setGroup(Group g){
		group = g;
		//System.out.println("Group "+ group.getId() +" : Alley " + id);
	}
	
	public int getId(){
		return id;
	}

}
