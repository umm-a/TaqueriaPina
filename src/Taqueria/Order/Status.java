package Taqueria.Order;

public enum Status {
    ORDERED("Beställd"),
    READY("Redo"),
    DELIVERED("Levererad");

    private final String statusText;

    Status(String statusText) {
        this.statusText = statusText;
    }
}
