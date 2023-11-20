package de.bfwbb.products;

import de.bfwbb.shop.ShopCtrl;

import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public non-sealed class Keyboard extends Product {
    private boolean hasBluetooth;

    public boolean getHasBluetooth() {
        return hasBluetooth;
    }

    private void setHasBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
    }

    @Override
    public void editProperty(Scanner scan, int property) {
        if (property > super.getFieldCount()) {
            System.out.println("Does the keyboard have bluetooth? y/n");

            switch (scan.next()) {
                case "y", "Y" -> setHasBluetooth(true);
                case "n", "N" -> setHasBluetooth(false);
                default -> {
                    System.err.println("Input unrecognised, please try again.");
                    ShopCtrl.wait(500);
                    editProperty(scan, property);
                }
            }

            System.out.println("Bluetooth was set to: " + getHasBluetooth());

        } else {
            super.editProperty(scan, property);
        }
    }

    @Override
    public int getFieldCount() {
        return super.getFieldCount() + getClass().getDeclaredFields().length;
    }
}