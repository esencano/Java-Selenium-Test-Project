package requests;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import loggers.Log4jLogger;
import models.User;

public class CommentsRequest extends  BaseRequest{
    @Step("Make Comment Request")
    public void makeComment(User user, String transactionID,String content){
        LoginRequests loginRequests = new LoginRequests();
        String cookie = loginRequests.login(user);

        RestAssured
                .given().contentType("application/json").baseUri(BASE_URL).body(getTransactionCommnetBody(transactionID,content), ObjectMapperType.GSON)
                .header("Cookie","connect.sid="+cookie)
                .pathParam("transactionID",transactionID)
                .when().post(COMMENTS_PATH)
                .then().statusCode(200);
        Log4jLogger.info("\'"+content+"\' Comment submitted for transaction ID: "+ transactionID);
    }

    @Step("Like Transaction Request")
    public void likeTransaction(User user, String transactionID){
        LoginRequests loginRequests = new LoginRequests();
        String cookie = loginRequests.login(user);

        RestAssured
                .given().contentType("application/json").baseUri(BASE_URL)
                .header("Cookie","connect.sid="+cookie)
                .pathParam("transactionID",transactionID)
                .when().post(LIKE_PATH)
                .then().statusCode(200);
        Log4jLogger.info("Liked transaction ID: "+ transactionID);
    }

    private JsonObject getTransactionCommnetBody(String transactionID,String content ){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("transactionId",transactionID);
        jsonObject.addProperty("content",content );
        return jsonObject;
    }
}
