package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingPineapple extends TacoDecorator {
    private final Taco taco;
    private String name = "Ananas";
    private Double price = 10.0;

    public ToppingPineapple(Taco taco) {
        this.taco = taco;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription(){
        return " + " + name + " (" + price + " kr)";
    }

    public double getPrice(){
        return price;
    }

    @Override
    public double squishyFactor() {
        return taco.squishyFactor() + 0.5;
    }

    @Override
    public int getNapkins() {
        return (int) (squishyFactor() / 2);
    }
}
