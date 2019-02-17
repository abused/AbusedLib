package abused_master.abusedlib.client.gui;

import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class OutputSlot extends Slot {

    public OutputSlot(Inventory inventory_1, int int_1, int int_2, int int_3) {
        super(inventory_1, int_1, int_2, int_3);
    }

    @Override
    public boolean canInsert(ItemStack itemStack_1) {
        return false;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity_1) {
        return super.canTakeItems(playerEntity_1);
    }

    public ItemStack takeStack(int int_1) {
        return super.takeStack(int_1);
    }

    public ItemStack onTakeItem(PlayerEntity playerEntity_1, ItemStack itemStack_1) {
        this.onCrafted(itemStack_1);
        super.onTakeItem(playerEntity_1, itemStack_1);
        return itemStack_1;
    }
}