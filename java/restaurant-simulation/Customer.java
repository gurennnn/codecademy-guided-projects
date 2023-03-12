public class Customer {
    // private fields
    private String name;
    private int money;
    // constructor
    public Customer(String name, int money) {
        this.name = name;
        this.money = money;
    }
    // getters
    public String getName() {
        return this.name;
    }
    public int getMoney() {
        return this.money;
    }
    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setMoney(int money) {
        this.money = money;
    }
}