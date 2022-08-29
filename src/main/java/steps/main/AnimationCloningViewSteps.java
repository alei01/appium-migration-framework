package steps.main;

import steps.AbstractSteps;
import view.ViewsHolder;
import view.main.AnimationCloningView;

public class AnimationCloningViewSteps extends AbstractSteps {
    private final AnimationCloningView animationCloningView = ViewsHolder.getView(AnimationCloningView.class);

    public void clickRunButton() {
        animationCloningView.clickRunButton();
    }
}
