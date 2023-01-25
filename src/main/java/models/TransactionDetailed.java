package models;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class TransactionDetailed {

    private String amount;
    List<Comment> comments;
    List<Like> likes;
    String description;
    String receiverAvatar;
    String senderAvatar;
    String receiverName;
    String senderName;
    String id;
    String requestStatus;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiverAvatar() {
        return receiverAvatar;
    }

    public void setReceiverAvatar(String receiverAvatar) {
        this.receiverAvatar = receiverAvatar;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "TransactionDetailed{" +
                "amount='" + amount + '\'' +
                ", comments=" + comments +
                ", likes=" + likes +
                ", description='" + description + '\'' +
                ", receiverAvatar='" + receiverAvatar + '\'' +
                ", senderAvatar='" + senderAvatar + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", senderName='" + senderName + '\'' +
                ", id='" + id + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                '}';
    }
}
