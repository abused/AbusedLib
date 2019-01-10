package abused_master.abusedlib.blocks;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public abstract class BlockBase extends BlockWithEntity {

    private String name;
    private ItemGroup tab;

    public BlockBase(String name, Material material, float hardness, ItemGroup itemGroup) {
        super(FabricBlockSettings.of(material).hardness(hardness).build());
        this.name = name;
        this.tab = itemGroup;
    }

    public String getName() {
        return name;
    }

    public Identifier getNameIdentifier(String modid) {
        return new Identifier(modid, getName());
    }

    public ItemGroup getTab() {
        return tab;
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean isSideVisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
        return blockState_1.getBlock() == this ? true : super.isSideVisible(blockState_1, blockState_2, direction_1);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return null;
    }
}
