package abused_master.abusedlib.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnergyStorage {

    void recieveEnergy(int recieve);
    boolean sendEnergy(World world, BlockPos pos, int amount);
    void extractEnergy(int amount);
    int getEnergyStored();
    int getEnergyCapacity();
    void setEnergyCapacity(int maxCapacity);
    void setEnergyStored(int energy);
    CompoundTag writeEnergyToNBT(CompoundTag nbt);
    EnergyStorage readFromNBT(CompoundTag nbt);
}