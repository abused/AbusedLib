package abused_master.abusedlib.utils;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class CacheMapHolder {

    public static final CacheMapHolder INSTANCE = new CacheMapHolder();

    public Map<ItemStack, ItemStack> furnaceRecipes = Maps.newHashMap();

    public ItemStack getOutputStack(ItemStack input) {
        for (Map.Entry<ItemStack, ItemStack> entry : this.furnaceRecipes.entrySet()) {
            if(input.getItem() == entry.getKey().getItem()) {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }
}
