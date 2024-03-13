package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService  messageService;
    ObjectMapper mapper;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::registerPostHandler);
        app.post("login", this::loginPostHandler);
        app.post("messages",this::messagePostHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Endpoint handler for POST /register
     * @param context Javalin context object
     * @throws JsonProcessingException
     */
    private void registerPostHandler(Context context) throws JsonProcessingException {
        Account account = this.mapper.readValue(context.body(), Account.class);
        Account newAccount = this.accountService.createAccount(account);
        if (newAccount != null) {
            context.json(mapper.writeValueAsString(newAccount));
        } else {
            context.status(400);
        }
    }

    /**
     * Endpoint handler for POST /login
     * @param context Javalin context object
     * @throws JsonProcessingException
     */
    private void loginPostHandler(Context context) throws JsonProcessingException {
        Account account = this.mapper.readValue(context.body(), Account.class);
        Account authenticAccount = this.accountService.authenticateAccount(account);
        if (authenticAccount != null) {
            context.json(mapper.writeValueAsString(authenticAccount));
        } else {
            context.status(401);
        }
    }

    private void messagePostHandler(Context context) throws JsonProcessingException {
        Message message = this.mapper.readValue(context.body(), Message.class);
        Message postMessage = this.messageService.createMessage(message);
        if (postMessage != null) {
            context.json(mapper.writeValueAsString(postMessage));
        } else {
            context.status(400);
        }
        
    }

}