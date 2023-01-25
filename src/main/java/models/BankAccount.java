package models;

import com.github.javafaker.Faker;


public class BankAccount {
    private String bankName;
    private String routingNumber;
    private  String accountNumber;
    private String bankAccountID;

    public BankAccount(String bankName, String routingNumber, String accountNumber) {
        this.bankName = bankName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankAccountID(){
        return bankAccountID;
    }

    public void setBankAccountID(String bankAccountID) {
        this.bankAccountID = bankAccountID;
    }

    public static BankAccount getRandomInstance(){
        Faker faker = new Faker();
        return new BankAccount(faker.name().firstName()+" BANK",faker.number().digits(9),faker.number().digits(9));
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bankName='" + bankName + '\'' +
                ", routingNumber='" + routingNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankAccountID='" + bankAccountID + '\'' +
                '}';
    }
}
