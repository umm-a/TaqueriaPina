package Taqueria.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Taqueria.TacoInterface.*;

public class Order {

    private List<Taco> tacoList = new ArrayList<>();
    private double totalPrice;
    private String customerName;
    private String customerPhone;
    private Status status;


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    private int orderID;

    public Order(int orderID, List<Taco> tacoList, double totalPrice, String customerName, String customerPhone, Status status) {
        this.orderID = orderID;
        this.tacoList = tacoList;
        this.totalPrice = totalPrice;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.status = status;
    }
    public Order() {
    }

    public List<Taco> getTacoList() {
        return tacoList;
    }

    public void setTacoList(List<Taco> tacoList) {
        this.tacoList = tacoList;
    }
    public String[] printTacoList() {
        String[] list = new String[tacoList.size()];
        for (Taco taco : tacoList) {
            list[tacoList.indexOf(taco)] = taco.toString();
        }
        return list;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static synchronized int getOrderIDFromFile() {
        int tempOrderID;
        try (BufferedReader readOrderID = new BufferedReader(new FileReader("resources/orderID.txt"))){
            tempOrderID = Integer.parseInt(readOrderID.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempOrderID;
    }
    public static synchronized void writeOrderIDToFile(int number) {
        try (BufferedWriter writeOrderID = new BufferedWriter(new FileWriter("resources/orderID.txt"))){
            writeOrderID.write(String.valueOf(number));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTaco(Taco taco) {
        tacoList.add(taco);
    }
    public List<Taco> getTacoList () {
        return tacoList;
    }

    @Override
    public String toString() {
        return "Order: #" + getOrderID() + "\n" +
                "Kundens namn: " + getCustomerName() + "\n" +
                "Kundens telefon: " + getCustomerPhone() + "\n" +
                "Antal artiklar i beställningen: " + tacoList.size() + "\n" +
                "Beställning: " + Arrays.toString(printTacoList()) + "\n" + // TODO: Fixa så att artiklarna skrivs ut en och en, kanske ej array
                "Totalpris: " + getTotalPrice() + "\n" +
                "Status: " + getStatus() + "\n";
    }
}
