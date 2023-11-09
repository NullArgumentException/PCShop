import java.util.InputMismatchException;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            switch ((Integer) scan.nextInt()) {
                case Integer i when i == 0 -> {
                    System.out.println("Integer " + i);
                }
                case Integer i when i == 1 -> {
                    System.out.println("Integer " + i);
                }
                case Integer i when i == 2 -> {
                    System.out.println("Integer " + i);
                }
                case Integer i when i == 3 -> {
                    System.out.println("Integer " + i);
                }
                default -> {
                    throw new InputMismatchException();
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
        }
    }

}
