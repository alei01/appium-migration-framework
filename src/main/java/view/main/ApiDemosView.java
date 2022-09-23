package view.main;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.remote.RemoteWebElement;
import view.AbstractView;

import static engine.ElementsFinder.findByIosNsPredicate;

public class ApiDemosView extends AbstractView {

    @AndroidFindBy(uiAutomator = "text(\"Animation\")")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'Animation'")
    private RemoteWebElement animationButton;

    @AndroidFindBy(uiAutomator = "text(\"Cloning\")")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'Cloning'")
    private RemoteWebElement cloningButton;
    @AndroidFindBy(uiAutomator = "text(\"OS\")")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'OS'")
    private RemoteWebElement osButton;

    @Override
    public boolean isActive() {
        return true;
    }

    public void clickAnimationButton() {
        animationButton.click();
    }

    public void clickCloningButton() {
        cloningButton.click();
    }

    public void clickOsButton() {
        osButton.click();
    }

    public String getIosSignUpFieldText() {
        return findByIosNsPredicate(cloningButton, "name == 'button1'").getText();
    }
}
