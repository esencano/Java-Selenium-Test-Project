package enums;

import com.google.gson.annotations.SerializedName;

public enum NotificationTypes {
    @SerializedName("liked")
    LIKED,
    @SerializedName("commented")
    COMMENTED,
    @SerializedName("payment_received")
    PAYMENT_RECEIVED,
    @SerializedName("payment_request_received")
    PAYMENT_REQUEST_RECEIVED;
}
