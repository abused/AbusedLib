package abused_master.abusedlib.mixins;

import abused_master.abusedlib.eventhandler.EventRegistry;
import abused_master.abusedlib.eventhandler.events.PlayerEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(Entity entity_1, Hand hand_1, CallbackInfoReturnable cir) {
        PlayerEvents.PlayerInteractEntity playerInteractEntity = new PlayerEvents.PlayerInteractEntity((PlayerEntity) (Object) this, ((PlayerEntity) (Object) this).getActiveHand(), entity_1);
        EventRegistry.runEvent(playerInteractEntity);

        if (playerInteractEntity.isCanceled()) {
            cir.setReturnValue(ActionResult.FAILURE);
            cir.cancel();
        }
    }

    @Shadow
    public abstract boolean isSpectator();
}
