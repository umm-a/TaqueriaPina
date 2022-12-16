package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingSauce extends TacoDecorator {
    private final Taco taco;
    private String description = " + Sås (10 kr)";
    private Double price = 10.0;

    public ToppingSauce(Taco taco) {
        this.taco = taco;
    }
    public String getDescription(){
        return taco.getDescription() + description;
    }

    public double getPrice(){
        return taco.getPrice() + price;
    }
}
