package logic;

/**
 * Represents the payment options available in the media streaming service.
 * 
 * This class provides different payment methods such as card payment, Vipps, and invoice.
 * Depending on the user's choice, the appropriate payment process is initiated. The actual 
 * payment processing is not implemented here but simulated with messages.
 * 
 * @author Trudy Ann Roberts
 */
public class Payment {
    
    /**
     * Simulates processing a card payment by redirecting the user to a third-party processor.
     * 
     * When this method is called, the system displays a message indicating that the user is 
     * being redirected to a third-party service to handle the card payment.
     */
    public void cardPayment() {
        System.out.println("Sending you to a third party for processing card payment...");
    }
    
    /**
     * Simulates the process of using Vipps for payment.
     * 
     * When this method is called, the user is prompted to open the Vipps app for payment. 
     */
    public void vipps() {
        System.out.println("Please open Vipps to process payment...");
    }
    
    /**
     * Simulates generating an invoice for payment.
     * 
     * When this method is called, the system displays a message indicating that an invoice is being generated.
     */
    public void invoice() {
        System.out.println("Generating invoice...");
    }
}
