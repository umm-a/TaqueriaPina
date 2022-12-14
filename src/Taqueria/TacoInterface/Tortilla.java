package Taqueria.TacoInterface;

public class Tortilla implements Taco {
    private final double price = 98.00;
    @Override
    public String getDescription() {
        String description = "Tortilla";
        return description + " (" + price + ")";
    }
    @Override
    public double getPrice() {
        return price;
    }
}
