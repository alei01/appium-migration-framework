package engine;

import cfg.MobileConfig;
import driver.DriverProvider;

import java.util.Arrays;
import java.util.function.Supplier;

public enum AppPlatform implements Supplier<AppPlatform> {
    IOS, ANDROID, UNDEFINED;

    public static AppPlatform from(final String val) {
        return Arrays.stream(values()).filter(p -> p.name().equalsIgnoreCase(val)).findFirst()
                .orElseGet(UNDEFINED);
    }

    public static AppPlatform getCurrentPlatform() {
        return AppPlatform.from(MobileConfig.getMobileConfig().getPlatform());
    }

    public static boolean isAndroid() {
        return is(ANDROID);
    }

    public static boolean isIos() {
        return is(IOS);
    }

    public static boolean is(AppPlatform platform) {
        return getCurrentPlatform().equals(platform);
    }

    public void run(final Runnable runnable) {
        if (this.equals(getCurrentPlatform())) {
            runnable.run();
        }
    }

    @Override
    public AppPlatform get() {
        return this;
    }
}

