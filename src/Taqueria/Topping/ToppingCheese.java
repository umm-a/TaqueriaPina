package Taqueria.Topping;

import Taqueria.TacoDecorator;
import Taqueria.TacoInterface.Taco;

public class ToppingCheese extends TacoDecorator {
    private final Taco taco;
    private String name = " + Ost";
    private Double price = 10.0;

    public ToppingCheese(Taco taco) {
        this.taco = taco;
    }
    @Override
    public String getName() {
        return taco.getName() + name;
    }

    public String getDescription(){
        return taco.getDescription() + name + " (" + price + " kr)";
    }

    public double getPrice(){
        return taco.getPrice() + price;
    }

}
