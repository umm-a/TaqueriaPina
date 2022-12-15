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
                case "2" -> searchOrder();
                case "3" -> changeOrderStatus();
                default -> System.out.println("Ej giltigt val");
            }
        }
    }


    public void startOrder() {
        boolean create = true;
        String scannerInput;
        Scanner scan = new Scanner(System.in);

        Order order = new Order();

        // Lägger till kundinfo
        while (true) {
            if (order.getCustomerName() != null && order.getCustomerPhone() != null) {
                break;
            } else if (order.getCustomerName() == null) {
                System.out.println("Ange kundens namn: ");
                scannerInput = scan.nextLine().trim();
                if (scannerInput.length() > 0) {
                    order.setCustomerName(scannerInput);
                } else {
                    System.out.println("Felaktig inmatning av namn, försök igen");
                }
            } else if (order.getCustomerPhone() == null) {
                System.out.println("Ange kundens telefonnummer: ");
                scannerInput = scan.nextLine().trim();
                if (scannerInput.matches("[0-9]+")) {
                    order.setCustomerPhone(scannerInput);
                } else {
                    System.out.println("Felaktig inmatning av telefonnummer, försök igen");
                }
            }
        }

        while (create) {

            System.out.println("Välj bas: 1. Tortilla, 2. Sallad, 3. Taco Shell.\nEller välj: 4. Order färdig.");
            scannerInput = scan.nextLine();
            switch (scannerInput) {

                case "1" -> {
                    addToppingsToTaco(new Tortilla(), order);
                }

                case "2" -> {
                    addToppingsToTaco(new Salad(), order);
                }

                case "3" -> {
                    addToppingsToTaco(new TacoShell(), order);
                }

                case "4" -> {
                    if (order.getTacoList().size() > 0) {
                        order.getOrderID();
                        order.setOrderID(orderID + 1);

                        order.setStatus(Status.ORDERED);
                        activeOrderList.add(order);
                        System.out.println("Beställning skapad. Ordernummer: " + order.getOrderID() +
                                "\nKund: " + order.getCustomerName() + ", " + order.getCustomerPhone() +
                                "\nStatus: " + order.getStatus().toString() +
                                "\nAntal tacos: " + order.getTacoList().size() +
                                "\nTotalpris: " + order.getTotalPrice() + " kr");
                        updateKitchenGUI();
                    } else {
                        System.out.println("Du måste lägga till minst en bas för att skapa en beställning\n" +
                                "Beställningen har inte skapats.");

                    }
                    create = false;
                }

            }
        }
    }

    public void addToppingsToTaco(Taco taco, Order order) {
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
                    addToppings = false;
                }
            }
        }
    }

    public void addTacoToOrder(Taco taco, Order order) {

        order.addTaco(taco);
        System.out.println("Lade på order: " + taco.getDescription());
    }

    public void searchOrder() { //är metodnamnet passande?
        String searchString = null;
        StringBuilder orderInfo = new StringBuilder();
        System.out.println("Sök efter kundinformation, status eller ordernummer: ");
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextLine()) {
            searchString = scan.nextLine();
            for (Order order : activeOrderList) {
                assert searchString != null;
                if (order.getCustomerName().contains(searchString) || order.getCustomerPhone().contains(searchString)) {
                    orderInfo.append(order);
                } else if (order.getOrderID() == Integer.parseInt(searchString)) {
                    orderInfo.append(order);
                }
            }
            for (Order order : finishedOrderList) {
                assert searchString != null;
                if (order.getCustomerName().contains(searchString) || order.getCustomerPhone().contains(searchString)) {
                    orderInfo.append(order);
                } else if (order.getOrderID() == Integer.parseInt(searchString)) {
                    orderInfo.append(order);
                }
            }
        }
        if (orderInfo.length() == 0) {
            System.out.println("Ingen matchning hittades");
        } else {
            System.out.println(orderInfo);
        }
    }

    public void changeOrderStatus() {
        Scanner scan = new Scanner(System.in);
        String scannerInput;
        int orderID = 0;
        Status status = null;
        while (true) {
            if (orderID != 0 && status != null) {
                break;
            } else if (orderID == 0) {
                System.out.println("Ange ordernummer: ");
                scannerInput = scan.nextLine();
                if (scannerInput.matches("[0-9]+")) {
                    orderID = Integer.parseInt(scannerInput);
                } else {
                    System.out.println("Felaktig inmatning av ordernummer, försök igen");
                }
            } else if (status == null) {
                System.out.println("Ange status: 1. BESTÄLLD, 2. REDO, 3. LEVERERAD");
                scannerInput = scan.nextLine();
                switch (scannerInput) {
                    case "1" -> status = Status.ORDERED;
                    case "2" -> status = Status.READY;
                    case "3" -> status = Status.DELIVERED;
                    default -> System.out.println("Felaktig inmatning av status, försök igen");
                }
                System.out.println("Status på order #" + orderID + " ändrad till: " + status);
            }
        } for (Order order : activeOrderList) {
            if (order.getOrderID() == orderID) {
                order.setStatus(status);
                if (status == Status.READY) {
                    System.out.println("Beställning " + orderID + " är nu redo att hämtas.");
                    updateKitchenGUI();
                } if (status == Status.DELIVERED) {
                    System.out.println("Beställning " + orderID + " är nu levererad.");
                    finishedOrderList.remove(order);
                    activeOrderList.remove(order);
                }
            }
        }
    }

    public void updateKitchenGUI() {

    }
}
