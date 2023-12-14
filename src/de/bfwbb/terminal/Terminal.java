package de.bfwbb.terminal;

import de.bfwbb.products.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Terminal class represents a terminal window in a PC-Shop application.
 * It provides methods for managing the terminal window layout, displaying menus,
 * adding, editing, searching, and removing products, and interacting with the user.
 *
 * @author nargex
 * @see <a href="https://github.com/NullArgumentException/PCShop">GitHub page</a>
 */
public class Terminal extends Application {
    // "typing" speed for the animateText() method
    private static final double TEXT_SPEED = 0.005;
    private static final String PATH_TO_FONT = "font/Hack-Regular.ttf";
    private static final String border = "-".repeat(95);
    private final ArrayList<Product> productList;
    private final String owner;
    private Text output;
    private ScrollPane scrollPane;
    private TextField tfInput;
    private Timeline timeline;

    /**
     * Terminal constructor that sets the shop's owner and adds an array of existing product objects to the ArrayList
     *
     * @param owner    shop owner name to be displayed in the main menu
     * @param products array of product objects that should be added to the shop's inventory
     */
    public Terminal(String owner, Product[] products) {
        this.owner = owner;
        this.productList = new ArrayList<>(List.of(products));
    }

    /**
     * Retrieves the list of products in the terminal.
     *
     * @return The list of products.
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }

    /**
     * Sets up the terminal window layout.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {

        // object creation
        output = new Text(border);
        scrollPane = new ScrollPane();
        tfInput = new TextField();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        Font terminalFont;

        // tries to load the terminal font
        try {
            terminalFont = Font.loadFont(new FileInputStream(PATH_TO_FONT), 14);
        } catch (FileNotFoundException e) {
            System.err.println("Font file not found, using default");
            terminalFont = output.getFont();
        }
        output.setFont(terminalFont);
        output.setFill(Color.GREEN);


        // creates a scroll bar as needed when output exceeds the height of the pane
        scrollPane.setContent(output);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefHeight(600);
        // using calculated width of the border string as width for the pane
        scrollPane.setPrefWidth(output.getLayoutBounds().getWidth() + 2);
        scrollPane.setStyle("-fx-background: black;");

        tfInput.setStyle("-fx-prompt-text-fill: black;");

        borderPane.setCenter(scrollPane);
        borderPane.setBottom(tfInput);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("PC Shop Terminal");
        primaryStage.getIcons().add(new Image("file:icon/pc_shop.png"));
        primaryStage.show();
        primaryStage.centerOnScreen();

        mainMenu();
    }

    /**
     * Displays the main menu of the PC-Shop application.
     * Prompts the user to choose a menu item.
     */
    private void mainMenu() {
        tfCleanup();
        animateText(String.format("""
                                          %1$s
                                           PC-Shop                                   Main Menu   by: [%2$s]
                                          %1$s
                                           1) Add Product
                                           2) Edit Product
                                           3) Search Product
                                           4) Remove Product
                                           0) Close Shop
                                          %1$s""", border, owner));

        tfInputFormat("^[0-4]$", "Enter a number from the menu.");

        tfInput.setOnAction(e -> {
            String in = tfInput.getText();
            if (!in.isEmpty()) {
                switch (in) {
                    case "0" -> closeShop();
                    case "1" -> addProduct();
                    case "2" -> listProducts("edit", productList);
                    case "3" -> searchProduct();
                    case "4" -> listProducts("remove", productList);
                }
            }
        });
    }

    /**
     * Prompts the user to select the product type that should be added.
     * Creates a new object of the selected type and hands it to newProduct().
     */
    private void addProduct() {
        tfCleanup();
        animateText(String.format("""
                                          %1$s
                                           Choose the type of product to add
                                          %1$s
                                           1) Keyboard
                                           2) Monitor
                                           3) Motherboard
                                           4) Mouse
                                           0) Return to main menu
                                          %1$s""", border));
        tfInputFormat("^[0-4]$", "Enter a number from the menu.");
        tfInput.setOnAction(e -> {
            String in = tfInput.getText();
            if (!in.isEmpty()) {
                switch (in) {
                    case "0" -> mainMenu();
                    case "1" -> newProduct(new Keyboard(), 1);
                    case "2" -> newProduct(new Monitor(), 1);
                    case "3" -> newProduct(new Motherboard(), 1);
                    case "4" -> newProduct(new Mouse(), 1);
                }
            }
        });
    }

    /**
     * Property is checked against the total number of fields the class has
     * and calls editProperty() until all have been set.
     * After all properties have been set the object is added to the list of products.
     *
     * @param product  The product to be added.
     * @param property The index of the property to be edited next.
     */
    private void newProduct(Product product, int property) {
        tfCleanup();
        if (property <= product.fieldCount()) {
            editProperty(product, property, true);
        } else {
            productList.add(product);

            animateText(String.format("""
                                              %1$s
                                               Product was added to the shop
                                              %1$s
                                               %2$s
                                              %1$s
                                               Do you want to add another Product?
                                              %1$s""", border, product));

            tfInputFormat("^[yYnN]$", "Enter 'y' or 'n'.");

            tfInput.setOnAction(e -> {
                String in = tfInput.getText();
                if (!in.isEmpty()) {
                    switch (in) {
                        case "y", "Y" -> addProduct();
                        case "n", "N" -> mainMenu();
                    }
                }
            });
        }
    }

    /**
     * Lists the products based on the given mode and product list.
     *
     * @param mode     the mode to determine the behavior of the listing ("edit", "search", "remove")
     * @param products the list of products to be listed
     */
    private void listProducts(String mode, ArrayList<Product> products) {
        tfCleanup();
        if (!products.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(border).append("\n");
            switch (mode) {
                case "edit" -> stringBuilder.append("""
                                                             Choose which product you want to edit
                                                            """);
                case "search" -> stringBuilder.append("""
                                                               Select a product from the search results
                                                              """);
                case "remove" -> stringBuilder.append("""
                                                               Choose the product you want to remove
                                                              """);
            }
            stringBuilder.append(border).append("\n");

            int i = 0;
            for (Product p : products) {
                i++;
                stringBuilder.append(String.format("""
                                                            %d) %s
                                                           """, i, p.toString()));
            }
            stringBuilder.append(String.format("""
                                                       %1$s
                                                        0) Return to main menu
                                                       """, border));
            if (mode.equals("search")) {
                stringBuilder.append(" s) Search for something else\n");
                tfInputFormat(String.format("^[0-%ds]{1,%d}$", (i < 10) ? i : 9, String.valueOf(i).length()),
                              "Enter a number from the menu or 's' to search again.");
            } else {
                tfInputFormat(String.format("^[0-%d]{1,%d}$", (i < 10) ? i : 9, String.valueOf(i).length()),
                              "Enter a number from the menu.");
            }
            stringBuilder.append(border);
            animateText(stringBuilder.toString());

            int finalI = i;
            tfInput.setOnAction(e -> {
                String in = tfInput.getText();
                if (in.equals("0")) {
                    mainMenu();
                } else if (in.equals("s") && mode.equals("search")) {
                    searchProduct();
                } else if (!in.isEmpty() && Integer.parseInt(in) <= finalI) {
                    switch (mode) {
                        case "edit" -> editProduct(products.get(Integer.parseInt(in) - 1));
                        case "search" -> searchSelection(products.get(Integer.parseInt(in) - 1));
                        case "remove" -> removeProduct(products.get(Integer.parseInt(in) - 1));
                    }
                } else tfInput.clear();

            });
        } else {
            animateText(String.format("""
                                              %1$s
                                               There are no products to list
                                              %1$s
                                               Press Enter to go back to the main menu
                                              %1$s""", border));
            tfInputFormat("", "Press Enter");
            tfInput.setOnAction(e -> mainMenu());
        }
    }

    /**
     * Edits the properties of a given product. Allows the user to select a property to edit based on the product type.
     *
     * @param product The product to be edited.
     */
    private void editProduct(Product product) {
        tfCleanup();
        StringBuilder propMenu = new StringBuilder();
        propMenu.append(String.format("""
                                              %1$s
                                               Choose which property to edit
                                              %1$s
                                               %2$s
                                              %1$s
                                               1) Brand
                                               2) Model name
                                               3) Price
                                               """, border, product));
        switch (product) {
            case Keyboard ignored -> propMenu.append("""
                                                              4) Bluetooth
                                                             """);
            case Monitor ignored -> propMenu.append("""
                                                             4) Refresh rate
                                                             5) Resolution
                                                            """);
            case Motherboard ignored -> propMenu.append("""
                                                                 4) Chipset
                                                                """);
            case Mouse ignored -> propMenu.append("""
                                                           4) Wireless
                                                          """);
        }
        propMenu.append(String.format("""
                                               0) Return to main menu
                                               s) Select a different product to edit
                                              %1$s""", border));
        animateText(propMenu.toString());
        tfInputFormat(String.format("^[0-%ds]$", product.fieldCount()), "Enter a number from the menu.");
        tfInput.setOnAction(e -> {
            String in = tfInput.getText();
            if (!in.isEmpty()) {
                switch (in) {
                    case "0" -> mainMenu();
                    case "s" -> listProducts("edit", productList);
                    default -> editProperty(product, Integer.parseInt(in), false);
                }
            }
        });
    }

    /**
     * Edits the properties of a product based on the given property index.
     * For new products this method calls newProduct() until all properties have been set.
     *
     * @param product      The product whose property needs to be edited.
     * @param property     The index of the property to be edited.
     * @param isNewProduct Specifies whether the product is new or existing.
     */
    private void editProperty(Product product, Integer property, boolean isNewProduct) {
        tfCleanup();
        switch (property) {
            case 1 -> {
                animateText(String.format("""
                                                  %1$s
                                                   What is the name of the brand? %2$s
                                                  %1$s""", border,
                                          isNewProduct ? "" : String.format("%n Currently set to: '%s'",
                                                                            product.getBrand())));
                tfInputFormat(".*", "Enter the brand name.");
                tfInput.setOnAction(e -> {
                    String in = tfInput.getText();
                    if (!in.isEmpty()) {
                        product.setBrand(in);
                        if (isNewProduct) {
                            newProduct(product, property + 1);
                        } else {
                            editProductReturn(product, String.format("Brand name was set to '%s'", product.getBrand()));
                        }
                    }
                });
            }
            case 2 -> {
                animateText(String.format("""
                                                  %1$s
                                                   What is the name of the model? %2$s
                                                  %1$s""", border,
                                          isNewProduct ? "" : String.format("%n Currently set to: '%s'",
                                                                            product.getModel())));
                tfInputFormat(".*", "Enter the model name.");
                tfInput.setOnAction(e -> {
                    String in = tfInput.getText();
                    if (!in.isEmpty()) {
                        product.setModel(in);
                        if (isNewProduct) {
                            newProduct(product, property + 1);
                        } else {
                            editProductReturn(product, String.format("Model name was set to '%s'", product.getModel()));
                        }
                    }
                });
            }
            case 3 -> {
                animateText(String.format("""
                                                  %1$s
                                                   What is the price of the product? %2$s
                                                  %1$s""", border,
                                          isNewProduct ? "" : String.format("%n Currently set to: '%s€'",
                                                                            product.getPrice())));
                // only positive int/double in the text field
                tfInputFormat("^\\d+[,.]?\\d*$", "Enter a number.");
                tfInput.setOnAction(actionEvent -> {
                    String in = tfInput.getText();
                    if (!in.isEmpty() && in.matches("^\\d+([.,]\\d+)?$")) {
                        product.setPrice(Double.parseDouble(in.replace(',', '.')));
                        if (isNewProduct) {
                            newProduct(product, property + 1);
                        } else {
                            editProductReturn(product, String.format("Price was set to '%.2f€'", product.getPrice()));
                        }
                    }
                });
            }
            case Integer i when i > 3 -> {
                switch (product) {
                    case Keyboard p -> {
                        animateText(String.format("""
                                                          %1$s
                                                           Does the Keyboard have bluetooth connectivity? %2$s
                                                          %1$s""", border,
                                                  isNewProduct ? "" : String.format("%n Currently set to: '%s'",
                                                                                    p.getHasBluetooth() ? "yes" : "no")));
                        // allow only 'y' or 'n' as input
                        tfInputFormat("^[yYnN]$", "Enter 'y' or 'n'.");
                        tfInput.setOnAction(e -> {
                            String in = tfInput.getText();
                            if (!in.isEmpty()) {
                                p.setHasBluetooth(in.equalsIgnoreCase("y"));
                                if (isNewProduct) {
                                    newProduct(product, property + 1);
                                } else {
                                    editProductReturn(p, String.format("Bluetooth was set to '%s'",
                                                                       (p.getHasBluetooth() ? "yes" : "no")));
                                }
                            }
                        });
                    }
                    case Monitor p -> {
                        switch (property) {
                            case 4 -> {
                                animateText(String.format("""
                                                                  %1$s
                                                                   What is the refresh rate of the monitor? %2$s
                                                                  %1$s""", border, isNewProduct ? "" : String.format(
                                        "%n Currently set to: '%sHz'", p.getRefreshRate())));
                                // allow only positive integers as input
                                tfInputFormat("^\\d+", "Enter a number.");
                                tfInput.setOnAction(e -> {
                                    String in = tfInput.getText();
                                    if (!in.isEmpty()) {
                                        p.setRefreshRate(Integer.parseInt(in));
                                        if (isNewProduct) {
                                            newProduct(product, property + 1);
                                        } else {
                                            editProductReturn(p, String.format("Refresh rate was set to '%dHz'",
                                                                               p.getRefreshRate()));
                                        }
                                    }
                                });
                            }
                            case 5 -> {
                                animateText(String.format("""
                                                                  %1$s
                                                                   What is the resolution of the monitor? %2$s
                                                                  %1$s""", border,
                                                          isNewProduct ? "" : String.format("%n Currently set to: '%s'",
                                                                                            p.getResolution())));
                                // allow resolution only in the correct format
                                tfInputFormat("^\\d+(x\\d*)?$", "Enter the resolution as <horizontal>x<vertical>.");
                                tfInput.setOnAction(e -> {
                                    String in = tfInput.getText();
                                    if (!in.isEmpty() && in.matches("^\\d+x\\d+$")) {
                                        p.setResolution(in);
                                        if (isNewProduct) {
                                            newProduct(product, property + 1);
                                        } else {
                                            editProductReturn(p, String.format("Resolution was set to: '%s'",
                                                                               p.getResolution()));
                                        }
                                    }
                                });
                            }
                        }
                    }
                    case Motherboard p -> {
                        animateText(String.format("""
                                                          %1$s
                                                           What chipset does the Motherboard have? %2$s
                                                          %1$s""", border,
                                                  isNewProduct ? "" : String.format("%n Currently set to: '%s'",
                                                                                    p.getChipset())));
                        // allows any String
                        tfInputFormat(".*", "Enter the chipset.");
                        tfInput.setOnAction(e -> {
                            String in = tfInput.getText();
                            if (!in.isEmpty()) {
                                p.setChipset(in);
                                if (isNewProduct) {
                                    newProduct(product, property + 1);
                                } else {
                                    editProductReturn(p, String.format("Chipset was set to: '%s'", p.getChipset()));
                                }
                            }
                        });
                    }
                    case Mouse p -> {
                        animateText(String.format("""
                                                          %1$s
                                                           Is the Mouse wireless? %2$s
                                                          %1$s""", border,
                                                  isNewProduct ? "" : String.format("%n Currently set to: '%s'",
                                                                                    p.getIsWireless() ? "yes" : "no")));
                        // allow only 'y' or 'n' as input
                        tfInputFormat("^[yYnN]$", "Enter 'y' for yes or 'n' for no.");
                        tfInput.setOnAction(e -> {
                            String in = tfInput.getText();
                            if (!in.isEmpty()) {
                                p.setIsWireless(in.equalsIgnoreCase("y"));
                                if (isNewProduct) {
                                    newProduct(product, property + 1);
                                } else {
                                    editProductReturn(p, String.format("Wireless was set to '%s'",
                                                                       p.getIsWireless() ? "yes" : "no"));
                                }
                            }
                        });
                    }
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + property);
        }
    }

    /**
     * Prompts the user what to do next after editing a product's property
     * Allows the user to select a different property or product to edit or to return to the main menu.
     *
     * @param product The product to be edited.
     * @param str     The string containing what was edited before.
     */
    private void editProductReturn(Product product, String str) {
        tfCleanup();
        animateText(String.format("""
                                          %1$s
                                           %2$s
                                          %1$s
                                           %3$s
                                          %1$s
                                           1) Edit another property
                                           2) Select a different product to edit
                                           0) Return to main menu
                                          %1$s""", border, str, product));
        tfInputFormat("^[0-2]$", "Enter a number from the menu.");
        tfInput.setOnAction(e -> {
            String in = tfInput.getText();
            if (!in.isEmpty()) {
                switch (in) {
                    case "0" -> mainMenu();
                    case "1" -> editProduct(product);
                    case "2" -> listProducts("edit", productList);
                }
            }
        });
    }

    /**
     * Prompts the user to enter a search query and hands the input to listSearchResults().
     */
    private void searchProduct() {
        tfCleanup();
        animateText(String.format("""
                                          %1$s
                                           What do you want to search for?
                                          %1$s
                                           0) Return to main menu
                                          %1$s
                                          """, border));
        tfInputFormat(".*", "Enter what to search for.");
        tfInput.setOnAction(e -> {
            String in = tfInput.getText().toLowerCase();
            if (in.equals("0")) {
                mainMenu();
            } else if (!in.isEmpty()) {
                listSearchResults(in);
            }
        });
    }

    /**
     * Creates a filtered list of products that match the given search string.
     * Every product field is checked for matches.
     * Product types match as well.
     * Additionally enables to match for wireless/bluetooth when associated field is true.
     *
     * @param str The string to search for.
     */
    private void listSearchResults(String str) {
        ArrayList<Product> results;
        results = new ArrayList<>(productList.stream().filter(product -> {
            boolean containsStr = product.getBrand().toLowerCase().contains(str)
                                  || product.getModel()
                                            .toLowerCase()
                                            .contains(str)
                                  || String.valueOf(product.getPrice()).contains(str);
            if (containsStr) return true;
            switch (product) {
                case Keyboard p -> {
                    return str.contains("keyboard")
                           || (p.getHasBluetooth() ? "yes" : "no").contains(str)
                           || (str.equalsIgnoreCase("bluetooth") && p.getHasBluetooth());
                }
                case Monitor p -> {
                    return str.contains("monitor")
                           || String.valueOf(p.getRefreshRate()).contains(str)
                           || p.getResolution().toLowerCase().contains(str);
                }
                case Motherboard p -> {
                    return str.contains("motherboard") || p.getChipset().toLowerCase().contains(str);
                }
                case Mouse p -> {
                    return str.contains("mouse")
                           || (p.getIsWireless() ? "yes" : "no").contains(str)
                           || (str.equalsIgnoreCase("wireless") && p.getIsWireless());
                }
            }
        }).toList());
        listProducts("search", results);
    }

    /**
     * Prompts the user to choose what to do with the selected product.
     * Offers options to edit, remove, search or return to the main menu.
     *
     * @param product The product to be processed.
     */
    private void searchSelection(Product product) {
        tfCleanup();
        animateText(String.format("""
                                          %1$s
                                           Choose what to do with the product
                                          %1$s
                                           %2$s
                                          %1$s
                                           1) Edit product
                                           2) Remove product
                                           3) Search for something else
                                           0) Return to main menu
                                          %1$s""", border, product));
        tfInputFormat("^[0-3]$", "Enter a number from the menu.");
        tfInput.setOnAction(e -> {
            String in = tfInput.getText();
            if (!in.isEmpty()) {
                switch (in) {
                    case "0" -> mainMenu();
                    case "1" -> editProduct(product);
                    case "2" -> removeProduct(product);
                    case "3" -> searchProduct();
                }
            }
        });
    }

    /**
     * Asks user for product removal confirmation
     *
     * @param product The product to be removed.
     */
    private void removeProduct(Product product) {
        tfCleanup();
        animateText(String.format("""
                                          %1$s
                                           Are you sure you want to remove this product?
                                          %1$s
                                           %2$s
                                          %1$s
                                           1) Confirm removal
                                           2) Select a different product to remove
                                           0) Return to main menu
                                          %1$s""", border, product));
        tfInputFormat("^[0-2]$", "Enter a number from the menu.");
        tfInput.setOnAction(e -> {
            String in = tfInput.getText();
            if (!in.isEmpty()) {
                switch (in) {
                    case "0" -> mainMenu();
                    case "1" -> {
                        productList.remove(product);
                        mainMenu();
                    }
                    case "2" -> listProducts("remove", productList);
                }
            }
        });
    }

    /**
     * Closes the shop after asking the user for confirmation.
     * Otherwise they're returned to the main menu
     */
    private void closeShop() {
        tfCleanup();
        animateText(String.format("""
                                          %1$s
                                           Do you really want to exit the shop?
                                          %1$s""", border));
        tfInputFormat("^[yYnN]$", "Enter 'y' or 'n'.");
        tfInput.setOnAction(e -> {
            String in = tfInput.getText();
            if (!in.isEmpty()) {
                switch (in) {
                    case "y", "Y" -> {
                        System.out.println("Closing shop.");
                        Platform.exit();
                    }
                    case "n", "N" -> mainMenu();
                }
            }
        });
    }

    /**
     * Sets the prompt text and text formatter of the input field.
     * The text formatter ensures that the input matches the specified regular expression.
     *
     * @param regex  The regular expression pattern that the input must match.
     * @param prompt The prompt text to be displayed in the input field.
     */
    private void tfInputFormat(String regex, String prompt) {
        tfInput.setPromptText(prompt);
        tfInput.setTextFormatter(new TextFormatter<>(i -> {
            if (i.getControlNewText().matches(regex) || i.getControlNewText().isEmpty()) {
                return i;
            } else {
                return null;
            }
        }));
    }

    /**
     * Animates the text by displaying it character by character in a specified speed.
     * Any running animation will be stopped before starting a new one.
     *
     * @param str The text to be animated.
     */
    private void animateText(String str) {
        if (timeline != null) {
            timeline.stop();
        }
        int iMax = str.length();
        final IntegerProperty i = new SimpleIntegerProperty(0);
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(TEXT_SPEED), event -> {
            if (i.get() > iMax) {
                timeline.stop();
            } else {
                output.setText(str.substring(0, i.get()));
                i.set(i.get() + 1);
                // scrolls down with the text
                scrollPane.setVvalue(1.0);
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Clears the text input field and removes the event handler for the "onAction" event.
     */
    private void tfCleanup() {
        tfInput.clear();
        tfInput.setOnAction(null);
    }
}