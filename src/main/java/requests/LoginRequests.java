package requests;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import loggers.Log4jLogger;
import models.User;

public class LoginRequests extends BaseRequest{

    @Step("Login Request")
    public String login(User user){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",user.getUsername());
        jsonObject.addProperty("password",user.getPassword());
        String cookie = RestAssured
                                .given()
                    .contentType("application/json")
                    .baseUri(BASE_URL)
                    .body(jsonObject)
                    .when()
                    .post(LOGIN_PATH)
                    .then().statusCode(200).extract().cookie("connect.sid");
        Log4jLogger.info("User logged in: " + user.getUsername());
        return cookie;
    }
}
