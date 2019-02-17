package abused_master.abusedlib.blocks.multipart.impl;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.abusedlib.blocks.multipart.IMultipart;
import abused_master.abusedlib.blocks.multipart.IMultipartHost;
import abused_master.abusedlib.tiles.BlockEntityMultipart;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMultipartHost extends BlockWithEntityBase {

    public BlockMultipartHost(String name, Material material, float hardness, ItemGroup itemGroup) {
        super(name, material, hardness, itemGroup);
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockHitResult hitResult) {
        IMultipartHost multipartHost = (IMultipartHost) world.getBlockEntity(pos);

        for (IMultipart multipart : multipartHost.getMultiparts().values()) {
            multipart.onMultipartActivated(pos, hitResult.getSide(), playerEntity, hand, playerEntity.getStackInHand(hand));
        }

        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityMultipart();
    }
}
