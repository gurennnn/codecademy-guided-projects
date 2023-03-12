public class Food implements PricedItem<Integer> {
    // private fields
    private String name;
    private String description;
    private int price;
    // constructor
    public Food(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    // overriding the abstract methods
    @Override
    public Integer getPrice() {
        return this.price;
    }
    @Override
    public void setPrice(Integer price) {
        this.price = price;
    }
    // overriding toString
    @Override
    public String toString() {
        return String.format("%s: %s  Cost: $%d", this.name, this.description, this.price);
    }
}
