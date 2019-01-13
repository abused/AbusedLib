package abused_master.abusedlib.capabilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

public enum CapabilityHandler {
    INSTANCE;

    private IdentityHashMap<String, Capability<?>> providers = Maps.newIdentityHashMap();
    private IdentityHashMap<String, List<Function<Capability<?>, Object>>> callbacks = Maps.newIdentityHashMap();

    /**
     * Register a capability
     * @param type - The interface class the capability bases itself off of
     * @param storage - The IStorage class that contains the NBT data
     * @param implementation - The default implementation of the capability
     * @param <T>
     */
    public <T> void register(Class<T> type, Capability.IStorage<T> storage, final Class<? extends T> implementation) {
        Preconditions.checkArgument(implementation != null, "Invalid capability implementation registry");
        register(type, storage, () ->
        {
            try {
                return implementation.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Register a capability
     * @param type - The interface class the capability bases itself off of
     * @param storage - The IStorage class that contains the NBT data
     * @param factory - The default implementation of the capability as a Callable
     * @param <T>
     */
    public <T> void register(Class<T> type, Capability.IStorage<T> storage, Callable<? extends T> factory) {
        Preconditions.checkArgument(type    != null, "Attempted to register a capability with invalid type");
        Preconditions.checkArgument(storage != null, "Attempted to register a capability with no storage implementation");
        Preconditions.checkArgument(factory != null, "Attempted to register a capability with no defaults implementation factory");
        String realName = type.getName().intern();
        Preconditions.checkState(!providers.containsKey(realName), "Can not register a capability implementation multiple times: %s", realName);

        Capability<T> cap = new Capability<T>(realName, storage, factory);
        providers.put(realName, cap);

        List<Function<Capability<?>, Object>> list = callbacks.get(realName);
        if (list != null)
        {
            for (Function<Capability<?>, Object> func : list)
            {
                func.apply(cap);
            }
        }
    }

    /**
     * Setting the field value for a capability annotated with @CapabilityInject
     * @param field - The field
     * @param target - The target object
     * @param value - The value to use
     * @throws Exception
     */
    public static void setFailsafeFieldValue(Field field, Object target, Object value) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        Method newFieldAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newFieldAccessor", Field.class, boolean.class);
        Method getReflectionFactory = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("getReflectionFactory");
        Method fieldAccessorSet = Class.forName("sun.reflect.FieldAccessor").getDeclaredMethod("set", Object.class, Object.class);

        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        Object reflectionFactory = getReflectionFactory.invoke(null);
        Object fieldAccessor = newFieldAccessor.invoke(reflectionFactory, field, false);
        fieldAccessorSet.invoke(fieldAccessor, target, value);
    }
}
