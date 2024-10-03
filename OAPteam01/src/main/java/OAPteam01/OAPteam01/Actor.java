package OAPteam01.OAPteam01;

/**
 * Actor class
 * The person class was changed to Customer
 * 
 * @author Erica Laub Varpe
 * @deprecated Not necessary because the actor will be found under film. 
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
