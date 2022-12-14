package Taqueria;

import Taqueria.Order.Order;
import Taqueria.Order.Status;
import Taqueria.TacoInterface.Salad;
import Taqueria.TacoInterface.Taco;
import Taqueria.TacoInterface.TacoShell;
import Taqueria.TacoInterface.Tortilla;
import Taqueria.Topping.ToppingCheese;
import Taqueria.Topping.ToppingMeat;
import Taqueria.Topping.ToppingPineapple;
import Taqueria.Topping.ToppingSauce;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
                System.out.println("Välj ett val med 1, 2, eller 3: \n1. Skapa beställning \n2. Sök beställning \n3. Ändra status på beställning ");
                scannerInput = scan.nextLine();
                switch (scannerInput){
                    case "1": startOrder();

                    case "2":
                        //      searchOrder();
                        run=false;
                        break;
                    case "3":
                        run=false;
                        break;
                    default:
                        System.out.println("Ej giltigt val");
                }
             }
        }
/*
        public void createOrder(){
        boolean addingOrders=true;
        Order currentOrder = new Order();
        startOrder(currentOrder);
     //   currentOrder.tacoList.add(startOrder());
        Scanner scan = new Scanner(System.in);
        String scannerInput = scan.nextLine();
        while (addingOrders) {
            System.out.println("Vill du lägga till fler tacos i beställningen? 1=Ja, Annat=Nej");
            if (scannerInput.equals("1")) {
                startOrder(currentOrder);
              //  currentOrder.tacoList.add(startOrder());
            } else {
                addingOrders = false;
            }
        }
        activeOrderList.add(currentOrder);
        }
        */

    public void startOrder(){ //ta in Order som argument ev.
        boolean create=true;
        while (create){
            Scanner scan = new Scanner(System.in);
            System.out.println("Välj bas: 1. Tortilla, 2. Sallad, 3. Taco Shell");
            scannerInput = scan.nextLine();
            switch (scannerInput){
                case "1":
                    Taco taco = new Tortilla();
                    addToppingsToOrder(taco);
                    create=false;
             //       return taco; todo -> man kan göra på olika vis, tänkte lägga ihop med Order men blev problematiskt - låt oss kika tillsammans
                case "2":
                    Taco taco2 = new Salad();
                    addToppingsToOrder(taco2);
                    create=false;
             //       return taco2;
                case "3":
                    Taco taco3 = new TacoShell();
                    addToppingsToOrder(taco3);
             //       return taco3;
                    create=false;
                    //break;
                case "4":
                    System.out.println("Inget giltigt val angivet, försök igen");
                    break;
            }
        }
    }
        public void addToppingsToOrder(Taco taco){ //ta ev. in Order som argument
        boolean addToppings=true;
            while (addToppings) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Välj toppings!\n1.Ost, 2.Köttfärs, 3.Ananas, 4.Sås\n (Om du är nöjd, skriv \"5\")");
                scannerInput = scan.nextLine();
                switch (scannerInput) {
                    case "1":
                    taco = new ToppingCheese(taco);
                    addToppingsToOrder(taco);
                    return;
                    case "2":
                        taco = new ToppingMeat(taco);
                        addToppingsToOrder(taco);
                        return;
                    case "3":
                        taco = new ToppingPineapple(taco);
                        addToppingsToOrder(taco);
                        return;
                    case "4":
                        taco = new ToppingSauce(taco);
                        addToppingsToOrder(taco);
                        return;
                    case "5":
                        addOrderToList(taco);
                        System.out.println(taco.getDescription());
                        //spara denna taco i lista för specifik order
                        return;
                 //   default:
                   //     System.out.println("Inget val, försök igen");
                }
            }
         //   return taco;
        }
    public void addOrderToList(Taco taco){

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
