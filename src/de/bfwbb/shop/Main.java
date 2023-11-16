package de.bfwbb.shop;

import de.bfwbb.products.Product;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Product> pList = new ArrayList<>();
        ShopCtrl s1 = new ShopCtrl("Stefan Schubmehl", pList);
        s1.mainMenu(scan);
    }
}