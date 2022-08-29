package steps.main;

import steps.AbstractSteps;
import view.main.ApiDemosView;
import view.ViewsHolder;

public class ApiDemosViewSteps extends AbstractSteps {
    private final ApiDemosView apiDemosView = ViewsHolder.getView(ApiDemosView.class);

    public void openAnimationCloningView(){
        apiDemosView.clickAnimationButton();
        apiDemosView.clickCloningButton();
    }

    public void openOsView() {
        apiDemosView.clickOsButton();
    }

    public String getIosSignUpFieldText() {
       return apiDemosView.getIosSignUpFieldText();
    }
}
