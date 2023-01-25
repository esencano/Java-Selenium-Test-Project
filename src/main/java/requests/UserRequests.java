package requests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import loggers.Log4jLogger;
import models.User;

import java.util.List;

public class UserRequests extends BaseRequest{
    @Step("Create User Request")
    public User createARandomFullUser(){
        User user = User.getRandomInstance();
        createUser(user);
        Log4jLogger.info("User has been created: " + user);
        return user;
    }

    @Step("Create User Request")
    public User createARandomSimpleUser(){
        User user = User.getRandomSimpleUser();
        createUser(user);
        Log4jLogger.info("User has been created: " + user);
        return user;
    }

    private void createUser(User user){
        String userID =  RestAssured
                .given()
                .contentType("application/json")
                .baseUri(BASE_URL)
                .body(user)
                .when()
                .post(USER_REGISTER_PATH)
                .then().statusCode(201).extract().path("user.id");
        user.setId(userID);
    }

    public List<User> getAllUsers(User user){
        LoginRequests loginRequests = new LoginRequests();

        String cookie = loginRequests.login(user);

        Response response = RestAssured
                .given().contentType("application/json")
                .baseUri(BASE_URL)
                .header("Cookie","connect.sid="+cookie)
                .when().get(USER_REGISTER_PATH)
                .then().statusCode(200).extract().response();
        List<User> users = response.jsonPath().getList("results");
        return users;
    }

}
