package subscription;



public class premiumSubscription implements subscription {
    private final double price = 15.99;
    private final String type = "Premium";

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return type;
    }
}

