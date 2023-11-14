package de.bfwbb.products;

import de.bfwbb.shop.ShopCtrl;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public non-sealed class Motherboard extends Product {
    private String chipset;

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
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
                    System.out.println("Enter the chipset name: ");
                    setChipset(scan.nextLine());
                    System.out.println("Chipset was set to: " + getChipset());
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
