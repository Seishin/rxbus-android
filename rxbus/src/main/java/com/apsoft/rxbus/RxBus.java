package com.apsoft.rxbus;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.apsoft.rxbus.annotations.Event;
import com.apsoft.rxbus.exceptions.InvalidEventHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {

    private static final String TAG = RxBus.class.getCanonicalName();

    private static RxBus instance;

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());
    private HashMap<Object, ArrayList<Method>> handlers = new HashMap<>(0);

    public static RxBus getInstance() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    private RxBus() {
        bus.subscribe(new Action1<Object>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void call(Object o) {

                for (Map.Entry<Object, ArrayList<Method>> entrySet : handlers.entrySet()) {
                    for (Method m : entrySet.getValue()) {
                        if (m.getAnnotation(Event.class).value().equals(o.getClass())) {
                            try {
                                if (m.getParameterTypes().length > 1) {
                                    throw new InvalidEventHandler("Handler method must be with a single param of type " + o.getClass().getCanonicalName());
                                }
                                m.invoke(entrySet.getKey(), o);
                            } catch (IllegalAccessException | InvocationTargetException | InvalidEventHandler e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Sending an event to the Rx Event Bus.
     *
     * @param event
     */
    public void send(Object event) {
        bus.onNext(event);
    }

    /**
     * Register the given subscriber object to receive events.
     * Must call {@link #unregister(Object)} once it doesn't want to receive events.
     *
     * @param subscriber Object that will receive events once it's registered
     */
    public void register(Object subscriber) {
        ArrayList<Method> handlerMethods = new ArrayList<>(0);
        for (Method m : subscriber.getClass().getMethods()) {
            if (m.isAnnotationPresent(Event.class)) {
                handlerMethods.add(m);
            }
        }
        handlers.put(subscriber, handlerMethods);
        Log.i(TAG, "Registered: " + subscriber.getClass().getCanonicalName() + " Subscribed objects: " + handlers.size() + " with " + handlers.values().size() + " handlers");
    }

    /**
     * Unregister a subscriber from the Rx Bus.
     * Once it unregistered, it won't receive events anymore.
     *
     * @param subscriber Object that won't receive events anymore once it's unregistered
     */
    public void unregister(Object subscriber) {
        handlers.remove(subscriber);
        Log.i(TAG, "Unregistered: " + subscriber.getClass().getCanonicalName() + " Subscribed objects: " + handlers.size() + " with " + handlers.values().size() + " handlers");
    }
}
