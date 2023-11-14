import java.util.Scanner;

public class CloseShop {
    public static void main(String[] args) {
        closeShop();
    }
    private static void closeShop() {
        System.out.println("Really exit shop? y/n");
        try (Scanner scan = new Scanner(System.in)) {
            switch (scan.next()) {
                case "y", "Y" -> {
                    System.out.println("Closing shop.");
                    System.exit(0);
                }
                case "n", "N" -> System.out.println("back to main menu");
                default -> {
                    System.err.println("Unknown input, try again.");
                    closeShop();
                }
            }
        }
    }
}
