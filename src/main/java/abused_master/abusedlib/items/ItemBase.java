package abused_master.abusedlib.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name, CreativeTabs tab) {
        super();
        this.setCreativeTab(tab);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }
}