package subscription;

public class subscriptionManager {
    public subscription createSubscription(String type) {
        if ("Basic".equalsIgnoreCase(type)) {
            return new basicSubscription();
        } else if ("Premium".equalsIgnoreCase(type)) {
            return new premiumSubscription();
        } else {
            return null; // Return null for invalid type
        }
    }

    public void changeSubscription(user user, subscription subscription) {
        user.setSubscription(subscription);
    }
}
