package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;

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
        app.get("messages", this::messageGetAllHandler);
        app.get("accounts/{account_id}/messages", this::messageGetWithAccountIdHandler);
        app.get("messages/{id}", this::messageGetWithId);

        app.post("register", this::registerPostHandler);
        app.post("login", this::loginPostHandler);
        app.post("messages", this::messagePostHandler);

        app.patch("messages/{id}", this::messageUpdate);

        app.delete("messages/{id}", this::messageDeleteWithId);

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

    /**
     * Endpoint handler for POST /messages
     * @param context Javalin context object
     * @throws JsonProcessingException
     */
    private void messagePostHandler(Context context) throws JsonProcessingException {
        Message message = this.mapper.readValue(context.body(), Message.class);
        Message postMessage = this.messageService.createMessage(message);
        if (postMessage != null) {
            context.json(mapper.writeValueAsString(postMessage));
        } else {
            context.status(400);
        }
    }

    /**
     * Endpoint handler for GET /messages
     * @param context Javalin context object
     * @throws JsonProcessingException
     */
    private void messageGetAllHandler(Context context) throws JsonProcessingException {
        ArrayList<Message> messageList = messageService.allMessages();
        context.json(mapper.writeValueAsString(messageList));
    }

    /**
     * Endpoint handler for GET /accounts/{account_id}/messages
     * @param context Javalin context object
     * @throws JsonProcessingException
     */
    private void messageGetWithAccountIdHandler(Context context) throws JsonProcessingException {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        ArrayList<Message> messageList = messageService.allMessagesWithAccountId(account_id);
        context.json(mapper.writeValueAsString(messageList));
    }

    /**
     * Endpoint handler for GET /messages/{id}
     * @param context Javalin context object
     * @throws JsonProcessingException
     */
    private void messageGetWithId(Context context) throws JsonProcessingException {
        int id = Integer.parseInt(context.pathParam("id"));
        Message message = this.messageService.findMessageWithId(id);
        if (message != null) {
            context.json(mapper.writeValueAsString(message));
        }
    }

    /**
     * Endpoint handler for DELETE /messages/{id}
     * @param context Javalin context object
     * @throws JsonProcessingException
     */
    private void messageDeleteWithId(Context context) throws JsonProcessingException {
        int id = Integer.parseInt(context.pathParam("id"));
        Message message = this.messageService.deleteMessageWithId(id);
        if (message != null) {
            context.json(mapper.writeValueAsString(message));
        }
    }

    /**
     * Endpoint hander for PATCH /messages/{id}
     * @param context Javalin context object
     * @throws JsonProcessingException 
     */
    private void messageUpdate(Context context) throws JsonProcessingException {
        int id = Integer.parseInt(context.pathParam("id"));
        String message_text = this.mapper.readTree(context.body()).findValue("message_text").asText();
        Message message = this.messageService.updateMessage(id, message_text);
        if (message != null) {
            context.json(mapper.writeValueAsString(message));
        } else {
            context.status(400);
        }
    }
}