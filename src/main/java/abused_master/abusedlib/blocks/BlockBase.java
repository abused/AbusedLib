package abused_master.abusedlib.blocks;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
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

    public BlockBase(String name, ItemGroup itemGroup, Block.Settings blockSettings) {
        super(blockSettings);
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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return null;
    }
}
