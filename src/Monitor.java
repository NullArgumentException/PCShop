import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author NullArgumentException
 */
public non-sealed class Monitor extends Product {
    private int refreshRate;
    private String resolution;

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

    protected void editProperty(int property) {
        if (property <= super.getFieldCount()) super.editProperty(property);
        else {
            switch (property) {
                case 4 -> {
                    Scanner scan = new Scanner(System.in);
                    boolean retry;
                    do {
                        retry = false;
                        System.out.println("Enter the Monitor's refresh rate: ");
                        try {
                            setRefreshRate(scan.nextInt());
                        } catch (InputMismatchException e) {
                            System.err.println("Wrong input, please enter a whole number.");
                            retry = true;
                            ShopCtrl.wait(500);
                        }
                    } while (retry);
                    scan.close();
                    System.out.println("Refresh rate was set to: " + getRefreshRate());
                    ShopCtrl.wait(500);
                }
                case 5 -> {
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Enter the Monitor's resolution: ");
                    setResolution(scan.nextLine());
                    System.out.println("Resolution was set to: " + getResolution());
                    ShopCtrl.wait(500);
                    scan.close();
                }
            }
        }
    }

    public int getFieldCount() {
        return super.getFieldCount() + getClass().getDeclaredFields().length;
    }
}
