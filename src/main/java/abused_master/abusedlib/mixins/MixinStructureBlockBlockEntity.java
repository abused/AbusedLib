package abused_master.abusedlib.mixins;

import abused_master.abusedlib.blocks.multiblock.MultiBlockBuilder;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.Style;
import net.minecraft.text.TextFormat;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(StructureBlockBlockEntity.class)
public abstract class MixinStructureBlockBlockEntity {

    @Shadow
    private String author;

    @Shadow
    private BlockPos size;

    @Shadow
    private Identifier structureName;

    @Inject(method = "Lnet/minecraft/block/entity/StructureBlockBlockEntity;saveStructure(Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/Structure;setAuthor(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void saveStructure(boolean save, CallbackInfoReturnable<Boolean> cir, BlockPos pos, ServerWorld serverWorld, StructureManager structureManager, Structure structure) {
        if(save && !author.isEmpty() && !((StructureBlockBlockEntity) (Object) this).getWorld().isClient()) {
            BlockPos pos2 = size.add(pos).add(-1, -1, -1);
            BlockPos minCorner = new BlockPos(Math.min(pos.getX(), pos2.getX()), Math.min(pos.getY(), pos2.getY()), Math.min(pos.getZ(), pos2.getZ()));

            ServerWorld world = (ServerWorld) ((StructureBlockBlockEntity) (Object) this).getWorld();
            ServerPlayerEntity player = world.getServer().getPlayerManager().getPlayer(author);

            if(player != null && MultiBlockBuilder.playerCommandCache.containsKey(player.getUuid())) {
                MultiBlockBuilder.createMultiBlock(serverWorld, MultiBlockBuilder.playerCommandCache.get(player.getUuid()), minCorner, structure, structureName.getPath() + ".json");
                player.addChatMessage(new StringTextComponent("Successfully saved multiblock " + structureName.getPath() + ".json").setStyle(new Style().setColor(TextFormat.GOLD)), false);
                cir.setReturnValue(true);
            }
        }
    }
}
