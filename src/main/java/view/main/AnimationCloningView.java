package view.main;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import view.AbstractView;

public class AnimationCloningView extends AbstractView {
    @AndroidFindBy(uiAutomator = "text(\"RUN\")")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'RUN'")
    private MobileElement runButton;

    @Override
    public boolean isActive() {
        return true;
    }

    public void clickRunButton() {
        runButton.click();
    }
}
