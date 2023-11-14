package de.bfwbb.shop;

import de.bfwbb.products.Product;

import java.util.ArrayList;

/**
 * @author NullArgumentException
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Product> pList = new ArrayList<>();
        ShopCtrl s1 = new ShopCtrl("Stefan Schubmehl", pList);
        s1.mainMenu();
    }
}