package view.main;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import view.AbstractView;

import static engine.ElementsFinder.findByIosNsPredicate;

public class ApiDemosView extends AbstractView {

    @AndroidFindBy(uiAutomator = "text(\"Animation\")")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'Animation'")
    private MobileElement animationButton;

    @AndroidFindBy(uiAutomator = "text(\"Cloning\")")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'Cloning'")
    private MobileElement cloningButton;
    @AndroidFindBy(uiAutomator = "text(\"OS\")")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'OS'")
    private MobileElement osButton;

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
