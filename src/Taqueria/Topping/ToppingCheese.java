package Taqueria.Topping;

import Taqueria.TacoDecorator;
import TacoInterface.Taco;

public class ToppingCheese extends TacoDecorator {
    private final Taco taco;
    private String name;
    private Double price;

    ToppingCheese(Taco taco) {
        this.taco = taco;
    }
    public String getDescription(){
        return taco.getDescription() + " + Ost (10 kr)";
    }

    public double getPrice(){
        return taco.getPrice() + 10;
    }
}
