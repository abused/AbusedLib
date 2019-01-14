package abused_master.abusedlib.registry;

import abused_master.abusedlib.AbusedLib;
import abused_master.abusedlib.capabilities.Capability;
import abused_master.abusedlib.capabilities.CapabilityHandler;
import abused_master.abusedlib.capabilities.defaults.CapabilityEnergyStorage;
import abused_master.abusedlib.capabilities.defaults.CapabilityItemHandler;
import abused_master.abusedlib.capabilities.utils.CapabilityInject;
import abused_master.abusedlib.capabilities.utils.RegisterCapability;
import net.minecraft.util.Identifier;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CapabilityRegistry {

    public static final CapabilityRegistry INSTANCE = new CapabilityRegistry();

    private final Map<Identifier, Class> capabilitiesMap;
    private final List<Function<Capability<?>, Object>> functions;
    private final MethodHandles.Lookup methodLookup;

    public CapabilityRegistry() {
        capabilitiesMap = new HashMap<>();
        functions = new ArrayList<>();
        methodLookup = MethodHandles.lookup();
    }

    /**
     * Add default capabilities to list
     */
    public void addCapabilities() {
        capabilitiesMap.put(new Identifier(AbusedLib.MODID, "capability_item_handler"), CapabilityItemHandler.class);
        capabilitiesMap.put(new Identifier(AbusedLib.MODID, "capability_energy"), CapabilityEnergyStorage.class);
    }

    /**
     * Loop through default capabilities in list and register them
     */
    public void registerCapabilities() {
        addCapabilities();

        for (Identifier identifier : capabilitiesMap.keySet()) {
            Class clazz = capabilitiesMap.get(identifier);
            registerCapability(clazz);
        }
    }

    /**
     * Register a custom capability
     *
     * @param identifier - The unique identifier for the capability
     * @param clazz      - The Capability.class to register
     */
    public void registerCapability(Identifier identifier, Class clazz) {
        capabilitiesMap.put(identifier, clazz);
        registerCapability(clazz);
    }

    /**
     * Called to get and invoke/set the RegisterCapability and CapabilityInject annotations
     *
     * @param clazz - The capability class
     */
    private void registerCapability(Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(RegisterCapability.class)) {
                continue;
            }

            if (Modifier.isPublic(method.getModifiers())) {
                throw new UnsupportedOperationException("RegisterCapability methods must be public. Method: " + method.getName());
            }

            if (method.getParameterTypes().length > 0) {
                throw new UnsupportedOperationException("RegisterCapability methods must not contain any parameters. Method " + method.getName());
            }

            try {
                MethodHandle methodHandle = methodLookup.unreflect(method).bindTo(clazz);
                methodHandle.invoke(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(CapabilityInject.class)) {
                continue;
            }

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
