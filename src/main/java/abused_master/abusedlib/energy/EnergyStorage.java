package abused_master.abusedlib.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyStorage implements IEnergyStorage {

    private int capacity;
    private int energyStored;

    public EnergyStorage(int capacity) {
        this(capacity, 0);
    }

    public EnergyStorage(int capacity, int amount) {
        this.capacity = capacity;
        this.energyStored = amount;
    }

    @Override
    public void recieveEnergy(int recieve) {
        this.energyStored += recieve;

        if (energyStored > capacity) {
            energyStored = capacity;
        }
    }

    @Override
    public boolean sendEnergy(World world, BlockPos pos, int amount) {
        if(amount <= energyStored) {
            if(world.getBlockEntity(pos) instanceof IEnergyReceiver && getEnergyReceiver(world, pos).receiveEnergy(amount)) {
                this.extractEnergy(amount);
                return true;
            }
        }

        return false;
    }

    public IEnergyReceiver getEnergyReceiver(World world, BlockPos pos) {
        return (IEnergyReceiver) world.getBlockEntity(pos);
    }

    @Override
    public void extractEnergy(int amount) {
        this.energyStored -= amount;

        if(amount > energyStored) {
            this.energyStored = 0;
        }
    }

    @Override
    public int getEnergyStored() {
        return energyStored;
    }

    @Override
    public int getEnergyCapacity() {
        return this.capacity;
    }

    @Override
    public void setEnergyCapacity(int maxCapacity) {
        this.capacity = maxCapacity;
    }

    @Override
    public void setEnergyStored(int energy) {
        this.energyStored = energy;
    }

    @Override
    public CompoundTag writeEnergyToNBT(CompoundTag nbt) {
        nbt.putInt("capacity", capacity);
        nbt.putInt("energyStored", energyStored);
        return nbt;
    }

    @Override
    public EnergyStorage readFromNBT(CompoundTag nbt) {
        capacity = nbt.getInt("capacity");
        energyStored = nbt.getInt("energyStored");
        return this;
    }
}