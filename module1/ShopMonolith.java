import java.util.HashMap;
import java.util.Map;

public class ShopMonolith{
    //---- Inventory module
    static Map<String, Integer> stock = new HashMap<>(Map.of("laptop", 2));

    static boolean reserve(String item){
        int qty = stock.getOrDefault(item, 0);
        if(qty == 0) return false;
        stock.put(item, qty - 1);
        return true;
    }

    //---- Order module
    static String placeOrder(String item){
        long start = System.nanoTime();
        boolean ok = reserve(item);
        long micros = (System.nanoTime() - start) / 1000;
        return (ok ? "Order Placed: " : "Out of Stock: ") + item
        + " | stock left=" + stock.get(item)
        + "| call took " + micros + " us";
    }
    public static void main(String[] args) {
        System.out.println("PID "+ ProcessHandle.current().pid());
        System.out.println(placeOrder("laptop"));
        System.out.println(placeOrder("laptop"));
        System.out.println(placeOrder("laptop"));
    }
}