package abused_master.abusedlib.mixins;

import abused_master.abusedlib.utils.events.DropItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"))
    public void dropItem(ItemStack stack, boolean boolean_1, boolean boolean_2, CallbackInfoReturnable<ItemStack> cir) {
        DropItemCallback.EVENT.invoker().dropItem((PlayerEntity) (Object) this, stack, boolean_1, boolean_2);
    }
}
