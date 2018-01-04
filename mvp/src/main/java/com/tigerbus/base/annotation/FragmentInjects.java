package com.tigerbus.base.annotation;

import android.view.View;

import com.tigerbus.base.MvpFragment;
import com.tigerbus.base.annotation.event.BaseEvent;
import com.tigerbus.base.log.Tlog;
import com.tigerbus.base.log.TlogType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class FragmentInjects {

    private final static String TAG = FragmentInjects.class.getSimpleName();


    public static void inject(MvpFragment mvpFragment, View layoutView) {
        try {
            Class<? extends MvpFragment> clazz = mvpFragment.getClass();
            injectViews(clazz, mvpFragment, layoutView);
            injectEvent(clazz, mvpFragment, layoutView);
        } catch (Exception e) {
            Tlog.printLog(TlogType.error, TAG, e.toString());
        }
    }

    private static void injectViews(Class<? extends MvpFragment> clazz, MvpFragment mvpFragment, View layoutView) throws IllegalAccessException {
        for (Field field : clazz.getFields()) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int viewId = viewInject.value();
                if (viewId != -1) {
                    View view = layoutView.findViewById(viewId);
                    field.setAccessible(true);
                    field.set(mvpFragment, view);
                }
            }
        }
    }

    private static void injectEvent(Class<? extends MvpFragment> clazz, MvpFragment mvpFragment, View layoutView) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Method method : clazz.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                BaseEvent baseEvent = annotationType.getAnnotation(BaseEvent.class);
                if (baseEvent != null) {
                    Class<?> listenerType = baseEvent.listenerType();
                    String methodName = baseEvent.methodName();
                    String listenerSetter = baseEvent.setListener();

                    DynamicHandler dynamicHandler = new DynamicHandler(mvpFragment);
                    dynamicHandler.addMethod(methodName, method);
                    Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, dynamicHandler);

                    for (int id : (int[]) annotationType.getDeclaredMethod("value").invoke(annotation, null)) {
                        View view = layoutView.findViewById(id);
                        view.getClass().getMethod(listenerSetter, listenerType).invoke(view, listener);
                    }
                }
            }
        }
    }
}
