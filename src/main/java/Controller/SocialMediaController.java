package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.*;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessageForIdHandler);
        
        return app;
    }


    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(acc);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.login(acc);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(401);
        }
    }

    private void newMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        
        Message message = mapper.readValue(ctx.body(),  Message.class);
        System.out.println(message.message_text);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message mess = messageService.getMessagebyID(id);
        if(mess != null){
            ctx.json(mapper.writeValueAsString(mess)) ;
        }
        else ctx.status(200);
        
    }

    private void deleteMessageHandler(Context ctx)throws JsonProcessingException{
        
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessage(id);
        if(deleted != null ){
            ctx.json(mapper.writeValueAsString(deleted));
        }
        else{
            ctx.status(200);
        }
        
    }

    private void updateMessageHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = ctx.body();
        JsonNode jsonNode = mapper.readTree(requestBody);
        String messageText = jsonNode.get("message_text").asText();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updated = messageService.updateMessage(id, messageText);
        if(updated != null){ctx.json(mapper.writeValueAsString(updated)) ;}
        else ctx.status(400);
        
        
    }

    private void getAllMessageForIdHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(mapper.writeValueAsString(messageService.getAllMessagesByID(id))) ;
    }
}