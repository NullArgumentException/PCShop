package de.bfwbb.products;

import de.bfwbb.shop.ShopCtrl;

import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public non-sealed class Mouse extends Product {
    private boolean isWireless;

    public boolean getIsWireless() {
        return isWireless;
    }

    private void setIsWireless(boolean wireless) {
        isWireless = wireless;
    }

    @Override
    public String toString() {
        return "Mouse [" +
                super.toString() +
                ", Wireless= " + ((isWireless) ? "yes" : "no") +
                "]";
    }

    public void editProperty(Scanner scan, int property) {
        if (property > super.getFieldCount()) {
            System.out.println("Is the mouse wireless? y/n");

            switch (scan.next()) {
                case "y", "Y" -> setIsWireless(true);
                case "n", "N" -> setIsWireless(false);
                default -> {
                    System.err.println("Input unrecognised, please try again.");
                    ShopCtrl.wait(500);
                    editProperty(scan, property);
                }
            }

            System.out.println("Wireless was set to: " + getIsWireless());

        } else {
            super.editProperty(scan, property);
        }
    }
}
