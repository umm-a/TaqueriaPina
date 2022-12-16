package Taqueria.TacoInterface;

public class Salad implements Taco {

    private final String name = "Tacosallad";
    private final double price = 79.00;

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return name + " (" + price + " kr)";
    }
    @Override
    public String getDescriptionWithoutPrice() {
        return name;
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
