package driver;

import cfg.MobileConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

import static engine.AppPlatform.isAndroid;
import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.CLEAR_SYSTEM_FILES;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.FULL_RESET;
import static io.appium.java_client.remote.MobileCapabilityType.NEW_COMMAND_TIMEOUT;
import static io.appium.java_client.remote.MobileCapabilityType.NO_RESET;
import static io.appium.java_client.remote.MobileCapabilityType.ORIENTATION;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;
import static io.appium.java_client.remote.MobileCapabilityType.UDID;

public class DriverBuilder {
    private static final MobileConfig config = MobileConfig.getMobileConfig();

    public DriverBuilder() {
    }

    public static DesiredCapabilities capabilitiesBuilder() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(APP, config.getAppPath());
        capabilities.setCapability(DEVICE_NAME, config.getPlatform());
        capabilities.setCapability(NEW_COMMAND_TIMEOUT, 60 * 60);
        capabilities.setCapability(CLEAR_SYSTEM_FILES, false);
        capabilities.setCapability(FULL_RESET, true);
        capabilities.setCapability(NO_RESET, false);
        capabilities.setCapability(ORIENTATION, "PORTRAIT");
        capabilities.setCapability(UDID, config.getUdid());
        capabilities.setCapability(PLATFORM_VERSION, "11");
        capabilities.setCapability(AUTOMATION_NAME, getAutomationName());

        //TODO: implement the platform specific logic

        return capabilities;
    }

    private static String getAutomationName() {
        return isAndroid() ? "UiAutomator2" : "XCUITest";
    }

    public static AppiumDriver init() {
        AppiumDriver driver = isAndroid() ? initUiAutomatorDriver() : initXcuiDriver();
        driver.manage().timeouts().implicitlyWait(1234123, TimeUnit.MILLISECONDS);
        return driver;
    }

    private static AndroidDriver initUiAutomatorDriver() {
        AppiumDriverLocalService service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort()
                .build();
        service.start();
        return new AndroidDriver(service, capabilitiesBuilder());
    }

    private static IOSDriver initXcuiDriver() {
        AppiumDriverLocalService service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort()
                .build();
        service.start();
        return new IOSDriver(service, capabilitiesBuilder());
    }
}
