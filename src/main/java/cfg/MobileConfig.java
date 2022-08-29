package cfg;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigCache;

@Config.LoadPolicy(Config.LoadType.MERGE)
public interface MobileConfig extends Config {

    static MobileConfig getMobileConfig() {
        return ConfigCache.getOrCreate(MobileConfig.class);
    }

    @Key("APP_PACKAGE")
    @DefaultValue("io.appium.android.apis")
    String getAppPackage();

    @Key("APPIUM_SERVER_PORT")
    @DefaultValue("4373")
    Integer appiumServerPort();

    @Key("APP_PATH")
    @DefaultValue("app-debug.apk")
    String getAppPath();

    @Key("PLATFORM")
    @DefaultValue("ANDROID")
    String getPlatform();

    @Key("UDID")
    @DefaultValue("ce11182ba204e23303")
    String getUdid();

    @Key("AUTOMATION_NAME")
    @DefaultValue("UiAutomator2")
    String getAutomationName();

    @Key("APP_ACTIVITY")
    @DefaultValue("io.appium.android.apis.view.TextFields")
    String getAppActivity();
}

