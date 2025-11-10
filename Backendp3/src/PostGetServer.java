import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.KeyStore;

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

        // --- Create HTTPS server ---
        HttpsServer server = HttpsServer.create(new InetSocketAddress(8443), 0);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext));

        server.createContext("/server", exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            String method = exchange.getRequestMethod();
            String response;

            switch (method) {
                case "OPTIONS":
                    exchange.sendResponseHeaders(204, -1);
                    return;

                case "GET":
                    response = "test";
                    break;

                case "POST":
                    String body = new String(exchange.getRequestBody().readAllBytes());
                    response = body.equals(".") ? "bye" : body;
                    break;

                default:
                    response = "Unsupported method: " + method;
            }


            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }

        });

        server.start();
        System.out.println("HTTPS server running on https://localhost:8443/server");
    }
}

// en fil hver til POST, GET

//POST: lave et activity, create account, modify activities, register user osv...

//GET: hente activities osv.. 