import java.util.Map;
import java.util.HashMap;

public class ShoppingBag <T extends PricedItem<Integer>> {
    // private fields
    private Map<T, Integer> shoppingBag;
    // empty constructor
    public ShoppingBag() {
        this.shoppingBag = new HashMap<>();
    }
    // addItem method
    public void addItem(T item) {
        // if items exists, its count is increased by 1, otherwise we add it for the first time so the count is 1
        if (this.shoppingBag.containsKey(item)) {
            int currentCount = this.shoppingBag.get(item) + 1;
            this.shoppingBag.put(item, currentCount);
        } else {
            this.shoppingBag.put(item, 1);
        }
    }
    // getTotalPrice method
    public int getTotalPrice() {
        int totalPrice = 0;
        for (Map.Entry<T, Integer> pair : this.shoppingBag.entrySet()) {
            totalPrice += pair.getKey().getPrice() * pair.getValue();
        }
        return totalPrice;
    }
}
