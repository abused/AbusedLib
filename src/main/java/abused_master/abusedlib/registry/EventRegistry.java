package abused_master.abusedlib.registry;

import abused_master.abusedlib.events.Event;
import abused_master.abusedlib.events.EventSubscriber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventRegistry {

    public static Map<Method, Object> eventSubscriberMethods = new HashMap<>();
    public static Map<Method, Class> methodEventMap = new HashMap<>();

    public static void registerEventHandler(Object clazz) {
        for (Method method : clazz.getClass().getMethods()) {

            if(method.isAnnotationPresent(EventSubscriber.class)) {
                if (method.getParameters().length > 1) {
                    throw new UnsupportedOperationException("Cannot have multiple parameters on a @EventSubscriber method! " + method.getName());
                }

                for (Class paramClass : method.getParameterTypes()) {
                    eventSubscriberMethods.put(method, clazz);
                    methodEventMap.put(method, paramClass);
                }
            }
        }
    }

    public static void runEvent(Event event) {
        for (Method method : methodEventMap.keySet()) {
            if(methodEventMap.get(method) == event.getClass()) {
                invokeMethod(eventSubscriberMethods.get(method), method, event);
            }
        }
    }

    public static void invokeMethod(Object classOBject, Method method, Event event) {
        try {
            method.invoke(classOBject, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
