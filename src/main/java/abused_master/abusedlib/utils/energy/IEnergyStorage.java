package abused_master.abusedlib.utils.energy;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnergyStorage {

    void recieveEnergy(int recieve);
    void sendEnergy(World world, BlockPos pos, int amount);
    void extractEnergy(int amount);
    int getEnergyStored();
    int getEnergyCapacity();
    void setEnergyCapacity(int maxCapacity);
}