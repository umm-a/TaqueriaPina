package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingCheese extends TacoDecorator {
    private final Taco taco;
    private String description = " + Ost (10 kr)";
    private Double price = 10.0;

    public ToppingCheese(Taco taco) {
        this.taco = taco;
    }
    public String getDescription(){
        return taco.getDescription() + description;
    }

    public double getPrice(){
        return taco.getPrice() + price;
    }

}
