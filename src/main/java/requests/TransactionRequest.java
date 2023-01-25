package requests;

import com.google.gson.JsonObject;
import enums.TransactionRequestResponseType;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import loggers.Log4jLogger;
import models.Transaction;
import enums.TransactionType;
import models.TransactionDetailed;
import models.User;

import java.util.List;

public class TransactionRequest extends BaseRequest{

    @Step("Make Transaction Request")
    public Transaction makeTransaction(User sender, String receiverId, TransactionType transactionType, double amount,String description){
        LoginRequests loginRequests = new LoginRequests();

        String cookie = loginRequests.login(sender);
        Transaction transaction = new Transaction(sender.getId(),receiverId,transactionType,amount,description);
        String id = RestAssured
                .given().contentType("application/json").baseUri(BASE_URL)
                .body(transaction, ObjectMapperType.GSON)
                .header("Cookie","connect.sid="+cookie)
                .when().post(TRANSACTION_PATH)
                .then().statusCode(200)
                .extract().path("transaction.id");
        transaction.setId(id);
        Log4jLogger.info("Transaction done: "+ transaction);
        return transaction;
    }

    @Step("Reject Transaction Request")
    public void rejectTransactionRequest(User user,String transactionId){
        acceptRejectTransaction(user,transactionId, TransactionRequestResponseType.REJECTED);
        Log4jLogger.info("Transaction \'"+ transactionId +"\' rejected By User: "+ user.getUsername());

    }

    @Step("Reject Transaction Request")
    public void acceptTransactionRequest(User user,String transactionId){
        acceptRejectTransaction(user,transactionId, TransactionRequestResponseType.ACCEPTED);
        Log4jLogger.info("Transaction \'"+ transactionId +"\' accepted By User: "+ user.getUsername());
    }

    public List<TransactionDetailed> getAllTransactions(User user){
        LoginRequests loginRequests = new LoginRequests();

        String cookie = loginRequests.login(user);
        List<TransactionDetailed> transactionDetailedList
                = RestAssured
                .given().contentType("application/json").baseUri(BASE_URL)
                .header("Cookie","connect.sid="+cookie)
                .when().get(PUBLIC_TRANSACTIONS_PATH)
                .then().statusCode(200)
                .extract().path("results");
        return transactionDetailedList;
    }
    private void acceptRejectTransaction(User user, String transactionId, TransactionRequestResponseType requestResponseType){
        LoginRequests loginRequests = new LoginRequests();
        String cookie = loginRequests.login(user);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",transactionId);
        jsonObject.addProperty("requestStatus", requestResponseType.name().toLowerCase());
       RestAssured
                .given().contentType("application/json").baseUri(BASE_URL)
                .body(jsonObject, ObjectMapperType.GSON)
                .header("Cookie","connect.sid="+cookie)
                .pathParam("transactionID",transactionId)
                .when().patch(ACCEPT_TRANSACTION_PATH)
                .then().statusCode(204);//wrong response
    }

}
