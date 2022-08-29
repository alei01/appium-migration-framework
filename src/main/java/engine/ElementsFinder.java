package engine;

import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.util.List;

import static engine.AppPlatform.IOS;
import static engine.AppPlatform.is;
import static java.lang.String.format;

public class ElementsFinder {

    public static IOSElement findByIosNsPredicate(String predicated) {
        return (IOSElement) ((IOSDriver) getDriver()).findElementByIosNsPredicate(predicated);
    }

    public static MobileElement findByIosNsPredicate(MobileElement element, String predicated) {
        return ((IOSElement) element).findElementByIosNsPredicate(predicated);
    }

    public static MobileElement findByAccessibilityId(String accessibilityId) {
        return getDriver().findElementByAccessibilityId(accessibilityId);
    }

    public static List<MobileElement> findElementsByIosNsPredicate(MobileElement baseElement, String predicate) {
        return ((IOSElement) baseElement).findElementsByIosNsPredicate(predicate);
    }

    public static List<MobileElement> findElementsByIosNsPredicate(String predicated) {
        return ((IOSDriver<MobileElement>) getDriver()).findElementsByIosNsPredicate(predicated);
    }

    public static IOSElement findElementByIosClassChain(String classChainString) {
        return (IOSElement) ((IOSDriver<?>) getDriver()).findElementByIosClassChain(classChainString);
    }

    public static MobileElement findByAndroidUiAutomator(String uiSelector) {
        return ((AndroidDriver<MobileElement>) getDriver()).findElementByAndroidUIAutomator(uiSelector);
    }

    public static MobileElement findByAndroidUiAutomator(MobileElement element, String uiSelector) {
        return ((AndroidElement) element).findElementByAndroidUIAutomator(uiSelector);
    }

    public static List<MobileElement> findElementsByAndroidUiAutomator(String uiSelector) {
        return ((AndroidDriver<MobileElement>) getDriver()).findElementsByAndroidUIAutomator(uiSelector);
    }

    public static MobileElement findByXpath(final String xpath) {
        return getDriver().findElementByXPath(xpath);
    }

    public static MobileElement findElementByText(final String text) {
        try {
            return (is(IOS)
                    ? findByIosNsPredicate(format("type==\"XCUIElementTypeStaticText\" and label==\"%s\"", text))
                    : findByAndroidUiAutomator(format("text(\"%s\")", text)));
        } catch (NoSuchElementException ignore) {
            return null;
        }
    }

    public static MobileElement findElementByPartialText(final String text) {
        return (is(IOS)
                ? findElementsByIosNsPredicate(format("type==\"XCUIElementTypeStaticText\" and label contains \"%s\"", text))
                : findElementsByAndroidUiAutomator(format("textContains(\"%s\")", text)))
                .stream().findFirst().orElseThrow(() ->
                        new TimeoutException(format("Could not find element with text matches '%s'", text)));
    }

    public static MobileElement findElementByTextFromParent(final String text, final MobileElement parentElement) {
        return (is(IOS)
                ? ((IOSElement) parentElement).findElementsByIosNsPredicate(format("type==\"XCUIElementTypeStaticText\"" +
                " and label==\"%s\"", text))
                : ((AndroidElement) parentElement).findElementsByAndroidUIAutomator(format("text(\"%s\")",
                text)))
                .stream().findFirst().orElse(null);
    }

    public static MobileElement findButtonByText(final String text) {
        return (is(IOS)
                ? findElementsByIosNsPredicate(format("type==\"XCUIElementTypeButton\" and label==\"%s\"", text))
                : findElementsByAndroidUiAutomator(format("text(\"%s\")", text)))
                .stream().findFirst().orElse(null);
    }

    public static AppiumDriver<MobileElement> getDriver() {
        return DriverProvider.get().getDriver();
    }
}
