package de.bfwbb.products;

import de.bfwbb.shop.ShopCtrl;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public non-sealed class Mouse extends Product {
    private boolean isWireless;

    public boolean getIsWireless() {
        return isWireless;
    }

    public void setIsWireless(boolean wireless) {
        isWireless = wireless;
    }

    private void editProperty() {
        try (Scanner scan = new Scanner(System.in)) {
            switch (scan.nextInt()) {
                case 1 -> {
                    super.editProperty(1);
                }
                case 2 -> {
                    super.editProperty(2);
                }
                case 3 -> {
                    super.editProperty(3);
                }
                case 4 -> {
                    boolean retry;
                    do {
                        retry = false;
                        System.out.println("Is the mouse wireless? y/n");
                        switch (scan.next()) {
                            case "y", "Y" -> setIsWireless(true);
                            case "n", "N" -> setIsWireless(false);
                            default -> {
                                System.err.println("Input unrecognised, please try again.");
                                retry = true;
                                ShopCtrl.wait(500);
                            }
                        }
                    } while (retry);
                    System.out.println("Wireless was set to: " + getIsWireless());
                    ShopCtrl.wait(500);
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            ShopCtrl.wait(1000);
            editProperty();
        }
    }

    public int getFieldCount() {
        return super.getFieldCount() + getClass().getDeclaredFields().length;
    }
}
