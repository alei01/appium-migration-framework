package driver;

import io.appium.java_client.AppiumDriver;

public class DriverProvider {
    private static final ThreadLocal<DriverProvider> INSTANCE = ThreadLocal.withInitial(DriverProvider::new);
    AppiumDriver driver = DriverBuilder.init();

    public static DriverProvider get() {
        return INSTANCE.get();
    }

    public AppiumDriver getDriver() {
        return driver;
    }
}
