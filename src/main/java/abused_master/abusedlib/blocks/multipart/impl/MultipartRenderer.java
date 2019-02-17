package abused_master.abusedlib.blocks.multipart.impl;

import abused_master.abusedlib.blocks.multipart.IMultipart;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.Direction;

public abstract class MultipartRenderer<T extends BlockEntity & IMultipart> extends BlockEntityRenderer<T> {

    public abstract void renderMultipart(T tile, Direction direction, double x, double y, double z, float deltaTime, int destroyStage);
}
