import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class PostHandler {
    public String handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String body = new String(exchange.getRequestBody().readAllBytes());

        return switch (path) {
            case "/echo" -> body;
            case "/shutdown" -> "bye";
            default -> "Unknown POST path: " + path;
        };
    }
}
