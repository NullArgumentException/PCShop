package de.bfwbb.products;

/**
 * The Motherboard class represents a motherboard product.
 * It extends the Product class and adds an attribute to store the chipset.
 *
 * @author NullArgumentException
 */
public non-sealed class Motherboard extends Product {
    private String chipset;

    public Motherboard() {
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    @Override
    public String toString() {
        return String.format("Motherboard%n    %s%n    [Chipset]: %s", super.toString(), chipset);
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
