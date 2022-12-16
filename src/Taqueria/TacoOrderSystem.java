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

    ArrayList<Order> orderListORDERED = new ArrayList<>();
    ArrayList<Order> orderListREADY = new ArrayList<>();
    ArrayList<Order> orderListDELIVERED = new ArrayList<>();
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
                    Välj bas: 1. Tortilla, 2. Sallad, 3. Taco Shell.
                    4. Skicka beställning. 5. Avbryt beställning.""");
            scannerInput = scan.nextLine();
            switch (scannerInput) {

                case "1" -> addToppingsToTaco(new Tortilla(), order);

                case "2" -> addToppingsToTaco(new Salad(), order);

                case "3" -> addToppingsToTaco(new TacoShell(), order);

                case "4" -> {
                    if (order.getTacoList().size() > 0) {
                        order.setOrderID(systemOrderID);
                        order.setStatus(Status.ORDERED);
                        orderListORDERED.add(order);
                        System.out.println("Beställning skapad: \n" + order);
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
            System.out.println("[TOPPINGMENY]\nVälj topping:\n1.Ost, 2.Köttfärs, 3.Ananas, 4.Sås\n(Om du är nöjd, skriv \"5\")");
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
        String searchString = null;
        StringBuilder orderInfo = new StringBuilder();
        System.out.println("Sök efter kundinformation(Namn/Telefon), status(Beställd/Redo/Levererad), eller ordernummer: ");
        Scanner scan = new Scanner(System.in);
        int searchResultCounter = 0;
        if (scan.hasNextLine()) {
            searchString = scan.nextLine();
            if (orderListORDERED.size() > 0) {
                for (Order order : orderListORDERED) {
                    if (order.getCustomerName().toLowerCase().contains(searchString.toLowerCase()) ||
                            order.getCustomerPhone().equals(searchString) ||
                            order.getStatus().toString().equalsIgnoreCase(searchString) ||
                            String.valueOf(order.getOrderID()).equals(searchString)) {
                        orderInfo.append(order);
                        searchResultCounter++;
                    }
                }
            }
            else if (orderListREADY.size() > 0) {
                for (Order order : orderListREADY) {
                    if (order.getCustomerName().toLowerCase().contains(searchString.toLowerCase()) ||
                            order.getCustomerPhone().equals(searchString) ||
                            order.getStatus().toString().equalsIgnoreCase(searchString) ||
                            String.valueOf(order.getOrderID()).equals(searchString)) {
                        orderInfo.append(order);
                        searchResultCounter++;
                    }
                }
            }
            else if (orderListDELIVERED.size() > 0) {
                for (Order order : orderListDELIVERED) {
                    if (order.getCustomerName().toLowerCase().contains(searchString.toLowerCase()) ||
                            order.getCustomerPhone().equals(searchString) ||
                            order.getStatus().toString().equalsIgnoreCase(searchString) ||
                            String.valueOf(order.getOrderID()).equals(searchString)) {
                        orderInfo.append(order);
                        searchResultCounter++;
                    }
                }
            }
        }
        if (orderInfo.length() == 0) {
            System.out.println("Sökresultat:\n" +
                    "Hittade ingen order med sökordet: \"" + searchString + "\".\n");
        } else {
            System.out.println("--- Sökresultat: ---\n" +
                    "Hittade: " + searchResultCounter + " ordrar med sökordet: \"" + searchString + "\".\n\n" +
                    orderInfo);
            System.out.println("--- Slut på sökresultat ---\n");
        }
    }

    public boolean searchForOrderNumberInOrderList(String searchString, List<Order> orderList) {
        if (orderList.size() > 0) {
            for (Order order : orderList) {
                return String.valueOf(order.getOrderID()).equals(searchString);
            }
        } return false;
    }

    public Order searchForOrderNumberInOrderList(List<Order> orderList, int orderID) {
        if (orderList.size() > 0) {
            for (Order order : orderList) {
                if (orderID == order.getOrderID()) {
                    return order;
                }
            }
        } return null;
    }
    public void changeOrderStatus() {
        Scanner scan = new Scanner(System.in);
        String scannerInput;
        int orderID = 0;
        Status status;
        Order order;

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
        // söker efter ordernummer i alla våra listor
        if (searchForOrderNumberInOrderList(orderListORDERED, orderID) != null) {
            order = searchForOrderNumberInOrderList(orderListORDERED, orderID);
            System.out.println("Vill du ändra status på beställning #" + orderID + "? (J/N)");
            scannerInput = scan.nextLine();
            if (scannerInput.equalsIgnoreCase("J")) {
                System.out.println("Status just nu är: " + order.getStatus() + ". Välj ny status: 1. Redo, 2. Levererad");
                scannerInput = scan.nextLine();
                if (scannerInput.equals("1")) {
                    status = Status.READY;
                    order.setStatus(status);
                    orderListREADY.add(order);
                    orderListORDERED.remove(order);
                    updateKitchenGUI();
                    System.out.println("Status för beställning #" + orderID + " har ändrats till: " + status + ".\n");
                } else if (scannerInput.equals("2")) {
                    status = Status.DELIVERED;
                    order.setStatus(status);
                    orderListDELIVERED.add(order);
                    orderListORDERED.remove(order);
                    updateKitchenGUI();
                    System.out.println("Status för beställning #" + orderID + " har ändrats till: " + status + ".\n");
                } else {
                    System.out.println("Felaktig inmatning av status, försök igen.");
                }
            } else {
                System.out.println("Status för beställning #" + orderID + " har inte ändrats.\n");
            }
        }
        else if (searchForOrderNumberInOrderList(orderListREADY, orderID) != null) {
            order = searchForOrderNumberInOrderList(orderListREADY, orderID);
            System.out.println("Vill du ändra status på beställning #" + orderID + "? (J/N)");
            scannerInput = scan.nextLine();
            if (scannerInput.equalsIgnoreCase("J")) {
                System.out.println("Status just nu är: " + order.getStatus() + ". Välj ny status: 1. Levererad, 2. Beställd");
                scannerInput = scan.nextLine();
                if (scannerInput.equals("1")) {
                    status = Status.DELIVERED;
                    order.setStatus(status);
                    orderListDELIVERED.add(order);
                    orderListREADY.remove(order);
                    System.out.println("Status för beställning #" + orderID + " har ändrats till: " + status + ".\n");
                } else if (scannerInput.equals("2")) {
                    status = Status.ORDERED;
                    order.setStatus(status);
                    orderListORDERED.add(order);
                    orderListREADY.remove(order);
                    updateKitchenGUI();
                    System.out.println("Status för beställning #" + orderID + " har ändrats till: " + status + ".\n");
                } else {
                    System.out.println("Felaktig inmatning av status, försök igen.");
                }
            } else {
                System.out.println("Status för beställning #" + orderID + " har inte ändrats.\n");
            }
        }
        else if (searchForOrderNumberInOrderList(orderListDELIVERED, orderID) != null) {
            order = searchForOrderNumberInOrderList(orderListDELIVERED, orderID);
            System.out.println("Order #" + orderID + " är markerad som levererad. Vill du ändra status ändå? (J/N)");
            scannerInput = scan.nextLine();
            if (scannerInput.equalsIgnoreCase("J")) {
                System.out.println("Status just nu är: " + order.getStatus() + ". Välj ny status: 1. Beställd, 2. Redo.");
                scannerInput = scan.nextLine();
                if (scannerInput.equals("1")) {
                    status = Status.ORDERED;
                    order.setStatus(status);
                    orderListORDERED.add(order);
                    orderListDELIVERED.remove(order);
                    updateKitchenGUI();
                    System.out.println("Status för beställning #" + orderID + " har ändrats till: " + status + ".\n");
                } else if (scannerInput.equals("2")) {
                    status = Status.READY;
                    order.setStatus(status);
                    orderListREADY.add(order);
                    orderListDELIVERED.remove(order);
                    System.out.println("Status för beställning #" + orderID + " har ändrats till " + status + ".\n");
                } else {
                    System.out.println("Felaktig inmatning av status, försök igen.");
                }
            } else {
                System.out.println("Status för beställning #" + orderID + " har inte ändrats.\n");
            }
        }
        else {
            System.out.println("Hittade ingen order med ordernummer #" + orderID + ".");
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
                kitchenGUI.orderText.append(o.getOrderID() + ": " + t.getDescription() + "\n");
                kitchenGUI.repaint();
                kitchenGUI.revalidate();
            }
            kitchenGUI.orderText.append("\n");
        }
    }
}
