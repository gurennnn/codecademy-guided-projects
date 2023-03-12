import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String customerName = input.nextLine();
        int money = input.nextInt();
        while (money < 0) {
            System.out.println("Invalid starting money. Try Again!");
            money = input.nextInt();
        }
        Customer customer = new Customer(customerName, money);
        TakeOutSimulator takeOutSimulator = new TakeOutSimulator(customer, input);
        takeOutSimulator.startTakeOutSimulator();
    }
}
