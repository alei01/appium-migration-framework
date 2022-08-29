package view;

import driver.DriverProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public abstract class AbstractView {

    protected AppiumDriver driver;
    private static final Duration IMPLICIT_TIMEOUT = Duration.ofMillis(10000);

    protected AbstractView() {
        initView();
    }

    public abstract boolean isActive();

    public void initView() {
        this.driver = DriverProvider.get().getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, IMPLICIT_TIMEOUT), this);
    }
}
