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
import java.util.List;
import java.util.Scanner;

public class TacoOrderSystem {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    KitchenGUI kitchenGUI = new KitchenGUI();

    ArrayList<Order> orderList = new ArrayList<>();
    boolean run = true;
    int systemOrderID = Order.getOrderIDFromFile();

    public TacoOrderSystem() {

        String scannerInput;
        while (run) {
            Scanner scan = new Scanner(System.in);
            System.out.println("[HUVUDMENY]\nVälj ett av följande alternativ: \n1. Skapa beställning \n2. Sök beställning \n3. Ändra status på beställning \n4. Avsluta");
            scannerInput = scan.nextLine();
            switch (scannerInput) {
                case "1" -> startOrder();
                case "2" -> searchOrder();
                case "3" -> changeOrderStatus();
                case "4" -> {
                    System.out.println("Vill du verkligen avsluta programmet? (J/N)");
                    if (scan.nextLine().equalsIgnoreCase("J")) {
                        System.out.println("Programmet avslutas.");
                        System.out.println("Tack för att du använder TacoOrderSystem!");
                        run = false;
                        System.exit(1);
                    } else {
                        System.out.println("Begäran om avslut av programmet avbröts.\n");
                    }
                }
                default -> System.out.println("Felaktig inmatning, försök igen.");
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
                    System.out.println("Felaktig inmatning av namn. Fältet får inte vara tomt. Försök igen.\n");
                }
            } else if (order.getCustomerPhone() == null) {
                System.out.println("Ange kundens telefonnummer: ");
                scannerInput = scan.nextLine().trim();
                if (scannerInput.matches("[0-9]+")) {
                    order.setCustomerPhone(scannerInput);
                } else {
                    System.out.println("Felaktig inmatning av telefonnummer. Endast siffror är tillåtna. Försök igen.\n");
                }
            }
        }

        while (create) {

            System.out.println("""
                    [TACO BASMENY]
                    Välj bas:
                    1. Tortilla
                    2. Sallad
                    3. Taco Shell
                    4. Skicka beställning
                    5. Avbryt beställning""");
            scannerInput = scan.nextLine();
            switch (scannerInput) {

                case "1" -> addToppingsToTaco(new Tortilla(), order);

                case "2" -> addToppingsToTaco(new Salad(), order);

                case "3" -> addToppingsToTaco(new TacoShell(), order);

                case "4" -> {
                    if (order.getTacoList().size() > 0) {
                        order.setOrderID(systemOrderID);
                        order.setStatus(Status.ORDERED);
                        orderList.add(order);
                        System.out.println("Beställning skapad: \n" + order + "\n");
                        updateKitchenGUI();
                        Order.writeOrderToFile(order);
                        // Räknar upp och uppdaterar filen med orderID om beställningen lyckades
                        systemOrderID++;
                        if (systemOrderID > 999) systemOrderID = 1;
                        Order.writeOrderIDToFile(systemOrderID);
                    } else {
                        System.out.println("""
                                Du måste lägga till minst en bas för att skapa en beställning
                                Beställningen har inte skapats.
                                """);
                    }
                    create = false;
                }

                case "5" -> {
                    System.out.println("Vill du avbryta beställningen? (J/N)");
                    if (scan.nextLine().equalsIgnoreCase("J")) {
                        System.out.println("Beställningen har avbrutits.\n");
                        create = false;
                    } else {
                        System.out.println("Du kan fortsätta med beställningen.");
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
            System.out.println("[TOPPINGMENY]\nVälj topping:\n1. Ost\n2. Köttfärs\n3. Ananas\n4. Sås\n5. Inga fler toppings");
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
        System.out.println("Tillagd i order: " + taco.getDescription() + ". Pris: " + df.format(taco.getPrice()) + " kr"
        + "\nAntal servetter till kund baserat på kladdighet: " + taco.getNapkins());
    }

    public void searchOrder() {
        String search;
        Scanner scan = new Scanner(System.in);
        System.out.println("Sök efter beställning genom att ange kundens namn, telefonnummer, ordernummer eller status(Beställd/Redo/Levererad): ");
        search = scan.nextLine();
        StringBuilder searchResult = new StringBuilder();
        for (Order order : orderList) {
            // söker efter Namn, Telefonnummer, Ordernummer eller Status.
            if (order.getCustomerName().toLowerCase().contains(search.toLowerCase()) ||
                    order.getCustomerPhone().equals(search.toLowerCase()) ||
                    String.valueOf(order.getOrderID()).equals(search.toLowerCase()) ||
                    order.getStatus().toString().equalsIgnoreCase(search.toLowerCase())) {
                searchResult.append(order).append("\n");
            }
        }
        if (searchResult.length() > 0) {
            System.out.println("--- Sökresultat ---\n" + searchResult);
            System.out.println("--- Slut på sökresultat ---");
        } else {
            System.out.println("Hittade inga beställningar med sökordet: \"" + search + "\".");
        }
    }

    public void changeOrderStatus() {
        Scanner scan = new Scanner(System.in);
        String scannerInput;
        int orderID = 0;

        // while-loopen ser till att vi får en inmatning som är ett heltal
        while (true) {
            if (orderID != 0) {
                break;
            } else {
                System.out.println("Ange ordernummer: ");
                scannerInput = scan.nextLine();
                if (scannerInput.matches("[0-9]+")) {
                    orderID = Integer.parseInt(scannerInput);
                } else {
                    System.out.println("Felaktig inmatning av ordernummer. Endast siffror är tillåtna. Försök igen.\n");
                }
            }
        }
        // kitchenGUI uppdateras om statusen går till eller från ORDERED.
        for (Order order : orderList) {
            if (orderID == order.getOrderID()) {
                if (order.getStatus() == Status.ORDERED) {
                    System.out.println("Beställning #" + orderID + " har statusen " + order.getStatus() +
                            ".\nVälj ny status:\n1. Redo\n2. Levererad\n3. Avbryt");
                    scannerInput = scan.nextLine();
                    switch (scannerInput) {
                        case "1" -> {
                            order.setStatus(Status.READY);
                            System.out.println("Beställning #" + orderID + " har fått ny status " + order.getStatus() + ".\n");
                            updateKitchenGUI();
                        }
                        case "2" -> {
                            order.setStatus(Status.DELIVERED);
                            System.out.println("Beställning #" + orderID + " har fått ny status " + order.getStatus() + ".\n");
                            updateKitchenGUI();
                        }
                        case "3" -> System.out.println("Avbryter ändring av status.\n");
                        default -> System.out.println("Felaktig inmatning. Försök igen.");
                    } break;
                }
                if (order.getStatus() == Status.READY) {
                    System.out.println("Beställning #" + orderID + " har statusen " + order.getStatus() +
                            ".\nVälj ny status:\n1. Levererad\n(2. Beställd)\n3. Avbryt");
                    scannerInput = scan.nextLine();
                    switch (scannerInput) {
                        case "1" -> {
                            order.setStatus(Status.DELIVERED);
                            System.out.println("Beställning #" + orderID + " har fått ny status " + order.getStatus() + ".\n");
                        }
                        case "2" -> {
                            order.setStatus(Status.ORDERED);
                            System.out.println("Beställning #" + orderID + " har fått ny status " + order.getStatus() + ".\n");
                            updateKitchenGUI();
                        }
                        case "3" -> System.out.println("Avbryter ändring av status.\n");
                        default -> System.out.println("Felaktig inmatning. Försök igen.");
                    } break;
                }
                if (order.getStatus() == Status.DELIVERED) {
                    System.out.println("OBS! Beställning #" + orderID + " är markerad som " + order.getStatus() +
                            ".\nVill du verkligen ändra status? (J/N)");
                    scannerInput = scan.nextLine();
                    if (scannerInput.equalsIgnoreCase("J")) {
                        System.out.println("Välj ny status:\n1. Beställd\n2. Redo\n3. Avbryt");
                        scannerInput = scan.nextLine();
                        switch (scannerInput) {
                            case "1" -> {
                                order.setStatus(Status.ORDERED);
                                System.out.println("Beställning #" + orderID + " har fått ny status " + order.getStatus() + ".\n");
                                updateKitchenGUI();
                            }
                            case "2" -> {
                                order.setStatus(Status.READY);
                                System.out.println("Beställning #" + orderID + " har fått ny status " + order.getStatus() + ".\n");
                            }
                            case "3" -> System.out.println("Avbryter ändring av status.\n");
                            default -> System.out.println("Felaktig inmatning. Försök igen.");
                        }
                    } else if (scannerInput.equalsIgnoreCase("N")) {
                        System.out.println("Avbryter ändring av status.\n");
                    } else {
                        System.out.println("Felaktig inmatning. Försök igen.");
                    }
                }
                else {
                    System.out.println("Statusen ändrades inte.");
                    return;
                }
            }
            else {
                System.out.println("Beställning #" + orderID + " hittades inte.\n");
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
        for (Order o : orderList) {
            if (o.getStatus() == Status.ORDERED) {
                List<Taco> tempList = o.getTacoList();
                for (Taco t : tempList) {
                    kitchenGUI.orderText.append(o.getOrderID() + ": " + t.getDescriptionWithoutPrice() + "\n");
                    kitchenGUI.repaint();
                    kitchenGUI.revalidate();
                }
                kitchenGUI.orderText.append("\n");
            }

        }
    }
}
