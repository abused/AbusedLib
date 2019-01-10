package abused_master.abusedlib.mixins;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
public class MixinWorld {

    /*
    @Inject(method = "breakBlock", at = @At("HEAD"))
    public void breakBlock(BlockPos blockPos_1, boolean boolean_1, CallbackInfoReturnable cir) {
        BlockEvents.BlockBreakEvent blockBreakEvent = new BlockEvents.BlockBreakEvent((World) (Object) this, blockPos_1, ((World) (Object) this).getBlockState(blockPos_1), null);
        EventRegistry.invokeEvent(blockBreakEvent);
    }
    */
}
