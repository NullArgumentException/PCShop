package de.bfwbb.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bfwbb.products.Product;
import de.bfwbb.terminal.Terminal;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author NullArgumentException
 */
public class Main extends Application {

    private static final String JSON_PATH = "db/products.json";
    private static Terminal terminal;

    public static void main(String[] args) {
        File jsonFile = new File(JSON_PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        Product[] products;

        try {
            // using Jackson library to read stored product objects from a json file
            products = objectMapper.readValue(jsonFile, Product[].class);
            System.out.println("product objects successfully read from json file");
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            // use empty array if the json file does not exist
            products = new Product[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        terminal = new Terminal("nargex", products);

        launch(args);

        try {
            // using Jackson library to write product objects into a json file to restore them later
            products = terminal.getProductList().toArray(new Product[0]);
            objectMapper.writeValue(jsonFile, products);
            System.out.println("product objects successfully written to json file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        terminal.start(primaryStage);
    }
}