package view;

import java.util.HashMap;
import java.util.Map;

public final class ViewsHolder {

    private static final ThreadLocal<Map<Class, AbstractView>> viewsMap = ThreadLocal
            .withInitial(HashMap::new);

    private ViewsHolder() {
    }

    /**
     * Inits views in the views
     */
    public static void initViews() {
        viewsMap.get().values().forEach(AbstractView::initView);
    }

    /**
     * Gets a view (implemented from {@link AbstractView}) class.
     *
     * @param viewClass view class.
     * @param <T>       class of type T.
     * @return class of {@link AbstractView} descendant.
     */
    public static <T extends AbstractView> T getView(final Class<T> viewClass) {
        T view = (T) viewsMap.get().get(viewClass);
        if (null == view) {
            view = getInstance(viewClass);
            viewsMap.get().put(viewClass, view);
        }
        return view;
    }

    private static <T extends AbstractView> T getInstance(Class<T> viewClass) {
        try {
            return viewClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of view: " + viewClass.getName(), e);
        }
    }
}