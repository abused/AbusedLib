package abused_master.abusedlib.blocks;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.loot.context.LootContext;

import java.util.List;

public class BlockBase extends Block {

    private String name;
    private ItemGroup tab;

    public BlockBase(String name, Material material, float hardness, ItemGroup itemGroup) {
        super(FabricBlockSettings.of(material).hardness(hardness).build());
        this.name = name;
        this.tab = itemGroup;
    }

    public BlockBase(String name, ItemGroup itemGroup, Settings blockSettings) {
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
    public List<ItemStack> getDroppedStacks(BlockState blockState_1, LootContext.Builder lootContext$Builder_1) {
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
}
