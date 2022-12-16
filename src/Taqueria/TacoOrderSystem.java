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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TacoOrderSystem {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    KitchenGUI kitchenGUI = new KitchenGUI();

    ArrayList<Order> orderListORDERED = new ArrayList<>();
    ArrayList<Order> orderListREADY = new ArrayList<>();
    ArrayList<Order> orderListDELIVERED = new ArrayList<>();
    boolean run = true;
    int systemOrderID = Order.getOrderIDFromFile();


    public TacoOrderSystem() {

        String scannerInput;
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
                        orderListORDERED.add(order);
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
        order.setTotalPriceOrder(taco.getPrice());
        System.out.println("DEBUG order.getTotalPriceOrder() " + order.getTotalPriceOrder());
        System.out.println("DEBUG taco.getPrice() " + taco.getPrice());
        System.out.println("Tillagd i order: " + taco.getDescription() + ". Pris: " + df.format(taco.getPrice()) + " kr");
    }

    public void searchOrder() { //är metodnamnet passande?
        String searchString = null;
        StringBuilder orderInfo = new StringBuilder();
        System.out.println("Sök efter kundinformation(Namn/Telefon), status(Beställd/Redo/Levererad), eller ordernummer(inled med #): ");
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextLine()) {
            searchString = scan.nextLine();
            for (Order order : orderListORDERED) {
                assert searchString != null;
                if (searchString.equalsIgnoreCase(order.getCustomerName()) || searchString.equalsIgnoreCase(order.getCustomerPhone()) ||
                        searchString.equalsIgnoreCase(order.getStatus().toString()) || searchString.equals("#" + order.getOrderID())) {
                    orderInfo.append(order);
                }
            }
            for (Order order : orderListREADY) {
                assert searchString != null;
                if (searchString.equalsIgnoreCase(order.getCustomerName()) || searchString.equalsIgnoreCase(order.getCustomerPhone()) ||
                        searchString.equalsIgnoreCase(order.getStatus().toString()) || searchString.equals("#" + order.getOrderID())) {
                    orderInfo.append(order);
                }
            }
            for (Order order : orderListDELIVERED) {
                assert searchString != null;
                if (searchString.equalsIgnoreCase(order.getCustomerName()) || searchString.equalsIgnoreCase(order.getCustomerPhone()) ||
                        searchString.equalsIgnoreCase(order.getStatus().toString()) || searchString.equals("#" + order.getOrderID())) {
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
            } else {
                System.out.println("Ange status: 1. BESTÄLLD, 2. REDO, 3. LEVERERAD");
                Order order = null;
                scannerInput = scan.nextLine();
                switch (scannerInput) {
                    case "1" -> {
                        status = Status.ORDERED;
                    }
                    case "2" -> {
                        status = Status.READY;
                        order = getOrderFromList(orderListORDERED, orderID);
                        if (order == null) {
                            System.out.println("Order " + orderID + " finns inte eller går inte att ändra till: " + status);
                            break;
                        }
                        order.setStatus(status);
                        System.out.println("Beställning #" + orderID + " är nu redo att hämtas.");
                        orderListREADY.add(order);
                        orderListORDERED.remove(order);
                        updateKitchenGUI();
                    }
                    case "3" -> {
                        status = Status.DELIVERED;
                        order = getOrderFromList(orderListREADY, orderID);
                        if (order == null) {
                            System.out.println("Order " + orderID + " finns inte eller går inte att ändra till: " + status);
                            break;
                        }
                        order.setStatus(status);
                        System.out.println("Beställning #" + orderID + " är nu levererad.");
                        orderListDELIVERED.add(order);
                        orderListREADY.remove(order);
                    }
                    default -> System.out.println("Felaktig inmatning av status, försök igen");
                }
                //System.out.println("Status på order #" + orderID + " ändrad till: " + status);
            }
        }
    }

    private Order getOrderFromList(ArrayList<Order> orderList, int orderID) {
        for (Order order : orderList) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null;
    }

    public void updateKitchenGUI() {
        kitchenGUI.orderText.setText("");
        for (Order o : orderListORDERED) {
            List<Taco> tempList = o.getTacoList();
            for (Taco t : tempList) {
                kitchenGUI.orderText.append("\n\n" + t.getDescription());
                kitchenGUI.repaint();
                kitchenGUI.revalidate();
            }
        }
    }
}
