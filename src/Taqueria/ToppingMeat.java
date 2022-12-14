package Taqueria;

public class ToppingMeat extends TacoDecorator {
    private final Taco taco;
    private String name;
    private Double price;

    ToppingMeat(Taco taco) {
        this.taco = taco;
    }
    public String getDescription(){
        return taco.getDescription() + " + Kött (20 kr)";
    }

    public Double getPrice(){
        return taco.getPrice() + 20;
    }
}
