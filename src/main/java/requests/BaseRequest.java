package requests;

public class BaseRequest {
    private final String envVariableBaseURL = System.getenv("APPLICATION_BE_URL");
    private final String defaultBaseURL = "http://localhost:3001";

    protected final String BASE_URL = !(envVariableBaseURL == null || envVariableBaseURL.equals("")) ?envVariableBaseURL : defaultBaseURL; // can be done private and change assign approach
    protected final String COMMENTS_PATH =  "/comments/{transactionID}";
    protected final String CREATE_BANK_ACCOUNT_PATH = "/graphql";
    protected final String DELETE_BANK_ACCOUNT_PATH = "/graphql";
    protected final String LIKE_PATH = "/likes/{transactionID}";
    protected final String LOGIN_PATH = "/login";
    protected final String NOTIFICATIONS_PATH = "/notifications";
    protected final String PUBLIC_TRANSACTIONS_PATH = "/transactions/public";
    protected final String TRANSACTION_PATH = "/transactions";
    protected final String ACCEPT_TRANSACTION_PATH = "/transactions/{transactionID}";
    protected final String USER_REGISTER_PATH = "/users";

}
