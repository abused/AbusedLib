package abused_master.abusedlib.registry;

import abused_master.abusedlib.AbusedLib;
import abused_master.abusedlib.capabilities.Capability;
import abused_master.abusedlib.capabilities.CapabilityHandler;
import abused_master.abusedlib.capabilities.utils.CapabilityInject;
import abused_master.abusedlib.capabilities.utils.RegisterCapability;
import abused_master.abusedlib.capabilities.defaults.inventory.CapabilityItemHandler;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CapabilityRegistry {

    private static Map<Identifier, Class> capabilitiesMap = new HashMap<>();
    private static List<Function<Capability<?>, Object>> functions = new ArrayList();

    /**
     * Add default capabilities to list
     */
    public static void addCapabilities() {
        capabilitiesMap.put(new Identifier(AbusedLib.MODID, "capability_item_handler"), CapabilityItemHandler.class);
    }

    /**
     * Loop through default capabilities in list and register them
     */
    public static void registerCapabilities() {
        addCapabilities();

        for (Identifier identifier : capabilitiesMap.keySet()) {
            Class clazz = capabilitiesMap.get(identifier);
            registerCapability(clazz);
        }
    }

    /**
     * Register a custom capability
     * @param identifier - The unique identifier for the capability
     * @param clazz - The Capability.class to register
     */
    public static void registerCapability(Identifier identifier, Class clazz) {
        capabilitiesMap.put(identifier, clazz);
        registerCapability(clazz);
    }

    /**
     * Called to get and invoke/set the RegisterCapability and CapabilityInject annotations
     * @param clazz - The capability class
     */
    private static void registerCapability(Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if(method.isAnnotationPresent(RegisterCapability.class)) {
                try {
                    method.invoke(null, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            for (Field field : clazz.getDeclaredFields()) {
                if(field.isAnnotationPresent(CapabilityInject.class)) {
                    functions.add(capability -> {
                        try {
                            CapabilityHandler.INSTANCE.setFailsafeFieldValue(field, null, capability);
                        } catch (Exception e) {
                            AbusedLib.LOGGER.warning("Unable to inject capability Name: " + field.getName() + " Target Class: " + clazz.getName());
                            e.printStackTrace();
                        }

                        return null;
                    });
                }
            }
        }
    }
}
