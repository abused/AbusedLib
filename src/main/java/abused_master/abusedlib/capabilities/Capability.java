package abused_master.abusedlib.capabilities;

import net.minecraft.nbt.Tag;
import net.minecraft.util.math.Direction;

import java.util.concurrent.Callable;

public class Capability<T> {

    private String name;
    private IStorage<T> storage;
    private Callable<? extends T> factory;

    public Capability(String name, IStorage<T> storage, Callable<? extends T> factory) {
        this.name = name;
        this.storage = storage;
        this.factory = factory;
    }

    public interface IStorage<T> {
        Tag toTag(Capability<T> capability, T instance, Direction direction);
        void fromTag(Capability<T> capatility, T instance, Direction direction, Tag tag);
    }

    public String getName() {
        return name;
    }

    public IStorage<T> getStorage() {
        return storage;
    }

    public void fromNBT(T instance, Direction direction, Tag tag) {
        storage.fromTag(this, instance, direction, tag);
    }

    public Tag toTag(T instance, Direction direction) {
        return storage.toTag(this, instance, direction);
    }
}
