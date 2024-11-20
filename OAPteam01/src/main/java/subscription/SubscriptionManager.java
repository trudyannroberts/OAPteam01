package subscription;

public class SubscriptionManager {
    public Subscription createSubscription(String type) {
        if ("Basic".equalsIgnoreCase(type)) {
            return new BasicSubscription();
        } else if ("Premium".equalsIgnoreCase(type)) {
            return new PremiumSubscription();
        } else {
            return null; // Returner null for ugyldige abonnementstyper
        }
    }
}

