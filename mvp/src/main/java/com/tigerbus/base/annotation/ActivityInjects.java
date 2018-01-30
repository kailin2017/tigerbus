package com.tigerbus.base.annotation;

import android.view.View;

import com.tigerbus.base.MvpActivity;
import com.tigerbus.base.annotation.event.BaseEvent;
import com.tigerbus.base.log.Tlog;
import com.tigerbus.base.log.TlogType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class ActivityInjects {

    private static final String TAG = ActivityInjects.class.getSimpleName();
    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Class<? extends MvpActivity> clazz, MvpActivity mvpActivity) {
        try {
            injectActivityView(clazz, mvpActivity);
            injectViews(clazz, mvpActivity);
            injectEvent(clazz, mvpActivity);
        } catch (Exception e) {
            Tlog.printLog(TlogType.error, TAG, e.toString());
        }
    }

    private static void injectActivityView(Class<? extends MvpActivity> clazz, MvpActivity mvpActivity) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        ActivityView activityView = clazz.getAnnotation(ActivityView.class);
        if (activityView != null) {
            Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW, int.class);
            method.setAccessible(true);
            method.invoke(mvpActivity, activityView.layout());
        }
    }

    private static void injectViews(Class<? extends MvpActivity> clazz, MvpActivity mvpActivity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            ViewInject viewInjects = field.getAnnotation(ViewInject.class);
            if (viewInjects != null) {
                int viewId = viewInjects.value();
                if (viewId != -1) {
                    Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
                    Object resView = method.invoke(mvpActivity, viewId);
                    field.setAccessible(true);
                    field.set(mvpActivity, resView);
                }
            }
        }
    }

    private static void injectEvent(Class<? extends MvpActivity> clazz, MvpActivity mvpActivity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Method method : clazz.getDeclaredMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                BaseEvent baseEvent = annotationType.getAnnotation(BaseEvent.class);
                if (baseEvent != null) {
                    Class<?> listenerType = baseEvent.listenerType();
                    String methodName = baseEvent.methodName();
                    String listenerSetter = baseEvent.setListener();

                    DynamicHandler dynamicHandler = new DynamicHandler(mvpActivity);
                    dynamicHandler.addMethod(methodName, method);
                    Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, dynamicHandler);

                    for (int id : (int[]) annotationType.getDeclaredMethod("value").invoke(annotation, null)) {
                        View view = mvpActivity.findViewById(id);
                        view.getClass().getMethod(listenerSetter, listenerType).invoke(view, listener);
                    }
                }
            }
        }
    }
}
