package Taqueria.Order;

import java.util.ArrayList;
import java.util.List;

import Taqueria.TacoInterface.*;

public class Order {

    public static int nextID = 1;

    public List<Taco> tacoList = new ArrayList<>();
    public double totalPrice;
    public final int orderID;
    public String customerName;
    public String customerPhone;
    public Status status;

    public Order() {

        status = Status.ORDERED;
        orderID = nextID;
        nextID++;

    }

}
