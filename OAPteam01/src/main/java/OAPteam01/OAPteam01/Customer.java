package OAPteam01.OAPteam01;
/**
 *  Customer class
 *  
 *  @author Erica Laub Varpe
 */
public class Customer extends Person {
	
	private String email;
	private String address;
	
	/**
	 * Creates an object of Customer.
	 */
	Customer(String firstName, String lastName, String email, String address) {
		super(firstName, lastName);
		this.email = email;
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public void display(){
		System.out.println("Information about a customer:");
		super.display();
		System.out.print("Email: " + email);
		System.out.print("Address: " + address); 
	}

}
