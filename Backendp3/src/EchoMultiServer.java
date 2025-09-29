import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);

        while(true) {
            (new EchoClientHandler(this.serverSocket.accept())).start();
        }
    }

    public void stop() throws IOException {
        this.serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        EchoMultiServer server = new EchoMultiServer();
        server.start(6666);
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

                String inputLine;
                while((inputLine = this.in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        this.out.println("bye");
                        break;
                    }

                    this.out.println(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    this.in.close();
                    this.out.close();
                    this.clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        }
    }
}
