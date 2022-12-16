package Taqueria.TacoInterface;

public class Tortilla implements Taco {
    private final String name = "Tortilla";
    private final double price = 89.00;
    @Override
    public String getDescription() {
        return name + " (" + price + " kr)";
    }
    @Override
    public double getPrice() {
        return price;
    }
}
