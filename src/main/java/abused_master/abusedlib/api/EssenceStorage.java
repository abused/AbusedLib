package abused_master.abusedlib.api;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * This class is responsible for saving and reading essence data from or to server
 */
public class EssenceStorage implements Capability.IStorage<IEssence> {

    int w = 0;
    int f = 0;
    int e = 0;
    int a = 0;

    @Override
    public NBTBase writeNBT(Capability<IEssence> capability, IEssence instance, EnumFacing side)
    {

        w = instance.getEssenceWater();
        f = instance.getEssenceFire();
        e = instance.getEssenceEarth();
        a = instance.getEssenceAir();

        return new NBTTagInt(instance.getEssenceTotal());
    }

    @Override
    public void readNBT(Capability<IEssence> capability, IEssence instance, EnumFacing side, NBTBase nbt)
    {
        int water = ((NBTPrimitive) nbt).getInt() - f - e - a;
        int fire = ((NBTPrimitive) nbt).getInt() - w - e - a;
        int earth = ((NBTPrimitive) nbt).getInt() - w - f - a;
        int air = ((NBTPrimitive) nbt).getInt() - w - f - e;

        instance.setAmountWater(water);
        instance.setAmountFire(fire);
        instance.setAmountEarth(earth);
        instance.setAmountAir(air);
    }
}
