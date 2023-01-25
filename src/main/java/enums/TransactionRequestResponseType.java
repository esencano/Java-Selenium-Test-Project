package enums;

public enum TransactionRequestResponseType {
    REJECTED("rejected"),
    ACCEPTED("accepted");

    private final String name;

    TransactionRequestResponseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
