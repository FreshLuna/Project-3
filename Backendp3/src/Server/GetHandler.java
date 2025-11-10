package Server;

import com.sun.net.httpserver.HttpExchange;

public class GetHandler {
    public String handle(HttpExchange exchange) {

        // we retrieve the full path the user attempts to access
        String path = exchange.getRequestURI().getPath();

        // switch handles the different possible paths, write full path for now
        // since im to lazy to cut off the server part mwuhahahhaha!!! >:3
        return switch (path) {
            case "/server/users" -> "List of users";
            case "/server/status" -> "Server is running";
            case "/server/activities" -> "bam";
            default -> "Unknown GET path: " + path;
        };
    }
}