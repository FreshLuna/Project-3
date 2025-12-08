package Server;

import Classes.Activity;
import Database.FilterRequest;
import Events.FullyBooked;
import Events.Published;
import Events.Verified;
import Classes.Participant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.List;

import Events.SignedUp;
import controller.SignUpResult;
import controller.SignupController;

public class PostHandler {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final SignupController signupController = new SignupController();

    public String handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // retrieves path
        String body = new String(exchange.getRequestBody().readAllBytes()); // retrieves the data send by the user
        System.out.println("RAW JSON RECEIVED:\n" + body);

        return switch (path) {
            case "/server/echo" -> body;

            case "/server/participants" -> {
                SignUpResult result = signupController.processSignup(body);

                if (!result.isSuccess()){

                    yield  result.getMessage();
                }
                // Save posted participant data to participants.txt
                SignedUp.appendParticipant(body);
                yield "Deltager er blevet tilmeldt";
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
