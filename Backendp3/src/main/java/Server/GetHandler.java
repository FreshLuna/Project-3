package Server;
// import Database.ActivityService;
import com.sun.net.httpserver.HttpExchange;
import static Database.DatabaseToStringReader.fileReader;

public class GetHandler {
    public String handle(HttpExchange exchange) {
        // we retrieve the full path the user attempts to access
        String path = exchange.getRequestURI().getPath();

        // switch handles the different possible paths, write full path for now
        // since im to lazy to cut off the server part mwuhahahhaha!!! >:3
        return switch (path) {
            case "/server/users" -> "List of users";
            case "/server/status" -> "Server is running";
            case "/server/activities" -> fileReader("activities.json");
            // case "/server/activities/filter" -> ActivityService activityService = new ActivityService("data/activities.json");
            case "/server" -> "this is what it says on the server!!!";
            default -> "Unknown GET path: " + path;
        };

    }
}