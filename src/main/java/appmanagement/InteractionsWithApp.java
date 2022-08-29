package appmanagement;

import cfg.MobileConfig;
import driver.DriverProvider;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.stream.Stream;

import static engine.AppPlatform.ANDROID;
import static engine.AppPlatform.IOS;
import static engine.AppPlatform.is;
import static io.appium.java_client.android.nativekey.AndroidKey.APP_SWITCH;
import static org.slf4j.LoggerFactory.getLogger;

public class InteractionsWithApp {
    private static final Logger LOG = getLogger(InteractionsWithApp.class);
    private static final MobileConfig config = MobileConfig.getMobileConfig();

    /**
     * iOS - Terminates the particular application if it is running.
     * Android - Minimizes app vis home button click.
     */
    public void minimizeApp() {
        LOG.info("Going to minimize app: {}", config.getAppPackage());
        DriverProvider.get().getDriver().terminateApp(config.getAppPackage());
    }

    /**
     * Removes the app which was provided in the capabilities at session creation from the device (uninstall).
     */
    public static void removeApp() {
        LOG.debug("Removing app with bundleId: {}", config.getAppPackage());
        DriverProvider.get().getDriver().removeApp(config.getAppPackage());
    }

    /**
     * Installs an app on the mobile device and run it.
     */
    public static void installAndLaunchApp() {
        LOG.debug("Installing app and launch it with bundleId: {}", config.getAppPackage());
        installApp(config.getAppPath());
        activateApp();
    }

    public static void installApp(String appPath) {
        DriverProvider.get().getDriver().installApp(appPath);
    }

    public static void startAndroidActivity(Activity activity) {
        ((AndroidDriver) DriverProvider.get().getDriver()).startActivity(activity);
    }

    /**
     * Terminating the app with safely process killing.
     * On Android, to be killed, the process should be in the background, otherwise, with the opened app, the process will restart.
     */
    public static void terminateApp() {
        LOG.info("Going to terminate app: {}", config.getAppPackage());
        DriverProvider.get().getDriver().terminateApp(config.getAppPackage());
    }

    public static void killApp() {
        LOG.info("Going to kill app: {}", config.getAppPackage());
        ANDROID.run(() -> {
            ((AndroidDriver) DriverProvider.get().getDriver()).pressKey(new KeyEvent().withKey(APP_SWITCH));
            ((AndroidDriver) DriverProvider.get().getDriver()).findElementByAndroidUIAutomator(
                    "resourceIdMatches(\".*com.sec.android.app.launcher:id/clear_all.*\")").click();
        });
        IOS.run(() -> DriverProvider.get().getDriver().terminateApp(config.getAppPackage()));
    }

    /**
     * Activates the given app if it installed, but not running or if it is running in the
     * background.*
     */
    public static void activateApp() {
        DriverProvider.get().getDriver().activateApp(config.getAppPackage());
    }

    /**
     * Terminate the Settings application if it is running.
     */
    public void closePhoneSettingsIOS() {
        IOS.run(() -> DriverProvider.get().getDriver().terminateApp("com.apple.Preferences"));
    }

    /**
     * Relaunches the application.
     */
    public static void relaunchApp() {
        terminateApp();
        activateApp();
    }

    /**
     * Relaunches the application and runs code after.
     *
     * @param codeToRunAfterBase a code to run after application's relaunch.
     */
    public void relaunchApp(final Runnable... codeToRunAfterBase) {
        relaunchApp();
        Stream.of(codeToRunAfterBase).forEachOrdered(Runnable::run);
    }

    public void lockDevice() {
        if (is(IOS)) {
            ((IOSDriver) DriverProvider.get().getDriver()).lockDevice();
        } else {
            ((AndroidDriver) DriverProvider.get().getDriver()).lockDevice();
        }
    }

    public void unlockDevice() {
        if (is(IOS)) {
            ((IOSDriver) DriverProvider.get().getDriver()).unlockDevice();
        } else {
            ((AndroidDriver) DriverProvider.get().getDriver()).unlockDevice();
        }
    }

    public void runAppInBackground(Duration duration) {
        LOG.info("Running app in background for {}", duration);
        DriverProvider.get().getDriver().runAppInBackground(duration);
        LOG.info("App is back from background");
    }
}
