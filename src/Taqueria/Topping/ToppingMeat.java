package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingMeat extends TacoDecorator {
    private final Taco taco;
    private String name;
    private Double price;

    public ToppingMeat(Taco taco) {
        this.taco = taco;
    }

    @Override
    public String getName() {
        return taco.getName() + name;
    }

    public String getDescription(){
        return taco.getDescription() + " + Kött (20 kr)";
    }

    public double getPrice(){
        return taco.getPrice() + 20;
    }
    @Override
    public double squishyFactor() {
        return taco.squishyFactor() + 1;
    }

    @Override
    public int getNapkins() {
        return (int) (squishyFactor() / 2);
    }
}
