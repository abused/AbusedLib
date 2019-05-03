package abused_master.abusedlib.mixins;

import abused_master.abusedlib.utils.CacheMapHolder;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketItem.class)
public class MixinBucketItem {

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Fluid fluid, Item.Settings settings, CallbackInfo ci) {
        CacheMapHolder.INSTANCE.cachedBucketsRegistry.put(fluid, (BucketItem) (Object) this);
    }
}
