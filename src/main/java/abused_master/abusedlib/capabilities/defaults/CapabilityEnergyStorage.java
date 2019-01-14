package abused_master.abusedlib.capabilities.defaults;

import abused_master.abusedlib.capabilities.Capability;
import abused_master.abusedlib.capabilities.CapabilityHandler;
import abused_master.abusedlib.capabilities.utils.CapabilityInject;
import abused_master.abusedlib.capabilities.utils.RegisterCapability;
import abused_master.abusedlib.capabilities.defaults.impl.EnergyStorage;
import abused_master.abusedlib.capabilities.defaults.impl.IEnergyStorage;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.Direction;

public class CapabilityEnergyStorage {

    @CapabilityInject(IEnergyStorage.class)
    public static Capability<IEnergyStorage> CAPABILITY_ENERGY_STORAGE = null;

    @RegisterCapability
    public static void register() {
        CapabilityHandler.INSTANCE.register(IEnergyStorage.class, new Capability.IStorage<IEnergyStorage>() {
            @Override
            public Tag toTag(Capability<IEnergyStorage> capability, IEnergyStorage instance, Direction direction) {
                return new IntTag(instance.getEnergyStored());
            }

            @Override
            public void fromTag(Capability<IEnergyStorage> capatility, IEnergyStorage instance, Direction direction, Tag tag) {
                if(!(instance instanceof  EnergyStorage))
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

                instance.setEnergyStored(((IntTag) tag).getInt());
            }
        }, () -> new EnergyStorage(1000));
    }
}
