package Taqueria;
import Taqueria.TacoInterface.Taco;

public abstract class TacoDecorator implements Taco {
    private Taco taco;

    public String getDescription(){
        return ""; //Kommer aldrig att användas
    }

    public double getPrice(){
        return 0.0; //Kommer aldrig att användas
    }

}
