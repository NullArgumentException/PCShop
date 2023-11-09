import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public sealed abstract class Product
        permits Keyboard, Monitor, Motherboard, Mouse {
    private String make;
    private String model;
    private double price;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // lets the user enter the property chosen in the menu selection
    protected void editProperty(int property) {
        Scanner scan = new Scanner(System.in);
        switch (property) {
            case 1 -> {
                System.out.println("Enter the make: ");
                setMake(scan.nextLine());
                System.out.println("Make was set to " + getMake());
                ShopCtrl.wait(500);
            }
            case 2 -> {
                System.out.println("Enter name of the model: ");
                setModel(scan.nextLine());
                System.out.println("Model name was set to " + getModel());
                ShopCtrl.wait(500);
            }
            case 3 -> {
                // TODO: add check for 0 value
                boolean retry;
                do {
                    retry = false;
                    System.out.println("Enter the price: ");
                    try {
                        setPrice(scan.nextDouble());
                    } catch (InputMismatchException e) {
                        System.err.println("Wrong input, please enter a number.");
                        retry = true;
                        ShopCtrl.wait(500);
                    }
                } while (retry);
                System.out.printf("Price was set to %.2f%n", getPrice());
                ShopCtrl.wait(500);
            }
        }
        scan.close();
    }

    protected void checkProperties() {

    }

    // returns the amount of properties a Class/Object has
    public int getFieldCount() {
        return getClass().getDeclaredFields().length;
    }
}
