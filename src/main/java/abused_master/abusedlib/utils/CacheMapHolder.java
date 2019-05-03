package abused_master.abusedlib.utils;

import com.google.common.collect.Maps;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public class CacheMapHolder {

    public static final CacheMapHolder INSTANCE = new CacheMapHolder();

    public Map<ItemStack, ItemStack> furnaceRecipes = Maps.newHashMap();
    public Map<Fluid, BucketItem> cachedBucketsRegistry = Maps.newHashMap();

    public ItemStack getOutputStack(ItemStack input) {
        for (Map.Entry<ItemStack, ItemStack> entry : this.furnaceRecipes.entrySet()) {
            if(input.getItem() == entry.getKey().getItem()) {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }

    public BucketItem getBucketForFluid(Fluid fluid) {
        return cachedBucketsRegistry.containsKey(fluid) ? cachedBucketsRegistry.get(fluid) : (BucketItem) Items.BUCKET;
    }

    public Fluid getFluidForBucket(BucketItem bucket) {
        for (Map.Entry<Fluid, BucketItem> entry : cachedBucketsRegistry.entrySet()) {
            if(entry.getValue() == bucket) {
                return entry.getKey();
            }
        }

        return null;
    }
}
