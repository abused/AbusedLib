package abused_master.abusedlib.utils;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class InventoryHelper {

    public static boolean insertItemIfPossible(Inventory inventory, ItemStack stack, boolean simulate) {
        if(inventory == null) {
            return false;
        }

        for (int i = 0; i < inventory.getInvSize(); i++) {
            if(!inventory.getInvStack(i).isEmpty()) {
                if(canItemStacksStack(inventory.getInvStack(i), stack) && inventory.getInvStack(i).getAmount() < 64) {
                    if(!simulate)
                        stack.addAmount(inventory.getInvStack(i).getAmount());
                        inventory.setInvStack(i, stack);

                    return true;
                }
            }else {
                if(!simulate)
                    inventory.setInvStack(i, stack);

                return true;
            }
        }

        return false;
    }

    public static boolean canItemStacksStack(ItemStack a, ItemStack b) {
        if (a.isEmpty() || !a.isEqualIgnoreTags(b) || a.hasTag() != b.hasTag())
            return false;

        return (!a.hasTag() || a.getTag().equals(b.getTag()));
    }

    public static Inventory getNearbyInventory(World world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPosition = new BlockPos(pos).offset(direction);
            BlockEntity entity = world.getBlockEntity(offsetPosition);
            if (entity != null && entity instanceof Inventory) {
                Inventory inventory = (Inventory) entity;
                return inventory;
            }
        }

        return null;
    }

    public static SidedInventory getNearbySidedInventory(World world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPosition = new BlockPos(pos).offset(direction);
            BlockEntity entity = world.getBlockEntity(offsetPosition);
            if (entity != null && entity instanceof SidedInventory) {
                SidedInventory inventory = (SidedInventory) entity;
                return inventory;
            }
        }

        return null;
    }
}
