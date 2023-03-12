import java.util.List;
import java.util.ArrayList;

public class FoodMenu {
    // private fields
    private List<Food> menu;
    // constructor
    public FoodMenu() {
        Food f1 = new Food("Bang Bang", "nuggets tacos", 8);
        Food f2 = new Food("Steak House", "steak tacos", 7);
        Food f3 = new Food("TexMex", "chicken tacos", 6);
        this.menu = new ArrayList<>();
        this.menu.add(f1);
        this.menu.add(f2);
        this.menu.add(f3);
    }
    // getFood method
    public Food getFood(int index) {
        try {
            return this.menu.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    // getLowestCostFood method
    public Food getLowestCostFood() {
        Food lowestCostFood = this.menu.get(0);
        for (Food food : this.menu) {
            if (food.getPrice() < lowestCostFood.getPrice()) {
                lowestCostFood = food;
            }
        }
        return lowestCostFood;
    }
    // overriding the toString method
    public String toString() {
        String displayedMenu = "";
        int counter = 1;
        for (Food food : this.menu) {
            displayedMenu += counter +". "+ food + "\n";
            counter++;
        }
        return displayedMenu;
    }
}
