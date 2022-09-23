package engine;

import driver.DriverProvider;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebElement;

public class ElementsFinder {

    public static RemoteWebElement findByIosNsPredicate(String predicated) {
        return (RemoteWebElement) getDriver().findElement(AppiumBy.iOSNsPredicateString(predicated));
    }

    public static RemoteWebElement findByIosNsPredicate(RemoteWebElement element, String predicated) {
        return (RemoteWebElement) element.findElement(AppiumBy.iOSNsPredicateString(predicated));
    }
/*

    public static RemoteWebElement findByAccessibilityId(String accessibilityId) {
        return getDriver().findElementByAccessibilityId(accessibilityId);
    }

    public static List<RemoteWebElement> findElementsByIosNsPredicate(RemoteWebElement baseElement, String predicate) {
        return ((RemoteWebElement) baseElement).findElementsByIosNsPredicate(predicate);
    }

    public static List<RemoteWebElement> findElementsByIosNsPredicate(String predicated) {
        return ((IOSDriver<RemoteWebElement>) getDriver()).findElementsByIosNsPredicate(predicated);
    }

    public static RemoteWebElement findElementByIosClassChain(String classChainString) {
        return (RemoteWebElement) ((IOSDriver<?>) getDriver()).findElementByIosClassChain(classChainString);
    }

    public static RemoteWebElement findByAndroidUiAutomator(String uiSelector) {
        return ((AndroidDriver<RemoteWebElement>) getDriver()).findElementByAndroidUIAutomator(uiSelector);
    }

    public static RemoteWebElement findByAndroidUiAutomator(RemoteWebElement element, String uiSelector) {
        return ((AndroidElement) element).findElementByAndroidUIAutomator(uiSelector);
    }

    public static List<RemoteWebElement> findElementsByAndroidUiAutomator(String uiSelector) {
        return ((AndroidDriver<RemoteWebElement>) getDriver()).findElementsByAndroidUIAutomator(uiSelector);
    }

    public static RemoteWebElement findByXpath(final String xpath) {
        return getDriver().findElementByXPath(xpath);
    }

    public static RemoteWebElement findElementByText(final String text) {
        try {
            return (is(IOS)
                    ? findByIosNsPredicate(format("type==\"XCUIElementTypeStaticText\" and label==\"%s\"", text))
                    : findByAndroidUiAutomator(format("text(\"%s\")", text)));
        } catch (NoSuchElementException ignore) {
            return null;
        }
    }

    public static RemoteWebElement findElementByPartialText(final String text) {
        return (is(IOS)
                ? findElementsByIosNsPredicate(format("type==\"XCUIElementTypeStaticText\" and label contains \"%s\"", text))
                : findElementsByAndroidUiAutomator(format("textContains(\"%s\")", text)))
                .stream().findFirst().orElseThrow(() ->
                        new TimeoutException(format("Could not find element with text matches '%s'", text)));
    }

    public static RemoteWebElement findElementByTextFromParent(final String text, final RemoteWebElement parentElement) {
        return (is(IOS)
                ? ((RemoteWebElement) parentElement).findElementsByIosNsPredicate(format("type==\"XCUIElementTypeStaticText\"" +
                " and label==\"%s\"", text))
                : ((AndroidElement) parentElement).findElementsByAndroidUIAutomator(format("text(\"%s\")",
                text)))
                .stream().findFirst().orElse(null);
    }

    public static RemoteWebElement findButtonByText(final String text) {
        return (is(IOS)
                ? findElementsByIosNsPredicate(format("type==\"XCUIElementTypeButton\" and label==\"%s\"", text))
                : findElementsByAndroidUiAutomator(format("text(\"%s\")", text)))
                .stream().findFirst().orElse(null);
    }
*/

    public static AppiumDriver getDriver() {
        return DriverProvider.get().getDriver();
    }
}
