import java.util.HashMap;
import java.util.Map;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class InventoryService{
    static Map<String, Integer> stock = new HashMap<>(Map.of("Laptop", 2));
    public static void main(String[] args)throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/reserve", exchange -> {
            if(!exchange.getRequestMethod().equals("POST")){
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }
            String item = exchange.getRequestURI().getQuery().split("=")[1];
            int qty = stock.getOrDefault(item, 0);
            String body;
            if(qty == 0) body = "OUT OF STOCK";
            else{
                stock.put(item, qty - 1);
                body = "RESERVED left=" + (qty - 1);
            }
            System.out.println("[inventory PID " + ProcessHandle.current().pid() + "] " + item + " -> " + body);
            exchange.sendResponseHeaders(200, body.length());
            exchange.getResponseBody().write(body.getBytes());
            exchange.close();
        });
        server.start();
        System.out.println("Inventory running on 8081, PID " + ProcessHandle.current().pid());
    }
}