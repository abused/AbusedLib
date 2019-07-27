package abused_master.abusedlib.blocks.multiblock;

import abused_master.abusedlib.AbusedLib;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public class MultiBlockReloadListener implements SimpleSynchronousResourceReloadListener {

    public static final int PREFIX_LENGTH = "multiblocks/".length();
    public static final int SUFFIX_LENGTH = ".json".length();
    public static final Identifier MULTIBLOCK_RELOAD_ID = new Identifier(AbusedLib.MODID, "multiblock_reloader");

    @Override
    public Identifier getFabricId() {
        return MULTIBLOCK_RELOAD_ID;
    }

    @Override
    public void apply(ResourceManager resourceManager) {
        MultiBlockBuilder.loadedMultiblocks.clear();

        for (Identifier identifier : resourceManager.findResources("multiblocks", s -> s.endsWith(".json"))) {
            try (InputStream inputStream = resourceManager.getResource(identifier).getInputStream()) {
                Identifier key = new Identifier(identifier.getNamespace(), identifier.getPath().substring(PREFIX_LENGTH, identifier.getPath().length() - SUFFIX_LENGTH));
                MultiBlock multiblock = MultiBlockBuilder.getMultiBlock(key, inputStream);
                MultiBlockBuilder.loadedMultiblocks.put(key, multiblock);
            } catch (IOException e) {
                AbusedLib.LOGGER.warn("Unable to load MultiBlock from data resources! " + identifier.toString(), e);
            }
        }
    }
}
