package test;

import abused_master.abusedlib.blocks.BlockBase;
import abused_master.abusedlib.client.render.obj.OBJLoader;
import abused_master.abusedlib.registry.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;

public class Test implements ModInitializer {

    public static BlockBase test = new BlockBase("test", Material.STONE, 1.0f, ItemGroup.REDSTONE);

    @Override
    public void onInitialize() {
        OBJLoader.INSTANCE.registerObjHandler("abusedlib");
        RegistryHelper.registerBlock("abusedlib", test);
    }
}
