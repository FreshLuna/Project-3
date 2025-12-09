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
import controller.CancelController;
import controller.CancelResult;
import controller.SignUpResult;
import controller.SignupController;

import static Database.DataLoader.loadActivities;

public class PostHandler {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final SignupController signupController = new SignupController();
    private final CancelController cancelController= new CancelController();

    public String handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // retrieves path
        String body = new String(exchange.getRequestBody().readAllBytes()); // retrieves the data send by the user
        //System.out.println("RAW JSON RECEIVED:\n" + body);

        return switch (path) {
            case "/server/echo" -> body;

            case "/server/participants" -> {
                SignUpResult result = signupController.processSignup(body);

                if (!result.isSuccess()){

                    yield  result.getMessage();
                }

                yield "Deltager er blevet tilmeldt";
            }
            case "/server/cancel" -> {
                CancelResult cancelResult = cancelController.processCancel(body);

                        if(!cancelResult.isSuccess()){

                            yield "afmelding mislykkedes";
                        }
                        yield "Deltager fjernet";
            }

            case "/server/publish" -> {

                Activity activity = mapper.readValue(body, Activity.class);

                Published.publish(activity);

                yield "Activity published";
            }

            case "/server/activities/filter" -> {
                try {
                    // 'body' contains the JSON request

                    // Get all activities from your service
                    List<Activity> allActivities = loadActivities();

                    // Use FilterRequest to filter activities from JSON
                    List<Activity> filteredActivities = FilterRequest.filterFromJson(allActivities, body);
                    ObjectMapper mapper = new ObjectMapper();

                    String jsonResult = mapper.writeValueAsString(filteredActivities);

                    // Print JSON to console
                    //System.out.println("Filtered JSON: " + jsonResult);

                    // Return the filtered activities as JSON
                    yield new ObjectMapper().writeValueAsString(filteredActivities);

                } catch (Exception e) {
                    e.printStackTrace();
                    // In case of error, return empty list as JSON
                    yield "[]";
                }
            }

            case "/server/shutdown" -> "bye";

            default -> "Unknown POST path: " + path;
        };
    }
}
