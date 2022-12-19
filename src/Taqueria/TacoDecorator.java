package Taqueria;
import Taqueria.TacoInterface.Taco;

public abstract class TacoDecorator implements Taco {
    private Taco taco;

    public String getDescription(){
        return ""; 
    }
    public String getDescriptionWithoutPrice(){
        return "";
    }
    public double getPrice(){
        return 0.0;
    }

}
