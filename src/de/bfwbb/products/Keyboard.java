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

    public void setHasBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
    }

    public void editProperty(int property) {
        if (property <= super.getFieldCount()) super.editProperty(property);
        else {
            Scanner scan = new Scanner(System.in);
            System.out.println("Does the keyboard have bluetooth? y/n");
            switch (scan.next()) {
                case "y", "Y" -> setHasBluetooth(true);
                case "n", "N" -> setHasBluetooth(false);
                default -> {
                    System.err.println("Input unrecognised, please try again.");
                    ShopCtrl.wait(500);
                    editProperty(property);
                }
            }
            System.out.println("Bluetooth was set to: " + getHasBluetooth());
            scan.close();
        }
    }

    public int getFieldCount() {
        return super.getFieldCount() + getClass().getDeclaredFields().length;
    }
}


