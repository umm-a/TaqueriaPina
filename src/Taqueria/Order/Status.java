package Taqueria.Order;

public enum Status {
    ORDERED("Beställd"),
    READY("Redo"),
    DELIVERED("Levererad");

    private final String statusText;

    Status(String text) {
        this.statusText = text;
    }
    public String toString() {
        return statusText;
    }
}
