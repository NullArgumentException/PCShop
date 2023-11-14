package de.bfwbb.shop;

import de.bfwbb.products.*;

import java.lang.reflect.Field;
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

    public void mainMenu() {
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
        menuSelection();
    }

    private void menuSelection() {
        try (Scanner scan = new Scanner(System.in)) {
            switch (scan.nextInt()) {
                case 0 -> closeShop();
                case 1 -> addProduct();
                case 2 -> editProduct(null);
                case 3 -> searchProduct();
                case 4 -> removeProduct();
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            wait(1000);
            menuSelection();
        }
    }

    private void addProduct() {
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
        try (Scanner scan = new Scanner(System.in)) {
            switch (scan.nextInt()) {
                case 0 -> {
                    System.out.println("Returning to main menu");
                    wait(500);
                    mainMenu();
                }
                case 1 -> {
                    Keyboard p = new Keyboard();
                    editProduct(p);
                    pList.add(p);
                }
                case 2 -> {
                    Monitor p = new Monitor();
                    editProduct(p);
                    pList.add(p);
                }
                case 3 -> {
                    Motherboard p = new Motherboard();
                    editProduct(p);
                    pList.add(p);
                }
                case 4 -> {
                    Mouse p = new Mouse();
                    editProduct(p);
                    pList.add(p);
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            wait(1000);
            addProduct();
        }
    }

    private void editProduct(Product product) {
        String menu = """
                -------------------------------------------------------
                Choose which property to edit
                -------------------------------------------------------
                1) Make
                2) Model name
                3) Price""";

        switch (product) {
            case null -> {
                listProducts();
            }
            case Keyboard p -> {
                System.out.println(menu);
                System.out.println("4) Bluetooth");
                propMenuSelect(p);
            }
            case Monitor p -> {
                System.out.println(menu);
                System.out.println("""
                        4) Refresh Rate
                        5) Resolution""");
                propMenuSelect(p);
            }
            case Motherboard p -> {
                System.out.println(menu);
                System.out.println("4) Chipset");
                propMenuSelect(p);
            }
            case Mouse p -> {
                System.out.println(menu);
                System.out.println("4) Wireless");
                propMenuSelect(p);
            }
        }
    }

    private void propMenuSelect(Product p) {
        System.out.println("""
                0) Save and back to main menu
                -------------------------------------------------------""");
        Scanner scan = new Scanner(System.in);
        try {
            switch ((Integer) scan.nextInt()) {
                case 0 -> {
                    saveProduct(p);
                }
                case Integer i when i <= p.getFieldCount() -> p.editProperty(i);
                default -> throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("Input unrecognised, please choose a number from the list.");
            wait(1000);
            editProduct(p);
        } finally {
            scan.close();
        }
    }

    private void saveProduct(Product product) {
        System.out.println("""
                This will check if all properties have been set, otherwise the product will be discarded.
                Are you sure you want to continue? y/n
                """);
        try (Scanner scan = new Scanner(System.in)) {
            switch (scan.next()) {
                case "y", "Y" -> {
                    Field[] pArr = product.getClass().getDeclaredFields();
                    for (Field p :
                            pArr) {
                        if (p == null) {
                            System.err.println("""
                                    Product could not be saved because a property was not set.
                                    Returning to main menu.""");
                            wait(1500);
                            mainMenu();
                        }
                    }
                }
                case "n", "N" -> mainMenu();
                default -> {
                    System.err.println("Unknown input, try again.");
                    wait(500);
                    saveProduct(product);
                }
            }
        }
    }

    private void searchProduct() {

    }

    private void removeProduct() {

    }

    private void closeShop() {
        System.out.println("Really exit shop? y/n");
        try (Scanner scan = new Scanner(System.in)) {
            switch (scan.next()) {
                case "y", "Y" -> {
                    System.out.println("Closing shop.");
                    System.exit(0);
                }
                case "n", "N" -> mainMenu();
                default -> {
                    System.err.println("Unknown input, try again.");
                    wait(500);
                    closeShop();
                }
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
            System.out.println(e.getMessage());
        }
    }
}
