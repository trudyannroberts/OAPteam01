package OAPteam01.OAPteam01;

/**
 * Actor class
 * 
 * @author Erica Laub Varpe
 */
public class Actor extends Person{

	/**
	 * Creates an object of Actor
	 */
	Actor(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	@Override
	public void display() {
		System.out.println("Information about an actor:");
		super.display();
	}
}
