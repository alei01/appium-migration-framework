package driver;

import cfg.MobileConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

import static engine.AppPlatform.isAndroid;

public class DriverBuilder {
    private static final MobileConfig config = MobileConfig.getMobileConfig();

    public DriverBuilder() {
    }

    private static UiAutomator2Options buildUiAutomator2Options(){
        return new UiAutomator2Options()
                .setApp(config.getAppPath())
                .setDeviceName("Galaxy S8")
                .setNewCommandTimeout(Duration.ofMillis(60*60))
                .setClearSystemFiles(false)
                .setFullReset(true)
                .setNoReset(false)
                .setOrientation(ScreenOrientation.PORTRAIT)
                .setUdid(config.getUdid())
                .setPlatformVersion("11")
                .setAutomationName("UiAutomator2");
    }

    private static XCUITestOptions buildXCUITestOptions(){
        return new XCUITestOptions()
                .setApp(config.getAppPath())
                .setDeviceName("Galaxy S8")
                .setNewCommandTimeout(Duration.ofMillis(60*60))
                .setClearSystemFiles(false)
                .setFullReset(true)
                .setNoReset(false)
                .setOrientation(ScreenOrientation.PORTRAIT)
                .setUdid(config.getUdid())
                .setPlatformVersion("11")
                .setAutomationName("XCUITest");
    }

    public static AppiumDriver init() {
        AppiumDriver driver = isAndroid() ? initUiAutomatorDriver() : initXcuiDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(600));
        return driver;
    }

    private static AndroidDriver initUiAutomatorDriver() {
        AppiumDriverLocalService service = getAppiumDriverLocalService();
        return new AndroidDriver(service, buildUiAutomator2Options());
    }

    private static IOSDriver initXcuiDriver() {
        AppiumDriverLocalService service = getAppiumDriverLocalService();
        return new IOSDriver(service, buildXCUITestOptions());
    }

    private static AppiumDriverLocalService getAppiumDriverLocalService() {
        AppiumDriverLocalService service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                .usingAnyFreePort()
                .build();
        service.start();
        return service;
    }
}
