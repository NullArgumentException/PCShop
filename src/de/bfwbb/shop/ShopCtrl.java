package de.bfwbb.shop;

import de.bfwbb.products.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public class ShopCtrl {
    private String owner;
    private ArrayList<Product> pList;

    public ShopCtrl(String owner, ArrayList<Product> pList) {
        this.owner = owner;
        this.pList = pList;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<Product> getpList() {
        return pList;
    }

    public void setpList(ArrayList<Product> pList) {
        this.pList = pList;
    }

    public void mainMenu(Scanner scan) {
        System.out.printf("""
                -------------------------------------------------------
                 PC-Shop             Main Menu   by: [%s]
                -------------------------------------------------------
                1) Add Product
                2) Edit Product
                3) Search Product
                4) Remove Product
                0) Close Shop
                -------------------------------------------------------%n""", owner);
        menuSelection(scan);
    }

    private void menuSelection(Scanner scan) {
        try {
            switch (scan.nextInt()) {
                case 0 -> closeShop(scan);
                case 1 -> addProduct(scan);
                case 2 -> editProduct(scan, null);
                case 3 -> searchProduct();
                case 4 -> removeProduct();
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            menuSelection(scan);
        }
    }

    private void addProduct(Scanner scan) {
        System.out.println("""
                -------------------------------------------------------
                 Choose the type of product to add
                -------------------------------------------------------
                1) Keyboard
                2) Monitor
                3) Motherboard
                4) Mouse
                0) Return to main menu
                -------------------------------------------------------""");
        try {
            switch (scan.nextInt()) {
                case 0 -> {
                    System.out.println("Returning to main menu");
                    wait(500);
                    mainMenu(scan);
                }
                case 1 -> {
                    Keyboard p = new Keyboard();
                    editProduct(scan, p);
                    pList.add(p);
                }
                case 2 -> {
                    Monitor p = new Monitor();
                    editProduct(scan, p);
                    pList.add(p);
                }
                case 3 -> {
                    Motherboard p = new Motherboard();
                    editProduct(scan, p);
                    pList.add(p);
                }
                case 4 -> {
                    Mouse p = new Mouse();
                    editProduct(scan, p);
                    pList.add(p);
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            wait(1000);
            addProduct(scan);
        }

        System.out.println("Do you want to add another product? y/n");
        switch (scan.next()) {
            case "y", "Y" -> {
                wait(1000);
                addProduct(scan);
            }
            case "n", "N" -> {
                System.out.println("Returning to main menu.");
                wait(1000);
                mainMenu(scan);
            }
            default -> {
                System.err.println("Unknown input, try again.");
                wait(500);
                closeShop(scan);
            }
        }
    }

    private void editProduct(Scanner scan, Product product) {
        String menu = """
                -------------------------------------------------------
                 Choose which property to edit
                -------------------------------------------------------
                1) Brand
                2) Model name
                3) Price""";

        switch (product) {
            case null -> {
                listProducts();
            }
            case Keyboard p -> {
                System.out.println(menu);
                System.out.println("4) Bluetooth");
                propMenuSelect(scan, p);
            }
            case Monitor p -> {
                System.out.println(menu);
                System.out.println("""
                        4) Refresh Rate
                        5) Resolution""");
                propMenuSelect(scan, p);
            }
            case Motherboard p -> {
                System.out.println(menu);
                System.out.println("4) Chipset");
                propMenuSelect(scan, p);
            }
            case Mouse p -> {
                System.out.println(menu);
                System.out.println("4) Wireless");
                propMenuSelect(scan, p);
            }
        }
    }

    private void propMenuSelect(Scanner scan, Product product) {
        System.out.println("""
                0) Save and back to main menu
                -------------------------------------------------------""");
        try {
            switch ((Integer) scan.nextInt()) {
                case 0 -> {
                    saveProduct(scan, product);
                }
                case Integer i when i <= product.getFieldCount() -> {
                    scan.nextLine();
                    product.editProperty(scan, i);
                    wait(1000);
                    editProduct(scan, product);
                }
                default -> throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            scan.nextLine();
            wait(1000);
            editProduct(scan, product);
        }
    }

    private void saveProduct(Scanner scan, Product product) {
        System.out.println("""
                This will check if all properties have been set, otherwise the product will be discarded.
                Are you sure you want to continue? y/n""");
        switch (scan.next()) {
            case "y", "Y" -> {
                if (product.checkForEmptyFields()) {
                    System.err.println("""
                            Product could not be saved because a property was not set.
                            Returning to main menu.""");
                    wait(1500);
                    mainMenu(scan);
                }
            }
            case "n", "N" -> {
                wait(500);
                editProduct(scan, product);
            }
            default -> {
                System.err.println("Unknown input, try again.");
                wait(500);
                saveProduct(scan, product);
            }
        }
    }

    private void searchProduct() {

    }

    private void removeProduct() {

    }

    private void closeShop(Scanner scan) {
        System.out.println("Really exit shop? y/n");
        switch (scan.next()) {
            case "y", "Y" -> {
                System.out.println("Closing shop.");
                System.exit(0);
            }
            case "n", "N" -> mainMenu(scan);
            default -> {
                System.err.println("Unknown input, try again.");
                wait(500);
                closeShop(scan);
            }
        }
    }

    private void listProducts() {
        // TODO: list up to 5 products and ask the user to either choose one or show the next 5
    }

    // pauses code execution for the given amount of time in milliseconds
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
