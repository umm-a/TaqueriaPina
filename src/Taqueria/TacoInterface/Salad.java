package Taqueria.TacoInterface;

public class Salad implements Taco {
    private final double price = 98.00;
    @Override
    public String getDescription() {
        String description = "Tacosallad";
        return description + " (" + price + " kr)";
    }
    @Override
    public double getPrice() {
        return price;
    }
}
