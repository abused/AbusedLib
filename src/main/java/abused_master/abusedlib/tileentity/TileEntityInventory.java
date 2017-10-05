package abused_master.abusedlib.tileentity;

import abused_master.abusedlib.utils.CustomItemStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileEntityInventory extends TileEntityBase {

    public final CustomItemStackHandler inventory;

    public TileEntityInventory(int slots) {
        inventory = new CustomItemStackHandler(slots) {
            @Override
            public boolean canInsert(ItemStack stack, int slot){
                return TileEntityInventory.this.isItemValidForSlot(slot, stack);
            }

            @Override
            public boolean canExtract(ItemStack stack, int slot){
                return TileEntityInventory.this.canExtractItem(slot, stack);
            }

            @Override
            public int getSlotLimit(int slot){
                return TileEntityInventory.this.getMaxStackSizePerSlot(slot);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                TileEntityInventory.this.markDirty();
            }
        };
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("items", inventory.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("items")) {
            inventory.deserializeNBT((NBTTagCompound) nbt.getTag("items"));
        }
    }

    public IItemHandler getItemHandler(EnumFacing facing){
        return this.inventory;
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return true;
    }

    public boolean canExtractItem(int slot, ItemStack stack){
        return true;
    }

    public int getMaxStackSizePerSlot(int slot){
        return 64;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            IItemHandler handler = this.getItemHandler(facing);
            if(handler != null){
                return (T)handler;
            }
        }
        return super.getCapability(capability, facing);
    }
}
