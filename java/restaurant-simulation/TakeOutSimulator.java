import java.util.Scanner;

public class TakeOutSimulator {
    // private fields
    private Customer customer;
    private FoodMenu menu;
    private Scanner input;
    // constructor
    public TakeOutSimulator(Customer customer, Scanner input) {
        this.customer = customer;
        this.menu = new FoodMenu();
        this.input = input;
    }
    // getOutputOnIntInput method
    private <T> T getOutputOnIntInput(String userInputPrompt, IntUserInputRetriever<T> intUserInputRetriever) {
        System.out.println(userInputPrompt);
        while (true) {
            if (!this.input.hasNextInt()) {
                System.out.println("Input needs to be an integer.");
                continue;
            }
            int retrievedInput = this.input.nextInt();
            try {
                return intUserInputRetriever.produceOutputOnIntUserInput(retrievedInput);
            } catch (IllegalArgumentException e) {
                System.out.println(retrievedInput + " is not a valid input. Try Again!");
            }
        }
    }
    // shouldSimulate method
    public boolean shouldSimulate() {
        String userPrompt = "Enter 1 to PROCEED the simulation or 0 to EXIT the program: ";
        IntUserInputRetriever<Boolean> intUserInputRetriever = (selection) -> {
            if (selection == 1 && this.customer.getMoney() >= this.menu.getLowestCostFood().getPrice()) return true;
            else if (selection == 0 || this.customer.getMoney() < this.menu.getLowestCostFood().getPrice()) return false;
            else throw new IllegalArgumentException();
        };
        return this.getOutputOnIntInput(userPrompt, intUserInputRetriever);
    }
    // getMenuSelection method
    public Food getMenuSelection() {
        String userPrompt = "Today's Menu Option!\n\n" + this.menu + "\nChoose a menu item: ";
        IntUserInputRetriever<Food> intUserInputRetriever = (selection) -> {
            Food food = this.menu.getFood(selection);
            if (food != null) return food;
            else throw new IllegalArgumentException();
        };
        return this.getOutputOnIntInput(userPrompt, intUserInputRetriever);
    }
    // isStillOrdering method
    public boolean isStillOrderingFood() {
        String userPrompt = "Enter 1 to CONTINUE shopping or 0 to CHECKOUT: ";
        IntUserInputRetriever<Boolean> intUserInputRetriever = (selection) -> {
            if (selection == 1) return true;
            else if (selection == 0) return false;
            else throw new IllegalArgumentException();
        };
        return this.getOutputOnIntInput(userPrompt, intUserInputRetriever);
    }
    // checkoutCustomer method
    public void checkoutCustomer(ShoppingBag<Food> shoppingBag) {
        System.out.println("Processing payment...");
        int remainingMoney = this.customer.getMoney() - shoppingBag.getTotalPrice();
        this.customer.setMoney(remainingMoney);
        System.out.printf("Your remaining money: $%d\n", this.customer.getMoney());
        System.out.println("Thank you and enjoy your food!");
    }
    // takeOutPrompt method
    public void takeOutPrompt() {
        ShoppingBag<Food> shoppingBag = new ShoppingBag<>();
        int customerMoneyLeft = this.customer.getMoney();
        while (true) {
            System.out.println("Your remaining credit is: $" + customerMoneyLeft);
            System.out.println("Today's Menu Options!\n");
            System.out.println(this.menu);
            System.out.println("Choose a menu item: ");
            int menuItemNum = this.input.nextInt();
            Food menuItemFood = this.menu.getFood(menuItemNum);
            if (customerMoneyLeft >= menuItemFood.getPrice()) {
                customerMoneyLeft -= menuItemFood.getPrice();
                shoppingBag.addItem(menuItemFood);
            } else {
                System.out.println("Oops! Looks like you don't have enough for that. Choose anotherItem or checkout.");
                System.out.println("Enter 1 for ORDERING, and 0 for CHECKOUT");
                int isOrdering = this.input.nextInt();
                if (isOrdering == 0) break;
            }
        }
        this.checkoutCustomer(shoppingBag);
    }
    // startTakeOutSimulator method
    public void startTakeOutSimulator() {
        System.out.println("Hello, welcome to my restaurant!");
        while (this.shouldSimulate()) {
            System.out.println("Welcome " + this.customer.getName() + "!");
            this.takeOutPrompt();
        }
    }
}
