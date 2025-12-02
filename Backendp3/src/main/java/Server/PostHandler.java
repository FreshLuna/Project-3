package Server;

import Classes.Activity;
import Database.FilterRequest;
import Events.Published;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.List;

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

            case "/server/activities/filter" -> {
                FilterRequest request = mapper.readValue(body, FilterRequest.class);

                // get filtered requests
                List<Activity> result = PostGetServer.getActivityService().filterByTags(request.getSelectedTags());

                // return JSON
                yield mapper.writeValueAsString(result);
            }

            case "/server/shutdown" -> "bye";

            default -> "Unknown POST path: " + path;
        };
    }
}
