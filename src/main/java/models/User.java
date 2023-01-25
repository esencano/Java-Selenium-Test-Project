package models;

import com.github.javafaker.Faker;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private  String username;
    private String password;
    private String phoneNumber;
    private String email;

    public User(String firstName, String lastName, String username, String password,String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static User getRandomInstance(){
        Faker faker = new Faker();
        return new User(faker.name().firstName(),faker.name().lastName(),faker.name().username(),faker.internet().password(4,10),faker.numerify("##########"),faker.internet().emailAddress());
    }

    public static User getRandomSimpleUser(){
        Faker faker = new Faker();
        return new User(faker.name().firstName(),faker.name().lastName(),faker.name().username(),faker.internet().password(4,10),"","");
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
