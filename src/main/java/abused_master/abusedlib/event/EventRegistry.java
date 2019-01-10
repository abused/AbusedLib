package abused_master.abusedlib.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventRegistry {

    public static List<Class> eventHandlerClasses = new ArrayList<>();

    public static void registerEventHandler(Class clazz) {
        eventHandlerClasses.add(clazz);
    }

    public static void invokeEvent(Event event) {
        for (Class clazz : eventHandlerClasses) {
            for (Method method : clazz.getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof SubscribeEvent) {
                        try {
                            method.invoke(clazz.newInstance(), event);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
