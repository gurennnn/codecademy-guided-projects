public interface PricedItem <T extends Number> {
    // abstract methods
    public T getPrice();
    public void setPrice(T price);
}
