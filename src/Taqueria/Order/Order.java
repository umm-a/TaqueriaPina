package Taqueria.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Taqueria.TacoInterface.*;
import Taqueria.TacoOrderSystem;

public class Order {


    public List<Taco> tacoList = new ArrayList<>();
    public double totalPrice;
    public static int orderID = getOrderID();
    public String customerName;
    public String customerPhone;
    public Status status;

    public static int getOrderID() {
        int orderID;
        try (BufferedReader readOrderID = new BufferedReader(new FileReader("resources/orderID.txt"))){
            String line = readOrderID.readLine();
            while (line != null) {
                line = readOrderID.readLine();
            }
            orderID = Integer.parseInt(readOrderID.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return orderID;
    }
    public static void setOrderID(int number) {
        try (BufferedWriter writeOrderID = new BufferedWriter(new FileWriter("resources/orderID.txt"))){
            writeOrderID.write(number);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Order() {


        status = Status.ORDERED;
        orderID = getOrderID() +1;
        setOrderID(orderID);

    }

}
