package abused_master.abusedlib.capabilities.defaults;

import abused_master.abusedlib.capabilities.Capability;
import abused_master.abusedlib.capabilities.CapabilityHandler;
import abused_master.abusedlib.capabilities.defaults.impl.IItemHandler;
import abused_master.abusedlib.capabilities.defaults.impl.ItemStackHandler;
import abused_master.abusedlib.capabilities.utils.CapabilityInject;
import abused_master.abusedlib.capabilities.utils.RegisterCapability;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.Direction;

public class CapabilityItemHandler {

    @CapabilityInject(IItemHandler.class)
    public static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    @RegisterCapability
    public static void register() {
        CapabilityHandler.INSTANCE.register(IItemHandler.class, new Capability.IStorage<IItemHandler>() {
            @Override
            public Tag toTag(Capability<IItemHandler> capability, IItemHandler instance, Direction direction) {
                ListTag nbtTagList = new ListTag();
                int size = instance.getSlots();
                for (int i = 0; i < size; i++)
                {
                    ItemStack stack = instance.getStackInSlot(i);
                    if (!stack.isEmpty())
                    {
                        CompoundTag itemTag = new CompoundTag();
                        itemTag.putInt("Slot", i);
                        stack.toTag(itemTag);
                        nbtTagList.add(itemTag);
                    }
                }
                return nbtTagList;
            }

            @Override
            public void fromTag(Capability<IItemHandler> capatility, IItemHandler instance, Direction direction, Tag tag) {
                ItemStackHandler itemHandlerModifiable = (ItemStackHandler) instance;
                ListTag tagList = (ListTag) tag;
                for (int i = 0; i < tagList.size(); i++)
                {
                    CompoundTag itemTags = tagList.getCompoundTag(i);
                    int j = itemTags.getInt("Slot");

                    if (j >= 0 && j < instance.getSlots())
                    {
                        itemHandlerModifiable.setStackInSlot(j, ItemStack.fromTag(itemTags));
                    }
                }
            }
        }, ItemStackHandler::new);
    }
}
