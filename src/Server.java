import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server{
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port " + port);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // Get the requested URI
            String uri = t.getRequestURI().toString();
            // To handle the root path
            if (uri.equals("/")) {
                uri = "/index.html"; // Default HTML page
            }
            // The path to html pages to request
            String response = loadFileAsString("C:\\Users\\pc\\IdeaProjects\\ClientServer\\HTML files" + uri);
            // Send the HTML content as the response
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        private String loadFileAsString(String filePath) throws IOException {
            Path path = Paths.get(filePath);
            return new String(Files.readAllBytes(path));
        }
    }
}
