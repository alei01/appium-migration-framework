package appmanagement;

import cfg.MobileConfig;
import driver.DriverProvider;
import io.appium.java_client.InteractsWithApps;
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
       getInteractionWithApps().terminateApp(config.getAppPackage());
    }

    public static InteractsWithApps getInteractionWithApps() {
        return is(ANDROID)
                ? (AndroidDriver) DriverProvider.get().getDriver()
                : (IOSDriver) DriverProvider.get().getDriver();
    }

    /**
     * Removes the app which was provided in the capabilities at session creation from the device (uninstall).
     */
    public static void removeApp() {
        LOG.debug("Removing app with bundleId: {}", config.getAppPackage());
        getInteractionWithApps().removeApp(config.getAppPackage());
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
        getInteractionWithApps().installApp(appPath);
    }

    public static void startAndroidActivity(Activity activity) {
        ((AndroidDriver) getInteractionWithApps()).startActivity(activity);
    }

    /**
     * Terminating the app with safely process killing.
     * On Android, to be killed, the process should be in the background, otherwise, with the opened app, the process will restart.
     */
    public static void terminateApp() {
        LOG.info("Going to terminate app: {}", config.getAppPackage());
        getInteractionWithApps().terminateApp(config.getAppPackage());
    }

    public static void killApp() {
        LOG.info("Going to kill app: {}", config.getAppPackage());
/*        ANDROID.run(() -> {
            ((AndroidDriver) getInteractionWithApps()).pressKey(new KeyEvent().withKey(APP_SWITCH));
            ((AndroidDriver) getInteractionWithApps()).findElementByAndroidUIAutomator(
                    "resourceIdMatches(\".*com.sec.android.app.launcher:id/clear_all.*\")").click();
        });*/
        IOS.run(() -> getInteractionWithApps().terminateApp(config.getAppPackage()));
    }

    /**
     * Activates the given app if it installed, but not running or if it is running in the
     * background.*
     */
    public static void activateApp() {
        getInteractionWithApps().activateApp(config.getAppPackage());
    }

    /**
     * Terminate the Settings application if it is running.
     */
    public void closePhoneSettingsIOS() {
        IOS.run(() -> getInteractionWithApps().terminateApp("com.apple.Preferences"));
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
            ((IOSDriver) getInteractionWithApps()).lockDevice();
        } else {
            ((AndroidDriver) getInteractionWithApps()).lockDevice();
        }
    }

    public void unlockDevice() {
        if (is(IOS)) {
            ((IOSDriver) getInteractionWithApps()).unlockDevice();
        } else {
            ((AndroidDriver) getInteractionWithApps()).unlockDevice();
        }
    }

    public void runAppInBackground(Duration duration) {
        LOG.info("Running app in background for {}", duration);
        getInteractionWithApps().runAppInBackground(duration);
        LOG.info("App is back from background");
    }
}
