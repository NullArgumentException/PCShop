package de.bfwbb.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The Product class represents a generic product.
 * It contains the brand, model, and price of the product.
 * Only Keyboard, Monitor, Motherboard and Mouse classes are permitted to extend Product.
 *
 * @author nargex
 * @see <a href="https://github.com/NullArgumentException/PCShop">GitHub page</a>
 */

// adds a class attribute to the json objects to let the object mapper know which class each object belongs to
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Keyboard.class, name = "keyboard"),
        @JsonSubTypes.Type(value = Monitor.class, name = "monitor"),
        @JsonSubTypes.Type(value = Motherboard.class, name = "motherboard"),
        @JsonSubTypes.Type(value = Mouse.class, name = "mouse")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public sealed abstract class Product
        permits Keyboard, Monitor, Motherboard, Mouse {
    private String brand;
    private String model;
    private double price;

    /**
     * Retrieves the brand of the product.
     *
     * @return The brand of the product as a String.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the product.
     *
     * @param brand The new brand information to be set as a String.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Retrieves the model of the product.
     *
     * @return The model of the product as a String.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the product.
     *
     * @param model The new model information to be set as a String.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Retrieves the price of the product.
     *
     * @return The price of the product as a double value.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price The new price to be set for the product as a double value.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the current object. The string representation
     * contains the brand, model, and price of the object.
     *
     * @return The string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("[Brand]: %s%n    [Model]: %s%n    [Price]: %.2f€", brand, model, price);
    }

    /**
     * Retrieves the count of fields declared in the {@code Product} class.
     *
     * @return The count of fields declared in the {@code Product} class.
     */
    public int fieldCount() {
        return Product.class.getDeclaredFields().length;
    }
}
