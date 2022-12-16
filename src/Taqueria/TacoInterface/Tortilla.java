package Taqueria.TacoInterface;

public class Tortilla implements Taco {
    private final String name = "Tortilla";
    private final double price = 89.00;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return name + " (" + price + " kr)";
    }
    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double squishyFactor() {
        return 1;
    }

    @Override
    public int getNapkins() {
        return (int) squishyFactor();
    }
}
