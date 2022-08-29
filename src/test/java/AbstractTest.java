import factory.StepFactory;
import org.testng.annotations.BeforeSuite;
import steps.main.AnimationCloningViewSteps;
import steps.main.ApiDemosViewSteps;
import view.ViewsHolder;

public class AbstractTest {
    public final ApiDemosViewSteps apiDemosViewSteps = StepFactory.get(ApiDemosViewSteps.class);
    public final AnimationCloningViewSteps animationCloningViewSteps = StepFactory.get(AnimationCloningViewSteps.class);

    @BeforeSuite
    public void beforeSuite() {
        ViewsHolder.initViews();
    }
}
