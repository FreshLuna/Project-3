package Server;

import Classes.Activity;
import Events.Published;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import Controller.CancelController;
import Controller.CancelResult;
import Controller.SignUpResult;
import Controller.SignupController;

import java.io.IOException;

import static Database.ActivityService.filterFromJson2;


public class PostHandler {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final SignupController signupController = new SignupController();
    private final CancelController cancelController= new CancelController();

    public String handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // retrieves path
        String body = new String(exchange.getRequestBody().readAllBytes()); // retrieves the data send by the user
        System.out.println("RAW JSON RECEIVED:\n" + body);


        return switch (path) {
            case "/server/echo" -> body;

            case "/server/filtered" -> {

                System.out.println("step 1" + body);
                System.out.println("step 2" + filterFromJson2(body));

                yield filterFromJson2(body);
            }

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

            case "/server/shutdown" -> "bye";

            default -> "Unknown POST path: " + path;
        };
    }
}
