package Topping;

import Taqueria.TacoDecorator;

public class ToppingSauce extends TacoDecorator {
    private final Taco taco;
    private String name;
    private Double price;

    ToppingSauce(Taco taco) {
        this.taco = taco;
    }
    public String getDescription(){
        return taco.getDescription() + " + Sås (10 kr)";
    }

    public Double getPrice(){
        return taco.getPrice() + 10;
    }
}
