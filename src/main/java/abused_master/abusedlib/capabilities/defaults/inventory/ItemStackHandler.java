package abused_master.abusedlib.capabilities.defaults.inventory;

import abused_master.abusedlib.capabilities.utils.ITagSerializable;
import abused_master.abusedlib.capabilities.NBTConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.DefaultedList;

import javax.annotation.Nonnull;

public class ItemStackHandler implements IItemHandler, ITagSerializable<CompoundTag> {

    protected DefaultedList<ItemStack> inventory;

    public ItemStackHandler() {
        this(1);
    }

    public ItemStackHandler(int size) {
        inventory = DefaultedList.create(size, ItemStack.EMPTY);
    }

    public ItemStackHandler(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public void setSize(int size) {
        inventory = DefaultedList.create(size, ItemStack.EMPTY);
    }

    @Override
    public int getSlots() {
        return inventory.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        validateSlot(slot);
        return this.inventory.get(0);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        validateSlot(slot);
        this.inventory.set(slot, stack);
        onContentsChanged(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        validateSlot(slot);

        ItemStack existing = this.inventory.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty()) {
            if (!ItemStack.areEqual(stack, existing))
                return stack;

            limit -= existing.getAmount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getAmount() > limit;


        if (existing.isEmpty()) {
            this.inventory.set(slot, reachedLimit ? copyStackWithSize(stack, limit) : stack);
        } else {
            existing.addAmount(reachedLimit ? limit : stack.getAmount());
        }
        onContentsChanged(slot);


        return reachedLimit ? copyStackWithSize(stack, stack.getAmount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int amount) {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlot(slot);

        ItemStack existing = this.inventory.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxAmount());

        if (existing.getAmount() <= toExtract) {
            this.inventory.set(slot, ItemStack.EMPTY);
            onContentsChanged(slot);

            return existing;
        } else {
            this.inventory.set(slot, copyStackWithSize(existing, existing.getAmount() - toExtract));
            onContentsChanged(slot);
            return copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public CompoundTag serializeTag() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < inventory.size(); i++) {
            if (!inventory.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                inventory.get(i).toTag(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", inventory.size());
        return nbt;
    }

    @Override
    public void deserializeTag(CompoundTag nbt) {
        setSize(nbt.containsKey("Size", NBTConstants.TAG_INT) ? nbt.getInt("Size") : inventory.size());
        ListTag tagList = nbt.getList("Items", NBTConstants.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompoundTag(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < inventory.size()) {
                inventory.set(slot, ItemStack.fromTag(itemTags));
            }
        }
        onLoad();
    }

    protected void validateSlot(int slot) {
        if (slot < 0 || slot >= inventory.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + inventory.size() + ")");
    }

    protected int getStackLimit(int slot, ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getMaxAmount());
    }

    public ItemStack copyStackWithSize(@Nonnull ItemStack itemStack, int size) {
        if (size == 0)
            return ItemStack.EMPTY;
        ItemStack copy = itemStack.copy();
        copy.setAmount(size);
        return copy;
    }

    protected void onContentsChanged(int slot) {
    }

    protected void onLoad() {
    }
}
