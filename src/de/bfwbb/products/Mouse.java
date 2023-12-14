package de.bfwbb.products;

/**
 * The Mouse class represents a mouse product.
 * It extends the Product class and adds an attribute to indicate whether it is wireless or not.
 *
 * @author nargex
 * @see <a href="https://github.com/NullArgumentException/PCShop">GitHub page</a>
 */
public non-sealed class Mouse extends Product {
    private boolean isWireless;

    /**
     * Retrieves the wireless ability of the mouse.
     *
     * @return {@code true} if the mouse has wireless connectivity, {@code false} otherwise.
     */
    public boolean getIsWireless() {
        return isWireless;
    }

    /**
     * Sets the wireless ability of the mouse.
     *
     * @param wireless {@code true} if mouse is wireless or else {@code false}
     */
    public void setIsWireless(boolean wireless) {
        isWireless = wireless;
    }

    /**
     * Returns a string representation of the Mouse object. The string representation
     * includes the brand, model, and price from the {@link Product} superclass, as well
     * as the wireless attribute of the Mouse.
     *
     * @return The string representation of the Mouse object.
     */
    @Override
    public String toString() {
        return String.format("Mouse%n    %s%n    [Wireless]: %s", super.toString(), ((isWireless) ? "yes" : "no"));
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
