package de.bfwbb.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The Product class represents a generic product.
 * It contains the brand, model, and price of the product.
 *
 * @author NullArgumentException
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

    public Product() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
     * Returns the total number of fields in the current class.
     *
     * @return The number of fields.
     */
    public int fieldCount() {
        return Product.class.getDeclaredFields().length;
    }
}
