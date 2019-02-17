package abused_master.abusedlib.blocks;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWithEntityBase extends BlockBase implements BlockEntityProvider {

    public BlockWithEntityBase(String name, Material material, float hardness, ItemGroup itemGroup) {
        super(name, material, hardness, itemGroup);
    }

    public BlockWithEntityBase(String name, ItemGroup itemGroup, Settings blockSettings) {
        super(name, itemGroup, blockSettings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
        if (blockState_1.getBlock() != blockState_2.getBlock()) {
            super.onBlockRemoved(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
            world_1.removeBlockEntity(blockPos_1);
        }
    }

    @Override
    public boolean onBlockAction(BlockState blockState_1, World world_1, BlockPos blockPos_1, int int_1, int int_2) {
        super.onBlockAction(blockState_1, world_1, blockPos_1, int_1, int_2);
        BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
        return blockEntity_1 == null ? false : blockEntity_1.onBlockAction(int_1, int_2);
    }
}
