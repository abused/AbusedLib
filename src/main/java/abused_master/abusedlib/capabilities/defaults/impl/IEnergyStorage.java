package abused_master.abusedlib.capabilities.defaults.impl;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnergyStorage {

    void recieveEnergy(int recieve);
    void sendEnergy(World world, BlockPos pos, int amount);
    void extractEnergy(int amount);
    int getEnergyStored();
    int getEnergyCapacity();
    void setEnergyCapacity(int maxCapacity);
    void setEnergyStored(int energy);
}