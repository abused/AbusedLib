package abused_master.abusedlib.capabilities.defaults.impl;

import net.minecraft.item.ItemStack;

public interface IItemHandler {

    int getSlots();
    ItemStack getStackInSlot(int slot);
    void setStackInSlot(int slot, ItemStack stack);
    ItemStack insertItem(int slot, ItemStack stack);
    ItemStack extractItem(int slot, int amount);
    int getSlotLimit(int slot);
}
