package factory;

import steps.AbstractSteps;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class StepFactory {
    private static final ThreadLocal<Map<Class<? extends AbstractSteps>, AbstractSteps>> stepsMap =
            ThreadLocal.withInitial(HashMap::new);

    public static <T extends AbstractSteps> T get(final Class<T> initStepClass) {
        AbstractSteps steps = stepsMap.get().get(initStepClass);
        if (null == steps) {
            steps = getStepsInstance(initStepClass);
            stepsMap.get().put(initStepClass, steps);
        }
        return (T) steps;
    }

    private static <T extends AbstractSteps> AbstractSteps getStepsInstance(final Class<T> initStepClass) {
        AbstractSteps steps;
        Class stepsClass = null;

        if (!isClassAbstract(initStepClass)) {
            stepsClass = initStepClass;
        }

        try {
            steps = (T) stepsClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of platform steps: " + initStepClass.getName(), e);
        }
        return steps;
    }

    private static boolean isClassAbstract(final Class<? extends AbstractSteps> initStepClass) {
        return Modifier.isAbstract(initStepClass.getModifiers());
    }
}
