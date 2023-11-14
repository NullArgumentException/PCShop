import de.bfwbb.products.Product;
import de.bfwbb.shop.ShopCtrl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CheckSelection {
    public static void main(String[] args) {
        propMenuSelect();
    }
    public static void propMenuSelect() {
        System.out.println("Enter number");
        Scanner scan = new Scanner(System.in);
        try {
            switch ((Integer) scan.nextInt()) {
                case 0 -> System.out.println("zero");
                case Integer i when i <= 4 -> System.out.println(i);
                default -> {
                    throw new InputMismatchException();
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            propMenuSelect();
        } finally {
            scan.close();
        }
    }
}
