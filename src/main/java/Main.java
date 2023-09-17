import Controller.SocialMediaController;
import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
        HttpClient webClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse response;

        HttpRequest postMessageRequest = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/register"))
        .POST(HttpRequest.BodyPublishers.ofString("{"+
                "\"username\": \"are\", " +
                "\"password\": \"hello message\", "))
        .header("Content-Type", "application/json")
        .build();
        try{
            response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            System.out.println(status);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        postMessageRequest = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/messages"))
        .POST(HttpRequest.BodyPublishers.ofString("{"+
                "\"posted_by\":1, " +
                "\"message_text\": \"hello message\", " +
                "\"time_posted_epoch\": 1669947792}"))
        .header("Content-Type", "application/json")
        .build();
        try{
            response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            System.out.println(status);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        

    }
}
