package com.rarvinw.app.basic.androidboot.mvp;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by newhope on 2016/4/4.
 */
public abstract class NullablePresenter<V extends IView> implements IPresenter<V> {
    protected WeakReference<V> view;
    private final V nullView;

    public NullablePresenter() {
        try {

            Class<V> viewClass = null;
            Class<?> currentClass = getClass();

            while (viewClass == null) {

                Type genericSuperType = currentClass.getGenericSuperclass();

                while (!(genericSuperType instanceof ParameterizedType)) {
                    currentClass = currentClass.getSuperclass();
                    genericSuperType = currentClass.getGenericSuperclass();
                }

                Type[] types = ((ParameterizedType) genericSuperType).getActualTypeArguments();

                for (int i = 0; i < types.length; i++) {
                    Class<?> genericType = (Class<?>) types[i];
                    if (genericType.isInterface() && isSubTypeOfMvpView(genericType)) {
                        viewClass = (Class<V>) genericType;
                        break;
                    }
                }

                currentClass = currentClass.getSuperclass();
            }

            nullView = NoOp.of(viewClass);
        } catch (Throwable t) {
            throw new IllegalArgumentException(
                    "The generic type <V extends MvpView> must be the first generic type argument of class "
                            + getClass().getSimpleName()
                            + " (per convention). Otherwise we can't determine which type of View this"
                            + " Presenter coordinates.", t);
        }
    }

    private boolean isSubTypeOfMvpView(Class<?> klass) {
        if (klass.equals(IView.class)) {
            return true;
        }
        Class[] superInterfaces = klass.getInterfaces();
        for (int i = 0; i < superInterfaces.length; i++) {
            if (isSubTypeOfMvpView(superInterfaces[0])) {
                return true;
            }
        }
        return false;
    }

    @Override public void attachView(V view) {
        this.view = new WeakReference<V>(view);
    }

    protected V getView() {
        if (view != null) {
            V realView = view.get();
            if (realView != null) {
                return realView;
            }
        }

        return nullView;
    }

    @Override public void detachView(boolean retainInstance) {
        if (view != null) {
            view.clear();
            view = null;
        }
    }
}
