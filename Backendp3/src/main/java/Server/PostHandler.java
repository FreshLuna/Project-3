package Server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class PostHandler {
    public String handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // retrieves path
        String body = new String(exchange.getRequestBody().readAllBytes()); // retrieves the data send by the user

        return switch (path) {
            case "/server/echo" -> body;
            case "/server/shutdown" -> "bye";
            default -> "Unknown POST path: " + path;
        };
    }
}
