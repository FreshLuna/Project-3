package Server;

import Classes.Activity;
import Events.Published;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import Events.SignedUp;

public class PostHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    public String handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // retrieves path
        String body = new String(exchange.getRequestBody().readAllBytes()); // retrieves the data send by the user

        return switch (path) {
            case "/server/echo" -> body;

            case "/server/participants" -> {
                // Save posted participant data to participants.txt
                SignedUp.appendParticipant(body);
                yield "Participant saved";
            }

            case "/server/publish" -> {

                Activity activity = mapper.readValue(body, Activity.class);

                Published.publish(activity);

                yield "Activity published";
            }

            case "/server/shutdown" -> "bye";

            default -> "Unknown POST path: " + path;
        };
    }
}
