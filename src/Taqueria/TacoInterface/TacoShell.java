package Taqueria.TacoInterface;

public class TacoShell implements Taco {
    private final String name = "Hårt skal";
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
