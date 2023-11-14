import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerLoop {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean retry;
        int auswahl = 0;
        do {
            retry = false;
            try {
                auswahl = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Bitte geben Sie eine Zahl ein.");
                retry = true;
                scanner.nextLine();
            }
        } while (retry);

        System.out.println("Auswahl: " + auswahl);
    }
}