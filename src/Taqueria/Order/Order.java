package Taqueria.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Taqueria.TacoInterface.*;

public class Order {

    private List<Taco> tacoList = new ArrayList<>();
    private double totalPrice;
    private String customerName;
    private String customerPhone;
    private Status status;

    public Order(List<Taco> tacoList, double totalPrice, String customerName, String customerPhone, Status status) {
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

    public void addTaco(Taco taco) {
        tacoList.add(taco);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
