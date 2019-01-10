package abused_master.abusedlib.mixins;

import abused_master.abusedlib.event.BlockEvents;
import abused_master.abusedlib.event.EventRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "onBreak", at = @At("HEAD"))
    public void onBreak(World world_1, BlockPos blockPos_1, BlockState blockState_1, PlayerEntity playerEntity_1, CallbackInfo ci) {
        BlockEvents.BlockBreakEvent blockBreakEvent = new BlockEvents.BlockBreakEvent(world_1, blockPos_1, blockState_1, playerEntity_1);
        EventRegistry.invokeEvent(blockBreakEvent);

        if(blockBreakEvent.isCanceled()) {
            ci.cancel();
            return;
        }
    }

    @Inject(method = "onBlockBreakStart", at = @At("HEAD"))
    public void onBlockBreakStart(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, CallbackInfo ci) {
        BlockEvents.BlockBreakEvent blockBreakEvent = new BlockEvents.BlockBreakEvent(world_1, blockPos_1, blockState_1, playerEntity_1);
        EventRegistry.invokeEvent(blockBreakEvent);

        if(blockBreakEvent.isCanceled()) {
            ci.cancel();
            return;
        }
    }
}
