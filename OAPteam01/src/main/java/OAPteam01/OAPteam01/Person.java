package OAPteam01.OAPteam01;

/**
* A superclass for the customer and actor class. The shared attributes are created here.
* 
* @author Erica Laub Varpe
*/
public abstract class Person {
	private String firstName;
	private String lastName;

	Person(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Displays information about an object Person
	 */
	public void display() {
		System.out.println("First name: " + getFirstName());
		System.out.println("Last name: " + getLastName());
	}
}
