package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.User;


public class NotificationsRequest extends BaseRequest{

    public int getNotificationsCount(User user){
        LoginRequests loginRequests = new LoginRequests();
        String cookie = loginRequests.login(user);

        Response response = RestAssured
                .given().contentType("application/json").baseUri(BASE_URL)
                .header("Cookie","connect.sid="+cookie)
                .when().get(NOTIFICATIONS_PATH)
                .then().statusCode(200).extract().response();
        return response.jsonPath().getList("results").size();
    }
}
