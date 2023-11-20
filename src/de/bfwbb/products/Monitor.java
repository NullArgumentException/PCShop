package de.bfwbb.products;

import de.bfwbb.shop.ShopCtrl;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public non-sealed class Monitor extends Product {
    private int refreshRate;
    private String resolution;

    public int getRefreshRate() {
        return refreshRate;
    }

    private void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public String getResolution() {
        return resolution;
    }

    private void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "Monitor [" +
                super.toString() +
                ", Refresh rate= " + refreshRate +
                ", Resolution= " + resolution +
                "]";
    }

    public void editProperty(Scanner scan, int property) {
        if (property > super.getFieldCount()) {
            switch (property) {
                case 4 -> {
                    boolean retry;
                    do {
                        retry = false;
                        System.out.println("Enter the Monitor's refresh rate: ");
                        try {
                            setRefreshRate(scan.nextInt());
                        } catch (InputMismatchException e) {
                            System.err.println("Wrong input, please enter a whole number.");
                            scan.nextLine();
                            retry = true;
                            ShopCtrl.wait(500);
                        }
                    } while (retry);
                    System.out.println("Refresh rate was set to: " + getRefreshRate());
                    ShopCtrl.wait(500);
                }
                case 5 -> {
                    System.out.println("Enter the Monitor's resolution: ");
                    setResolution(scan.nextLine());
                    System.out.println("Resolution was set to: " + getResolution());
                    ShopCtrl.wait(500);
                }
            }
        } else {
            super.editProperty(scan, property);
        }
    }
}
