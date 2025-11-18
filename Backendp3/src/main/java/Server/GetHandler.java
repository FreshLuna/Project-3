package Server;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import static Database.DatabaseToStringReader.readFileToString;
import static Database.DatabaseToStringReader.readAbsoluteFile;;

public class GetHandler {
    public String handle(HttpExchange exchange) throws IOException {
        // we retrieve the full path the user attempts to access
        String path = exchange.getRequestURI().getPath();

        // switch handles the different possible paths, write full path for now
        //since im to lazy to cut off the server part mwuhahahhaha!!! >:3
        return switch (path) {
            case "/server/users" -> "List of users";
            case "/server/status" -> "Server is running";
            case "/server/activities" -> readAbsoluteFile("/home/lunaw/Documents/GitHub/Project-3/Backendp3/src/main/resources/activities.txt");
            case "/server" -> "this is what it says on the server!!!";
            default -> "Unknown GET path: " + path;
        };

        // shit old code uggo
    }
}