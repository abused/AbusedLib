package abused_master.abusedlib.network.capability;

import abused_master.abusedlib.api.IEssence;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityEssence implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IEssence.class)
    public static final Capability<IEssence> CAPABILITY_ESSENCE = null;

    private IEssence instance = CAPABILITY_ESSENCE.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CAPABILITY_ESSENCE;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CAPABILITY_ESSENCE ? CAPABILITY_ESSENCE.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CAPABILITY_ESSENCE.getStorage().writeNBT(CAPABILITY_ESSENCE, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CAPABILITY_ESSENCE.getStorage().readNBT(CAPABILITY_ESSENCE, this.instance, null, nbt);
    }
}