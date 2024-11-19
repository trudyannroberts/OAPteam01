package subscription;

public class user {
    private String username;
    private subscription subscription;

    public user(String username) {
        this.username = username;
    }

    public void setSubscription(subscription subscription) {
        this.subscription = subscription;
    }

    public subscription getSubscription() {
        return subscription;
    }

    public String getUsername() {
        return username;
    }
}

