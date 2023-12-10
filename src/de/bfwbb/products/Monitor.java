package de.bfwbb.products;

/**
 * The Monitor class represents a monitor product.
 * It extends the Product class and adds attributes to store the refresh rate and resolution of the monitor.
 *
 * @author NullArgumentException
 */
public non-sealed class Monitor extends Product {
    private int refreshRate;
    private String resolution;

    public Monitor() {
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    /**
     * Returns a string representation of the Monitor object. The string representation
     * includes the brand, model, and price from the {@link Product} superclass, as well
     * as the refresh rate and resolution attributes of the Monitor.
     *
     * @return The string representation of the Monitor object.
     */
    @Override
    public String toString() {
        return String.format("Monitor%n    %s%n    [Refresh rate]: %dHz%n    [Resolution]: %s", super.toString(), refreshRate, resolution);
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
