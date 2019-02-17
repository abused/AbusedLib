package abused_master.abusedlib.blocks.multipart.impl;

import abused_master.abusedlib.tiles.BlockEntityMultipart;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.Direction;

public class MultipartHostRenderer extends BlockEntityRenderer<BlockEntityMultipart> {

    @Override
    public void render(BlockEntityMultipart tile, double x, double y, double z, float deltaTime, int destroyStage) {
        super.render(tile, x, y, z, deltaTime, destroyStage);

        if(!tile.multiparts.isEmpty()) {
            for (Direction direction : tile.multiparts.keySet()) {
                BlockEntity blockEntity = tile.multiparts.get(direction).getMultipartEntity();

                if(blockEntity != null) {
                    BlockEntityRenderer renderer = BlockEntityRenderDispatcher.INSTANCE.get(blockEntity);

                    if(renderer instanceof MultipartRenderer) {
                        ((MultipartRenderer) renderer).renderMultipart(blockEntity, direction, x, y, z, deltaTime, destroyStage);
                    }
                }
            }
        }
    }
}
