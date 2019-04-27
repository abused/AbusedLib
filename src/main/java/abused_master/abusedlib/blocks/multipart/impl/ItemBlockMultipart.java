package abused_master.abusedlib.blocks.multipart.impl;

import abused_master.abusedlib.blocks.multipart.IMultipart;
import abused_master.abusedlib.blocks.multipart.IMultipartHost;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ItemBlockMultipart extends BlockItem {

    public ItemBlockMultipart(Block block_1, Settings item$Settings_1) {
        super(block_1, item$Settings_1);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemStack stack = context.getItemStack();
        Direction direction = context.getFacing();
        BlockPos pos = context.getBlockPos().offset(direction);

        if(!world.isAir(pos) && world.getBlockEntity(pos) instanceof IMultipartHost) {
            IMultipartHost multipartHost = (IMultipartHost) world.getBlockEntity(pos);

            if(!multipartHost.hasMultipart(direction)) {
                if(multipartHost.tryAddMultipart(direction, (IMultipart) ((BlockWithEntity) Block.getBlockFromItem(stack.getItem())).createBlockEntity(world))) {
                    stack.subtractAmount(1);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.useOnBlock(context);
    }
}
