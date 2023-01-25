package enums;

import com.google.gson.annotations.SerializedName;

public enum TransactionType {
    @SerializedName("payment")
    PAYMENT("payment"),
    @SerializedName("request")
    REQUEST("request");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }
}
