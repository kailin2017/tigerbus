package com.tigerbus.base.annotation;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public final class DynamicHandler implements InvocationHandler {
    private WeakReference<Object> handlerRef;
    private final HashMap<String, Method> methodMap = new HashMap<>(1);

    public DynamicHandler(Object o) {
        this.handlerRef = new WeakReference<>(o);
    }

    public void addMethod(String name, Method method) {
        methodMap.put(name, method);
    }

    public Object getHandler() {
        return handlerRef.get();
    }

    public void setHandler(Object o) {
        this.handlerRef = new WeakReference<>(o);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object handler = handlerRef.get();
        if (handler != null) {
            method = methodMap.get(method.getName());
            if (method != null) {
                return method.invoke(handler, args);
            }
        }
        return null;
    }
}
