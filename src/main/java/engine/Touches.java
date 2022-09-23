package engine;

import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.function.Supplier;

import static io.appium.java_client.android.nativekey.AndroidKey.BACK;
import static io.appium.java_client.android.nativekey.AndroidKey.HOME;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

public class Touches {

    private Touches() {
    }

    public static void swipeLeft(RemoteWebElement element) {
        swipeLeft(element, ofMillis(50));
    }

    public static void swipeLeft(RemoteWebElement element, Duration duration) {
        Point center = getCenter(element);
        int windowWidth = getDriver().manage().window().getSize().getWidth();
        PointOption pressPoint = point((int) (windowWidth * 0.7), center.getY());
        PointOption moveToPoint = point((int) (windowWidth * 0.3), center.getY());
        swipe(pressPoint, moveToPoint, duration);
    }

    public static void swipeRight(RemoteWebElement element) {
        swipeRight(element, ofMillis(50));
    }

    public static void swipeRight(RemoteWebElement element, Duration duration) {
        Point center = getCenter(element);
        int windowWidth = getDriver().manage().window().getSize().getWidth();
        PointOption pressPoint = point((int) (windowWidth * 0.3), center.getY());
        PointOption moveToPoint = point((int) (windowWidth * 0.7), center.getY());
        swipe(pressPoint, moveToPoint, duration);
    }

    public static void swipeUp(WebElement element) {
        int height = element.getSize().getHeight();
        Point location = element.getLocation();
        PointOption pressPoint = point(location.getX(), location.getY() + 10);
        PointOption moveToPoint = point(location.getX(), (int) (location.getY() + height * 0.2 - 10));
        swipe(pressPoint, moveToPoint, ofSeconds(3));
    }

    public static void swipeDown(WebElement element, double percentageToIgnoreBottom) {
        int height = element.getSize().getHeight();
        Point location = element.getLocation();
        PointOption pressPoint = point(location.getX(), (int) (location.getY() + height - (height * percentageToIgnoreBottom)));
        PointOption moveToPoint = point(location.getX(), (int) (location.getY() + height * 0.2 + 10));
        swipe(pressPoint, moveToPoint, ofSeconds(3));
    }

    public static void swipeDown(final WebElement element) {
        swipeDown(element, 0.10);
    }

    public static RemoteWebElement swipeDownTo(RemoteWebElement baseToSwipe, Supplier<RemoteWebElement> elementSupplier) {
        return swipeDownTo(baseToSwipe, elementSupplier);
    }

    /**
     * @param baseToSwipe              swipe will be done from this element (by coordinates)
     * @param run                      action to be performed after every swipe
     * @param percentageToIgnoreBottom percent of element's heoght from which swipe will be performed.
     *                                 100% means swipe will be done from the top of the element,
     *                                 0% means swipe will be done from the bottom of the element
     * Useful to collect some data through a scrollable view. Runnable is executed on page-end condition check.
     */
    @Deprecated(since = "20.0.0")
    public static void swipeToPageBottomAndPerSwipeDo(RemoteWebElement baseToSwipe, Runnable run, double percentageToIgnoreBottom) {
        StringBuilder pageSourceIndicator = new StringBuilder();
        swipeDownUntil(baseToSwipe, getPageSourceNotChangedAfterRunnableSupplier(pageSourceIndicator, run), percentageToIgnoreBottom);
    }

    public static void swipeToPageBottomAndPerSwipeDo(RemoteWebElement baseToSwipe, Runnable run) {
        swipeToPageBottomAndPerSwipeDo(baseToSwipe, run, 0.1);
    }

    public static void swipeToPageTopAndPerSwipeDo(RemoteWebElement baseToSwipe, Runnable run) {
        StringBuilder pageSourceIndicator = new StringBuilder();
        swipeUpUntil(baseToSwipe, getPageSourceNotChangedAfterRunnableSupplier(pageSourceIndicator, run));
    }

    /**
     * Swiping to page bottom and return condition is met.
     *
     * @param baseToSwipe       swipe will be done from this element (by coordinates)
     * @param conditionSupplier swipe until this condition met
     * @return true if condition was met within limited amount of swipes;
     * false if condition wasn't met within limit or page bottom has been reached
     */
    @Deprecated(since = "20.0.0")
    public static boolean swipeToPageBottomUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean conditionMatched = conditionSupplier.get();
        for (int scroll = 0; scroll < 15 && !conditionMatched && !getPageSourceNotChangedSupplier(stringBuilder).get(); scroll++) {
            swipeDown(baseToSwipe);
            conditionMatched = conditionSupplier.get();
        }
        return conditionMatched;
    }

    /**
     * Swiping to page top until condition is met.
     *
     * @param baseToSwipe       swipe will be done from this element (by coordinates)
     * @param conditionSupplier swipe until this condition met
     * @return true if condition was met within limited amount of swipes;
     * false if condition wasn't met within limit or page top has been reached
     */
    public static boolean swipeToPageTopUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean conditionMatched = conditionSupplier.get();
        for (int scroll = 0; scroll < 15 && !conditionMatched && !getPageSourceNotChangedSupplier(stringBuilder).get(); scroll++) {
            swipeUp(baseToSwipe);
            conditionMatched = conditionSupplier.get();
        }
        return conditionMatched;
    }

    public static boolean swipeDownUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier, double percentageToIgnoreBottom) {
        return swipeDownUntil(baseToSwipe, conditionSupplier, percentageToIgnoreBottom, 15);
    }

    /**
     * Swiping down until condition is met no matter if page source is changed or not.
     *
     * @param baseToSwipe              swipe will be done from this element (by coordinates)
     * @param conditionSupplier        swipe until this condition met
     * @param percentageToIgnoreBottom percent of element's heoght from which swipe will be performed.
     *                                 100% means swipe will be done from the top of the element,
     *                                 0% means swipe will be done from the bottom of the element
     * @param maxSwipe                 limit amount of swipes
     * @return true if condition was met within limited amount of swipes
     */
    public static boolean swipeDownUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier, double percentageToIgnoreBottom, int maxSwipe) {
        boolean conditionMatched = conditionSupplier.get();
        for (int scroll = 0; scroll < maxSwipe && !conditionMatched; scroll++) {
            swipeDown(baseToSwipe, percentageToIgnoreBottom);
            conditionMatched = conditionSupplier.get();
        }
        return conditionMatched;
    }

    public static boolean swipeUpUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier) {
        return swipeUpUntil(baseToSwipe, conditionSupplier, 15);
    }

    /**
     * Swiping up until condition is met no matter if page source is changed or not.
     *
     * @param baseToSwipe       swipe will be done from this element (by coordinates)
     * @param conditionSupplier swipe until this condition met
     * @param maxSwipe          limit amount of swipes
     * @return true if condition was met within limited amount of swipes
     */
    public static boolean swipeUpUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier, int maxSwipe) {
        boolean conditionMatched = conditionSupplier.get();
        for (int scroll = 0; scroll < maxSwipe && !conditionMatched; scroll++) {
            swipeUp(baseToSwipe);
            conditionMatched = conditionSupplier.get();
        }
        return conditionMatched;
    }

    public static boolean swipeDownUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier) {
        return swipeDownUntil(baseToSwipe, conditionSupplier, 0.1);
    }

    public static boolean swipeDownUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier, int maxSwipe) {
        return swipeDownUntil(baseToSwipe, conditionSupplier, 0.1, maxSwipe);
    }

    public static boolean swipeLeftUntil(RemoteWebElement baseToSwipe, Supplier<Boolean> conditionSupplier, int maxSwipe) {
        boolean conditionMatched = conditionSupplier.get();
        for (int scroll = 0; scroll < maxSwipe && !conditionMatched; scroll++) {
            swipeLeft(baseToSwipe);
            conditionMatched = conditionSupplier.get();
        }
        return conditionMatched;
    }

    public static void tap(final int x, final int y) {
        Dimension dimension = getScreenDimension();
        int normalizedX = getNormalizedCoordinate(x, dimension.getWidth());
        int normalizedY = getNormalizedCoordinate(y, dimension.getHeight());

        touches().tap(tapOptions().withTapsCount(1).withPosition(point(normalizedX, normalizedY))).perform();
    }

    private static Dimension getScreenDimension() {
        return getDriver().manage().window().getSize();
    }

    private static int getNormalizedCoordinate(int original, int max) {
        return original >= max ? max - 1 : original;
    }

    public static void tapPercentage(RemoteWebElement el, double percentage) {
        if (percentage >= 0 && percentage <= 1) {
            Point location = el.getLocation();
            Dimension size = el.getSize();
            int xTap = (int) (location.getX() + size.getWidth() * percentage);
            int yTap = location.getY() + size.height / 2;
            tap(xTap, yTap);
        } else {
            throw new RuntimeException("Percentage value should be between 0 and 1");
        }
    }

    public static void tapInTheCenterOfScreen() {
        Dimension size = getDriver().manage().window().getSize();
        touches().tap(tapOptions().withTapsCount(1).withPosition(point(size.width / 2, size.height / 2))).perform();
    }

    public static void tapInCenterOfElement(RemoteWebElement element) {
        Point center = getCenter(element);
        tap(center.getX(), center.getY());
    }

    public static void tapRightFacet(RemoteWebElement element) {
        Point topLeft = element.getLocation();
        int height = element.getSize().getHeight();
        int width = element.getSize().getWidth();
        tap(topLeft.getX() + width - 10, topLeft.getY() + height / 2);
    }

    public static void doubleTap(RemoteWebElement element) {
    //    touches().tap(tapOptions().withElement(element).withTapsCount(2)).perform();
    }

    public static void clickAndroidBackButton() {
        ((AndroidDriver) getDriver()).pressKey(new KeyEvent().withKey(BACK));
    }

    public static void clickAndroidHomeButton() {
        ((AndroidDriver) getDriver()).pressKey(new KeyEvent().withKey(HOME));
    }


    public static void swipe(PointOption pressPoint, PointOption moveToPoint, Duration pressDuration) {
        touches().press(pressPoint)
                .waitAction(waitOptions(pressDuration))
                .moveTo(moveToPoint)
                .release()
                .perform();
    }

    public static void swipeDownFromTopToBottom() {
        Dimension size = getDriver().manage().window().getSize();
        PointOption pressPoint = point(size.width / 2, 1);
        PointOption moveToPoint = point(size.width / 2, size.height - 1);
        swipe(pressPoint, moveToPoint, ofSeconds(1));
    }

    public static void swipeUpFromBottomToTop() {
        Dimension size = getDriver().manage().window().getSize();
        PointOption pressPoint = point(size.width / 2, size.height - 1);
        PointOption moveToPoint = point(size.width / 2, 1);
        swipe(pressPoint, moveToPoint, ofSeconds(1));
    }

    public static void swipe(final RemoteWebElement press, final RemoteWebElement release) {
        Point releasePoint = getCenter(release);
        Point pressPoint = getCenter(press);
        PointOption<?> pressP = PointOption.point(pressPoint.getX(), pressPoint.getY());
        PointOption<?> releaseP = PointOption.point(releasePoint.getX(), releasePoint.getY());
        Touches.swipe(pressP, releaseP, Duration.ofMillis(3000));
    }

    public static void swipeByScreenDimension(double start, double finish) {
        Dimension screenDimension = getDriver().manage().window().getSize();
        swipe(
                point(screenDimension.getWidth() / 2, (int) (screenDimension.getHeight() * finish)),
                point(screenDimension.getWidth() / 2, (int) (screenDimension.getHeight() * start)),
                ofMillis(500));
    }

    private static TouchAction touches() {
        return new TouchAction((PerformsTouchActions) getDriver());
    }

    private static AppiumDriver getDriver() {
        return DriverProvider.get().getDriver();
    }

    /**
     * This method can be used as a condition to check whether page source changed or not
     *
     * @param stringBuilder used as a holder to keep recent page source for comparison with actual page source.
     * @return Supplier whether page source has changed or not after previous invocation.
     */
    private static Supplier<Boolean> getPageSourceNotChangedSupplier(StringBuilder stringBuilder) {
        return getPageSourceNotChangedAfterRunnableSupplier(stringBuilder, () -> {
        });
    }

    /**
     * This method can be used as a condition to check whether page source changed or not. Before actual comparison runnable code will be executed
     *
     * @param stringBuilder used as a holder to keep recent page source for comparison with actual page source.
     * @param run           runnable code to be executed before checking whether page source has changed or not
     * @return Supplier whether page source has changed or not after previous invocation.
     */
    private static Supplier<Boolean> getPageSourceNotChangedAfterRunnableSupplier(StringBuilder stringBuilder, Runnable run) {
        return () -> {
            String initialPageSource = stringBuilder.toString();
            String updatedPageSource = getDriver().getPageSource();
            stringBuilder.setLength(0);
            stringBuilder.append(updatedPageSource);
            run.run();
            return initialPageSource.equals(updatedPageSource);
        };
    }

    public static Point getCenter(RemoteWebElement element) {
        Rectangle rect = element.getRect();
        return new Point(rect.width / 2, rect.height / 2);
    }
}
