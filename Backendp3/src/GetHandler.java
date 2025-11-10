import com.sun.net.httpserver.HttpExchange;

public class GetHandler {
    public String handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();

        return switch (path) {
            case "/server/users" -> "List of users";
            case "/server/status" -> "Server is running";
            case "/server/activities" -> "bam";
            default -> "Unknown GET path: " + path;
        };
    }
}