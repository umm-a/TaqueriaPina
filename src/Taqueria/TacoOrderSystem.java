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

import java.util.ArrayList;
import java.util.Scanner;

public class TacoOrderSystem {

    ArrayList<Order> activeOrderList = new ArrayList<>();
    ArrayList<Order> finishedOrderList = new ArrayList<>();
    boolean run = true;
    int orderID;


    public TacoOrderSystem() {

        String scannerInput;
        KitchenGUI kitchenGUI = new KitchenGUI();
        while (run) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Välj ett val med 1, 2, eller 3: \n1. Skapa beställning \n2. Sök beställning \n3. Ändra status på beställning ");
            scannerInput = scan.nextLine();
            switch (scannerInput) {
                case "1" -> startOrder();
                //case "2" -> searchOrder();
                //case "3" -> changeOrderStatus();
                default -> System.out.println("Ej giltigt val");
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

    public void startOrder() { //ta in Order som argument ev.
        boolean create = true;
        String scannerInput;
        Scanner scan = new Scanner(System.in);

        Order order = new Order();

        while (create) {

            System.out.println("Välj bas: 1. Tortilla, 2. Sallad, 3. Taco Shell.\nEller välj: 4. Order färdig.");
            scannerInput = scan.nextLine();
            switch (scannerInput) {

                case "1" -> {
                    addToppingsToTaco(new Tortilla(), order);
                }

                //       return taco; todo -> man kan göra på olika vis, tänkte lägga ihop med Order men blev problematiskt - låt oss kika tillsammans
                case "2" -> {
                    addToppingsToTaco(new Salad(), order);
                    //       return taco2;
                }

                case "3" -> {
                    addToppingsToTaco(new TacoShell(), order);
                    //       return taco3;
                }

                case "4" -> {
                    activeOrderList.add(order);
                    create = false;
                }

            }
        }
    }

    public void addToppingsToTaco(Taco taco, Order order) { //ta ev. in Order som argument
        boolean addToppings = true;
        String scannerInput;
        while (addToppings) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Välj toppings!\n1.Ost, 2.Köttfärs, 3.Ananas, 4.Sås\n (Om du är nöjd, skriv \"5\")");
            scannerInput = scan.nextLine();
            switch (scannerInput) {
                case "1" -> taco = new ToppingCheese(taco);
                case "2" -> taco = new ToppingMeat(taco);
                case "3" -> taco = new ToppingPineapple(taco);
                case "4" -> taco = new ToppingSauce(taco);
                case "5" -> {
                    addTacoToOrder(taco, order);

                    //spara denna taco i lista för specifik order
                    addToppings = false;
                }
                //   default:
                //     System.out.println("Inget val, försök igen");
            }
        }
        //   return taco;
    }

    public void addTacoToOrder(Taco taco, Order order) {

        order.addTaco(taco);
        System.out.println("Lade på order: " + taco.getDescription());
    }

    public String searchOrdersByStatus(Status status, ArrayList<Order> orderList) { //vill vi kunna söka i bägge listor?
        String temp = "";
        return temp;
    }

    public String searchOrder(String id, ArrayList<Order> activeOrderList, ArrayList<Order> finishedOrderList) { //är metodnamnet passande?
        String temp = "";
        return temp;
    }

    public void changeOrderStatus(int id, Status status) {

    }

    public void updateKitchenGUI(ArrayList<Order> activeOrderList) {

    }
}
