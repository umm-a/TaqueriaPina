package Taqueria.TacoInterface;

public class Tortilla implements Taco {
    private final double price = 89.00;
    @Override
    public String getDescription() {
        String description = "Tortilla";
        return description + " (" + price + " kr)";
    }
    @Override
    public double getPrice() {
        return price;
    }
}
