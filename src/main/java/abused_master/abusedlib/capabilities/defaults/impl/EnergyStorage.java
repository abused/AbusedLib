package abused_master.abusedlib.capabilities.defaults.impl;

import abused_master.abusedlib.capabilities.defaults.CapabilityEnergyStorage;
import abused_master.abusedlib.capabilities.utils.ICapabilityContainer;
import abused_master.abusedlib.tiles.TileEntityBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyStorage implements IEnergyStorage {

    private int capacity;
    private int energyStored;

    public EnergyStorage(int capacity) {
        this.capacity = capacity;
        this.energyStored = 0;
    }

    public EnergyStorage(int capacity, int amount) {
        this.capacity = capacity;
        this.energyStored = amount;
    }

    @Override
    public void recieveEnergy(int recieve) {
        this.energyStored += recieve;

        if(energyStored > capacity) {
            energyStored = capacity;
        }
    }

    @Override
    public void sendEnergy(World world, BlockPos pos, int amount) {
        if(world.getBlockEntity(pos) instanceof TileEntityBase || world.getBlockEntity(pos) instanceof ICapabilityContainer) {
            if(amount > energyStored) {
                ((ICapabilityContainer) world.getBlockEntity(pos)).getCapability(CapabilityEnergyStorage.CAPABILITY_ENERGY_STORAGE, null).recieveEnergy(energyStored);
                this.extractEnergy(energyStored);
            }else {
                ((ICapabilityContainer) world.getBlockEntity(pos)).getCapability(CapabilityEnergyStorage.CAPABILITY_ENERGY_STORAGE, null).recieveEnergy(energyStored);
                this.extractEnergy(amount);
            }
        }
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

    public CompoundTag writeEnergyToNBT(CompoundTag nbt) {
        nbt.putInt("capacity", capacity);
        nbt.putInt("energyStored", energyStored);
        return nbt;
    }

    public EnergyStorage readFromNBT(CompoundTag nbt) {
        capacity = nbt.getInt("capacity");
        energyStored = nbt.getInt("energyStored");
        return this;
    }
}