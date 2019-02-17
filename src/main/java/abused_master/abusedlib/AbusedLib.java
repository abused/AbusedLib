package abused_master.abusedlib;

import abused_master.abusedlib.registry.RegistryHelper;
import abused_master.abusedlib.tiles.BlockEntityMultipart;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class AbusedLib implements ModInitializer {

    public static final String MODID = "abusedlib";
    public static Logger LOGGER = Logger.getLogger("AbusedLib");
    public static BlockEntityType<BlockEntityMultipart> MULTIPART;

    @Override
    public void onInitialize() {
        MULTIPART = RegistryHelper.registerTile(new Identifier(MODID, "blockentity_multipart"), BlockEntityMultipart.class);
    }
}
