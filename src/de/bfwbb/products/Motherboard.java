package de.bfwbb.products;

/**
 * The Motherboard class represents a motherboard product.
 * It extends the Product class and adds an attribute to store the chipset.
 *
 * @author nargex
 * @see <a href="https://github.com/NullArgumentException/PCShop">GitHub page</a>
 */
public non-sealed class Motherboard extends Product {
    private String chipset;

    /**
     * Retrieves the chipset information of the motherboard.
     *
     * @return The chipset information as a String.
     */
    public String getChipset() {
        return chipset;
    }

    /**
     * Sets the chipset information of the motherboard.
     *
     * @param chipset The new chipset information to be set as a String.
     */
    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    /**
     * Returns a string representation of the Motherboard object. The string representation
     * includes the brand, model, and price from the {@link Product} superclass, as well
     * as the chipset attribute of the Motherboard.
     *
     * @return The string representation of the Motherboard object.
     */
    @Override
    public String toString() {
        return String.format("Motherboard%n    %s%n    [Chipset]: %s", super.toString(), chipset);
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
