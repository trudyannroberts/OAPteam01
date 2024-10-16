package logic;

/**
 * @author Trudy Ann Roberts
 */
public class Payment {
	/**
	 * The user will choose from three payment types, and based on what the user chooses, the method will display the choice.
	 */
	public void cardPayment() {
		System.out.println("Sending you to a third party for processing card payment...");
	}
	
	public void vipps() {
		System.out.println("Please open Vipps to process payment...");
	}
	
	public void invoice() {
		System.out.println("Generating invoice...");
		}
	}