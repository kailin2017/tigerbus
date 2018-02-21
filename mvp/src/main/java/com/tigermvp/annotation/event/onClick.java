package com.tigermvp.annotation.event;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@BaseEvent(listenerType = View.OnClickListener.class, setListener = ListenerSet.setOnClickListener, methodName = MethodName.onClick)
public @interface onClick {
    int[] value();
}
