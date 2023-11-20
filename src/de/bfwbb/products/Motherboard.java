package de.bfwbb.products;

import de.bfwbb.shop.ShopCtrl;

import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public non-sealed class Motherboard extends Product {
    private String chipset;

    public String getChipset() {
        return chipset;
    }

    private void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public void editProperty(Scanner scan, int property) {
        if (property > super.getFieldCount()) {
            System.out.println("Enter the chipset name: ");
            setChipset(scan.nextLine());
            System.out.println("Chipset was set to: " + getChipset());
            ShopCtrl.wait(500);
        } else {
            super.editProperty(scan, property);
        }
    }
}
