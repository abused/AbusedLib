package abused_master.abusedlib;

import abused_master.abusedlib.blocks.multiblock.MultiBlockBuilder;
import abused_master.abusedlib.blocks.multiblock.MultiBlockReloadListener;
import abused_master.abusedlib.tiles.BlockEntityMultipart;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.resource.ResourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbusedLib implements ModInitializer {

    public static final String MODID = "abusedlib";
    public static Logger LOGGER = LogManager.getLogger("AbusedLib");
    public static BlockEntityType<BlockEntityMultipart> MULTIPART;

    @Override
    public void onInitialize() {
        //Tile registry now needs a list of blocks, will rewrite multipart system...
        //MULTIPART = RegistryHelper.registerTile(new Identifier(MODID, "blockentity_multipart"), BlockEntityMultipart.class);
        MultiBlockBuilder.registerMultiBlockFunctions();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new MultiBlockReloadListener());
    }
}
