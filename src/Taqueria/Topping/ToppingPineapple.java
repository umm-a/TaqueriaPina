package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingPineapple extends TacoDecorator {
    private final Taco taco;
    private String name;
    private Double price;

    ToppingPineapple(Taco taco) {
        this.taco = taco;
    }
    public String getDescription(){
        return taco.getDescription() + " + Ananas (10 kr)";
    }

    public double getPrice(){
        return taco.getPrice() + 10;
    }
}
