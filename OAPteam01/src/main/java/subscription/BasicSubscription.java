package subscription;

public class BasicSubscription implements Subscription {
    private final double price = 9.99;
    private final String type = "Basic";

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return type;
    }
}


