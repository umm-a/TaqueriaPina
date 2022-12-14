package Taqueria;

import Taqueria.Order.Order;
import Taqueria.Order.Status;

import java.io.*;
import java.util.ArrayList;

public class TacoOrderSystem {

    ArrayList<Order> activeOrderList;
    ArrayList<Order> finishedOrderList;
    boolean run = true;
    String scannerInput;
    int orderID;


    public TacoOrderSystem(){

            KitchenGUI kitchenGUI = new KitchenGUI();
            while (run) {
                Scanner scan = new Scanner(System.in);
                scannerInput = scan.toString();
                System.out.println("Välj ett val med 1, 2, eller 3: \n1. Skapa beställning: \n2. Sök beställning: \n3. Ändra status på beställning: ");

                switch (scannerInput){
                    case "1": createOrder();

                    case "2":
                  //      searchOrder();
                    case "3":
                }
             }
        }

    public void createOrder(){
        //getOrderID() -> sätt orderID på objektet från textfilen
        //skapa order...
        // när vi har fått tillbaka order-objektet, avsluta med att uppdatera orderfilen med nya ID: görs nu i Order-class
    }

    public String searchOrdersByStatus(Status status, ArrayList<Order> orderList){ //vill vi kunna söka i bägge listor?
        String temp = "";
        return temp;
    }

    public String searchOrder(String id, ArrayList<Order> activeOrderList, ArrayList<Order> finishedOrderList){ //är metodnamnet passande?
        String temp = "";
        return temp;
    }
    public void changeOrderStatus(int id, Status status){

    }
    public void updateKitchenGUI(ArrayList<Order> activeOrderList){

    }
}
