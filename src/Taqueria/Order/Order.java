package Taqueria.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Taqueria.TacoInterface.*;

public class Order {

    public List<Taco> tacoList = new ArrayList<>();
    public double totalPrice;
    public int orderID = getOrderID() +1; // hämtar senaste ordernr från filen och lägger till 1
    public String customerName;
    public String customerPhone;
    public Status status;

    public int getOrderID() {
        int tempOrderID;
        try (BufferedReader readOrderID = new BufferedReader(new FileReader("resources/orderID.txt"))){
            tempOrderID = Integer.parseInt(readOrderID.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempOrderID;
    }
    public void setOrderID(int number) {
        try (BufferedWriter writeOrderID = new BufferedWriter(new FileWriter("resources/orderID.txt"))){
            writeOrderID.write(String.valueOf(number));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Order() {
        status = Status.ORDERED;
        // fyll på
        setOrderID(orderID);
    }

    public void addTaco(Taco taco) {
        tacoList.add(taco);
    }

}
