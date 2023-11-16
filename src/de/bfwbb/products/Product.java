package de.bfwbb.products;

import de.bfwbb.shop.ShopCtrl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public sealed abstract class Product
        permits Keyboard, Monitor, Motherboard, Mouse {
    private String make;
    private String model;
    private double price;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // lets the user enter the property chosen in the menu selection
    public void editProperty(int property) {
        Scanner scan = new Scanner(System.in);
        switch (property) {
            case 1 -> {
                System.out.println("Enter the make: ");
                setMake(scan.nextLine());
                System.out.println("Make was set to " + getMake());
                ShopCtrl.wait(500);
            }
            case 2 -> {
                System.out.println("Enter name of the model: ");
                setModel(scan.nextLine());
                System.out.println("Model name was set to " + getModel());
                ShopCtrl.wait(500);
            }
            case 3 -> {
                boolean retry;
                do {
                    retry = false;
                    System.out.println("Enter the price: ");
                    try {
                        double value = scan.nextDouble();
                        if (value == 0) {
                            System.err.println("Please enter a number greater than zero.");
                            retry = true;
                            ShopCtrl.wait(500);
                        } else {
                            setPrice(value);
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Wrong input, please enter a number.");
                        scan.nextLine();
                        retry = true;
                        ShopCtrl.wait(500);
                    }
                } while (retry);
                System.out.printf("Price was set to %.2f%n", getPrice());
                ShopCtrl.wait(500);
            }
        }
        scan.close();
    }

    // returns the amount of properties a Class/Object has
    public int getFieldCount() {
        return getClass().getDeclaredFields().length;
    }

    public boolean checkForEmptyFields() {
        List<Field> pList = Arrays.asList(getClass().getSuperclass().getDeclaredFields());
        pList.addAll(Arrays.asList(getClass().getDeclaredFields()));
        try {
            for (Field p : pList) {
                Object fieldValue = p.get(this);
                if (fieldValue == null || (fieldValue instanceof Number && ((Number) fieldValue).doubleValue() == 0.0)) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
