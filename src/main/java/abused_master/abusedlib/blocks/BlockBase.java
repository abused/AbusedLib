package abused_master.abusedlib.blocks;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public abstract class BlockBase extends Block {

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
}
