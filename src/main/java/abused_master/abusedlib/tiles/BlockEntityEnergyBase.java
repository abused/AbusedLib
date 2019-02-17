package abused_master.abusedlib.tiles;

import abused_master.abusedlib.energy.EnergyStorage;
import abused_master.abusedlib.energy.IEnergyHandler;
import net.minecraft.block.entity.BlockEntityType;

public abstract class BlockEntityEnergyBase extends BlockEntityBase implements IEnergyHandler {

    public BlockEntityEnergyBase(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
    }

    public boolean handleEnergyReceive(EnergyStorage storage, int amount) {
        if(canReceive(storage, amount)) {
            storage.recieveEnergy(amount);
            this.updateEntity();
            return true;
        }

        return false;
    }

    public boolean canReceive(EnergyStorage storage, int amount) {
        return (storage.getEnergyCapacity() - storage.getEnergyStored()) >= amount;
    }
}
