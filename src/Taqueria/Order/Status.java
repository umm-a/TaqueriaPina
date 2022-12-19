package Taqueria.Order;

public enum Status {
    ORDERED("BESTÄLLD"),
    READY("REDO"),
    DELIVERED("LEVERERAD");

    private final String statusText;

    Status(String text) {
        this.statusText = text;
    }
    public String toString() {
        return statusText;
    }
}
