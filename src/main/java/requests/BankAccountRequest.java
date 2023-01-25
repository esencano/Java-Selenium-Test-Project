package requests;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import loggers.Log4jLogger;
import models.BankAccount;
import models.User;

public class BankAccountRequest extends BaseRequest{

    @Step("Crete Bank Account Request")
    public BankAccount loginAndCreateRandomBankAccount(User user){
        LoginRequests loginRequests = new LoginRequests();
        String cookie = loginRequests.login(user);

        BankAccount bankAccount = BankAccount.getRandomInstance();

        String bankAccountID = RestAssured
                .given().contentType("application/json").baseUri(BASE_URL).body(getCreateBankAccountQueryBody(bankAccount), ObjectMapperType.GSON)
                .header("Cookie","connect.sid="+cookie)
                .when().post(CREATE_BANK_ACCOUNT_PATH)
                .then().statusCode(200).extract().path("data.createBankAccount.id");
        bankAccount.setBankAccountID(bankAccountID);
        Log4jLogger.info("Bank Account Has Been Created: " + bankAccount);
        return bankAccount;
    }
    @Step("Delete Bank Account Request")
    public void loginAndDeleteBankAccount(User user, String bankAccountID){
        LoginRequests loginRequests = new LoginRequests();
        String cookie = loginRequests.login(user);

        RestAssured
                .given().contentType("application/json").baseUri(BASE_URL).body(getDeleteBankAccountQueryBody(bankAccountID), ObjectMapperType.GSON)
                .header("Cookie","connect.sid="+cookie)
                .when().post(DELETE_BANK_ACCOUNT_PATH)
                .then().statusCode(200);
        Log4jLogger.info("Bank Account Has Been Deleted: "+ bankAccountID);
    }
    private JsonObject getCreateBankAccountQueryBody(BankAccount bankAccount){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operationName","CreateBankAccount");
        jsonObject.addProperty("query","\n  mutation CreateBankAccount($bankName: String!, $accountNumber: String!, $routingNumber: String!) {\n    createBankAccount(\n    \n      bankName: $bankName\n      accountNumber: $accountNumber\n      routingNumber: $routingNumber\n    ) {\n      id\n      uuid\n      userId\n      bankName\n      accountNumber\n      routingNumber\n      isDeleted\n      createdAt\n    }\n  }\n");
        JsonObject variablesObject = new JsonObject();
        variablesObject.addProperty("bankName",bankAccount.getBankName());
        variablesObject.addProperty("routingNumber",bankAccount.getRoutingNumber());
        variablesObject.addProperty("accountNumber",bankAccount.getAccountNumber());
        jsonObject.addProperty("variables",variablesObject.toString());
        return jsonObject;
    }

    private JsonObject getDeleteBankAccountQueryBody(String bankAccountID){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operationName","DeleteBankAccount");
        jsonObject.addProperty("query","\n  mutation DeleteBankAccount($id: ID!) { deleteBankAccount(id: $id)}" );
        JsonObject variablesObject = new JsonObject();
        variablesObject.addProperty("id",bankAccountID);
        jsonObject.addProperty("variables",variablesObject.toString());
        return jsonObject;
    }


}
