package de.bfwbb.products;

/**
 * The Monitor class represents a monitor product.
 * It extends the Product class and adds attributes to store the refresh rate and resolution of the monitor.
 *
 * @author nargex
 * @see <a href="https://github.com/NullArgumentException/PCShop">GitHub page</a>
 */
public non-sealed class Monitor extends Product {
    private int refreshRate;
    private String resolution;

    /**
     * Retrieves the refresh rate of the monitor.
     *
     * @return The refresh rate of the monitor in Hertz (Hz).
     */
    public int getRefreshRate() {
        return refreshRate;
    }

    /**
     * Sets the refresh rate of the monitor.
     *
     * @param refreshRate The new refresh rate to be set for the monitor, measured in Hertz (Hz).
     */
    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    /**
     * Retrieves the resolution of the monitor.
     *
     * @return The display resolution as a String.
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution of the monitor.
     *
     * @param resolution The new display resolution to be set as a String.
     */
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
        return String.format("Monitor%n    %s%n    [Refresh rate]: %dHz%n    [Resolution]: %s",
                             super.toString(),
                             refreshRate,
                             resolution);
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
