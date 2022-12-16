package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingSauce extends TacoDecorator {
    private final Taco taco;
    private String name;
    private Double price;

    public ToppingSauce(Taco taco) {
        this.taco = taco;
    }

    @Override
    public String getName() {
        return taco.getName() + name;
    }

    public String getDescription(){
        return taco.getDescription() + " + Sås (10 kr)";
    }

    public double getPrice(){
        return taco.getPrice() + 10;
    }

    @Override
    public double squishyFactor() {
        return taco.squishyFactor() + 2;
    }

    @Override
    public int getNapkins() {
        return (int) (squishyFactor() / 2);
    }
}
