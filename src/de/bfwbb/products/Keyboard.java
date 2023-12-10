package de.bfwbb.products;

/**
 * The Keyboard class represents a keyboard product.
 * It extends the Product class and adds an attribute to indicate whether it has Bluetooth.
 *
 * @author NullArgumentException
 */
public non-sealed class Keyboard extends Product {
    private boolean hasBluetooth;

    public boolean getHasBluetooth() {
        return hasBluetooth;
    }

    public void setHasBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
    }

    /**
     * Returns a string representation of the Keyboard object. The string representation
     * includes the brand, model, and price from the {@link Product} superclass, as well
     * as the Bluetooth attribute of the Keyboard.
     *
     * @return The string representation of the Keyboard object.
     */
    @Override
    public String toString() {
        return String.format("Keyboard%n    %s%n    [Bluetooth]: %s", super.toString(), ((hasBluetooth) ? "yes" : "no"));
    }

    /**
     * Returns the total number of fields in the current class and its superclass.
     *
     * @return The number of fields.
     */
    @Override
    public int fieldCount() {
        return super.fieldCount() + getClass().getDeclaredFields().length;
    }
}