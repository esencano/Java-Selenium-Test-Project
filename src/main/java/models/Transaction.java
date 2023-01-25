package models;

import enums.TransactionType;

public class Transaction {
    private String id;
    private TransactionType transactionType;
    private double amount;
    private String description;
    private String senderId;
    private String receiverId;

    public Transaction(){}

    public Transaction(String senderId, String receiverId,TransactionType transactionType, double amount, String description) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String recieverId) {
        this.receiverId = recieverId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                '}';
    }
}
