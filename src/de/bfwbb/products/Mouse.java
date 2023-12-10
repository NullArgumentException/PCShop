package de.bfwbb.products;

/**
 * The Mouse class represents a mouse product.
 * It extends the Product class and adds an attribute to indicate whether it is wireless or not.
 *
 * @author NullArgumentException
 */
public non-sealed class Mouse extends Product {
    private boolean isWireless;

    public Mouse() {
    }

    public boolean getIsWireless() {
        return isWireless;
    }

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
     * Returns the total number of fields in the current class and its superclass.
     *
     * @return The number of fields.
     */
    @Override
    public int fieldCount() {
        return super.fieldCount() + getClass().getDeclaredFields().length;
    }
}
