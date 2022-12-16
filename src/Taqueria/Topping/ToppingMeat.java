package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingMeat extends TacoDecorator {
    private final Taco taco;
    private String description = " + Kött (20 kr)";
    private Double price = 10.0;

    public ToppingMeat(Taco taco) {
        this.taco = taco;
    }
    public String getDescription(){
        return taco.getDescription() + description;
    }

    public double getPrice(){
        return taco.getPrice() + price;
    }
}
