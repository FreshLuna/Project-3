import com.sun.net.httpserver.*;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;


public class PostGetServer {
    public static void main(String[] args) throws Exception {


        // security setup for https encryption
        char[] passphrase = "password".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        try (FileInputStream fis = new FileInputStream("keystore.jks")) {
            ks.load(fis, passphrase);
        }
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);

        // Creates HTTPS server
        HttpsServer server = HttpsServer.create(new InetSocketAddress(8443), 0);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext));

        server.createContext("/server", exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            // Compacted The methodSwitch see getHandler and postHandler to add methods :D
            String response = methodSwitch(exchange);


            //sends response back to client computer
            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }

        });

        server.start();
        System.out.println("HTTPS server running on https://localhost:8443/server");
    }



    private static String methodSwitch(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response;

        GetHandler getHandler = new GetHandler();
        PostHandler postHandler = new PostHandler();

        switch (method) {
            case "OPTIONS":
                exchange.sendResponseHeaders(204, -1);
                return "";

            case "GET":
                response = getHandler.handle(exchange);
                break;

            case "POST":
                response = postHandler.handle(exchange);

                break;

            default:
                response = "Unsupported method: " + method;
        }

        return response;
    }
}