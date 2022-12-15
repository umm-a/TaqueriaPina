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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TacoOrderSystem {
    KitchenGUI kitchenGUI = new KitchenGUI();

    ArrayList<Order> activeOrderList = new ArrayList<>();
    ArrayList<Order> finishedOrderList = new ArrayList<>();
    boolean run = true;
    int systemOrderID = Order.getOrderIDFromFile();


    public TacoOrderSystem() {

        String scannerInput;
        KitchenGUI kitchenGUI = new KitchenGUI();
        while (run) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Välj ett val med 1, 2, eller 3: \n1. Skapa beställning \n2. Sök beställning \n3. Ändra status på beställning \n4. Avsluta");
            scannerInput = scan.nextLine();
            switch (scannerInput) {
                case "1" -> startOrder();
                case "2" -> searchOrder();
                case "3" -> changeOrderStatus();
                case "4" -> {
                    System.out.println("Vill du verkligen avsluta programmet? (J/N)");
                    if (scan.nextLine().equalsIgnoreCase("J")) {
                        System.out.println("Programmet avslutas");
                        System.out.println("Tack för att du använder TacoOrderSystem!");
                        run = false;
                        System.exit(1);
                    } else {
                        System.out.println("Avslut av programmet avbruten\n");
                    }
                }
                default -> System.out.println("Felaktig inmatning, försök igen");
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

            System.out.println("Välj bas: 1. Tortilla, 2. Sallad, 3. Taco Shell.\n4. För att skicka beställning. 5. Avbryt beställning.");
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
                        order.setOrderID(systemOrderID);
                        order.setStatus(Status.ORDERED);
                        activeOrderList.add(order);
                        System.out.println("Beställning skapad. \n" + order);
                        updateKitchenGUI();

                        // räklnar upp och uppdaterar filen med orderID om beställningen lyckades
                        systemOrderID++;
                        Order.writeOrderIDToFile(systemOrderID);
                    } else {
                        System.out.println("Du måste lägga till minst en bas för att skapa en beställning\n" +
                                "Beställningen har inte skapats.");
                    }
                    create = false;
                    updateKitchenGUI(activeOrderList);
                }

                case "5" -> {
                    System.out.println("Vill du avbryta beställningen? (J/N)");
                    if (scan.nextLine().equalsIgnoreCase("J")) {
                        System.out.println("Beställningen har avbrutits");
                        create = false;
                    } else {
                        System.out.println("Du kan fortsätta med beställningen");
                    }
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
        System.out.println("Tillagd i order: " + taco.getDescription());
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
                if (searchString.equalsIgnoreCase(order.getCustomerName()) || searchString.equalsIgnoreCase(order.getCustomerPhone()) ||
                        searchString.equalsIgnoreCase(order.getStatus().toString()) || searchString.equals(String.valueOf(order.getOrderID()))) {
                    orderInfo.append(order);
                }
            }
            for (Order order : finishedOrderList) {
                assert searchString != null;
                if (searchString.equalsIgnoreCase(order.getCustomerName()) || searchString.equalsIgnoreCase(order.getCustomerPhone()) ||
                        searchString.equalsIgnoreCase(order.getStatus().toString()) || searchString.equals(String.valueOf(order.getOrderID()))) {
                    orderInfo.append(order);
                }
            }
        }
        if (orderInfo.length() == 0) {
            System.out.println("Ingen matchning hittades");
        } else {
            System.out.println("Matchande ordrar: \n" + orderInfo);
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

    public void updateKitchenGUI(ArrayList<Order> activeOrderList) {
        kitchenGUI.orderText.setText("");
        for (Order o : activeOrderList) {
            List<Taco> tempList = o.getTacoList();
            for (Taco t : tempList) {
                kitchenGUI.orderText.append("\n\n" + t.getDescription());
                kitchenGUI.repaint();
                kitchenGUI.revalidate();
            }
        }
    }
}
