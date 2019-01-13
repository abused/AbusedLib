package abused_master.abusedlib.tiles;

import abused_master.abusedlib.capabilities.utils.ICapabilityContainer;
import net.fabricmc.fabric.block.entity.ClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.network.packet.BlockEntityUpdateClientPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

public abstract class TileEntityBase extends BlockEntity implements Tickable, ClientSerializable, ICapabilityContainer {

    public TileEntityBase(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.fromTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return this.toTag(tag);
    }

    @Override
    public BlockEntityUpdateClientPacket toUpdatePacket() {
        return super.toUpdatePacket();
    }
}
