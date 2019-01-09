package abused_master.abusedlib.registry;

import abused_master.abusedlib.blocks.BlockBase;
import abused_master.abusedlib.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultMappedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.function.Supplier;

public class RegistryHelper {

    /**
     * Blocks and Item registry Helpers
     */
    public static void registerBlock(String modid, BlockBase block) {
        Registry.register(DefaultMappedRegistry.BLOCK, block.getNameIdentifier(modid), block);
        Registry.register(DefaultMappedRegistry.ITEM, block.getNameIdentifier(modid), new BlockItem(block, new Item.Settings().itemGroup(block.getTab())));
    }

    public static void registerBlock(String modid, BlockBase block, BlockItem blockItem) {
        Registry.register(DefaultMappedRegistry.BLOCK, block.getNameIdentifier(modid), block);
        Registry.register(DefaultMappedRegistry.ITEM, block.getNameIdentifier(modid), blockItem);
    }

    public static void registerBlock(String modid, String name, ItemGroup itemGroup, Block block) {
        Registry.register(DefaultMappedRegistry.BLOCK, new Identifier(modid, name), block);
        Registry.register(DefaultMappedRegistry.ITEM, new Identifier(modid, name), new BlockItem(block, new Item.Settings().itemGroup(itemGroup)));
    }

    public static void registerBlock(String modid, String name, Block block, BlockItem blockItem) {
        Registry.register(DefaultMappedRegistry.BLOCK, new Identifier(modid, name), block);
        Registry.register(DefaultMappedRegistry.ITEM, new Identifier(modid, name), blockItem);
    }

    public static void registerItem(String modid, ItemBase item) {
        Registry.register(DefaultMappedRegistry.ITEM, item.getNameIdentifier(modid), item);
    }

    public static void registerItem(String modid, String name, Item item) {
        Registry.register(DefaultMappedRegistry.ITEM, new Identifier(modid, name), item);
    }

    /**
     * Tile entity registry
     * EX: BlockEntityType<BlockEntityTest> BET = registerTile(MODID, NAME, BlockEntityTest::new);
     */
    public static BlockEntityType registerTile(String modid, String name, Supplier blockEntity) {
        return Registry.register(DefaultMappedRegistry.BLOCK_ENTITY, new Identifier(modid, name), BlockEntityType.Builder.create(blockEntity).method_11034(null));
    }

    /**
     * World Gen Ore Registry
     */

    public static void generateOreInStone(Block block, int veinSize, int spawnRate, int maxHeight) {
        for (Biome biome : Biome.BIOMES) {
            biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(net.minecraft.world.gen.feature.Feature.ORE, new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, block.getDefaultState(), veinSize), Decorator.COUNT_RANGE, new RangeDecoratorConfig(spawnRate, 0, 0, maxHeight)));
        }
    }

    public static void generateOre(Block block, OreFeatureConfig.Target target, int veinSize, int spawnRate, int maxHeight) {
        for (Biome biome : Biome.BIOMES) {
            biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(net.minecraft.world.gen.feature.Feature.ORE, new OreFeatureConfig(target, block.getDefaultState(), veinSize), Decorator.COUNT_RANGE, new RangeDecoratorConfig(spawnRate, 0, 0, maxHeight)));
        }
    }
}
