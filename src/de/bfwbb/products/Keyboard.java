package de.bfwbb.products;

/**
 * The Keyboard class represents a keyboard product.
 * It extends the Product class and adds an attribute to indicate whether it has Bluetooth.
 *
 * @author nargex
 * @see <a href="https://github.com/NullArgumentException/PCShop">GitHub page</a>
 */
public non-sealed class Keyboard extends Product {
    private boolean hasBluetooth;

    /**
     * Retrieves the Bluetooth ability of the keyboard.
     *
     * @return {@code true} if the keyboard has Bluetooth connectivity, {@code false} otherwise.
     */
    public boolean getHasBluetooth() {
        return hasBluetooth;
    }

    /**
     * Sets the Bluetooth connectivity status of the keyboard.
     *
     * @param hasBluetooth {@code true} for Bluetooth ability 'yes', {@code false} for Bluetooth ability 'no'.
     */
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
        return String.format("Keyboard%n    %s%n    [Bluetooth]: %s",
                             super.toString(),
                             ((hasBluetooth) ? "yes" : "no"));
    }

    /**
     * Retrieves the total count of fields in the current class, including fields inherited
     * from its superclass.
     *
     * @return The number of fields.
     */
    @Override
    public int fieldCount() {
        return super.fieldCount() + getClass().getDeclaredFields().length;
    }
}
